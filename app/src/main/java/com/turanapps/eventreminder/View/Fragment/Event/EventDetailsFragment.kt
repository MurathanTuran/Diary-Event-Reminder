package com.turanapps.eventreminder.View.Fragment.Event

import android.app.*
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.turanapps.eventreminder.DTO.Request.EventRequest
import com.turanapps.eventreminder.DTO.Response.EventResponse
import com.turanapps.eventreminder.Error.ObserveErrors
import com.turanapps.eventreminder.Util.ReminderBroadcast
import com.turanapps.eventreminder.ViewModel.Fragment.Event.EventDetailsViewModel
import com.turanapps.eventreminder.databinding.FragmentEventDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class EventDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEventDetailsBinding

    private val viewModel: EventDetailsViewModel by viewModels()

    private val calendar = Calendar.getInstance()

    private var eventResponse: EventResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        eventResponse = arguments?.getParcelable("eventResponse")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ObserveErrors.observe(viewLifecycleOwner, requireContext(),
            viewModel.emptyEventHeaderErrorEntity, viewModel.emptyEventCommentErrorEntity, viewModel.insertEventErrorEntity, viewModel.emptyEventIdErrorEntity, viewModel.updateEventErrorEntity, viewModel.deleteEventErrorEntity)

        setMinDateForCalendar()

        onClick()

        observeReadyToGoEventFragment()

    }

    private fun setMinDateForCalendar(){

        val currentDate = Calendar.getInstance()

        val oneDayInMillis = 24 * 60 * 60 * 1000

        binding.calendarView.minDate = (currentDate.timeInMillis + oneDayInMillis)

    }

    private fun onClick() {
        onClickForNewDiary()
        onClickForOldDiary()
    }

    private fun onClickForNewDiary() {

        if(eventResponse==null){

            binding.deleteImageView.visibility = View.GONE

            dateClicked()
            saveClicked()

        }

    }

    private fun onClickForOldDiary() {

        if(eventResponse!=null){

            binding.calendarView.date = eventResponse!!.date!!.time

            calendar.time = eventResponse!!.date!!

            binding.headerText.setText(eventResponse!!.header)
            binding.commentText.setText(eventResponse!!.comment)

            dateClicked()
            updateClicked()
            deleteClicked()

        }

    }

    private fun dateClicked(){

        calendar.timeInMillis += 24 * 60 * 60 * 1000

        binding.calendarView.date = calendar.timeInMillis

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            showTimePickerDialog()
        }
    }

    private fun showTimePickerDialog() {
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
        }

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            timeSetListener,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
        timePickerDialog.show()
    }

    private fun saveClicked() {
        binding.saveImageView.setOnClickListener {
            binding.apply {
                val idS = viewModel.insertEvent(EventRequest(header = headerText.text.toString(), comment = commentText.text.toString(), date = calendar.time))

                val header = headerText.text.toString()
                val comment = commentText.text.toString()

                createNotificationChannel(header)

                val intent = Intent(requireContext(), ReminderBroadcast::class.java).apply {
                    putExtra("header", header)
                    putExtra("comment", comment)
                    putExtra("id", idS)
                }

                 val pendingIntent = PendingIntent.getBroadcast(
                    requireContext(),
                    idS,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                val alarmManager = requireContext().getSystemService(ALARM_SERVICE) as AlarmManager

                val dateInMillis = calendar.timeInMillis
                alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    dateInMillis,
                    pendingIntent
                )

            }
        }
    }


    private fun updateClicked(){
        binding.saveImageView.setOnClickListener {
            binding.apply {

                viewModel.updateEvent(EventRequest(id = eventResponse!!.id, header = headerText.text.toString(), comment = commentText.text.toString(), date = calendar.time))

                val idU = eventResponse!!.id
                val header = headerText.text.toString()
                val comment = commentText.text.toString()

                val notificationManager = NotificationManagerCompat.from(requireContext())
                notificationManager.cancel(idU!!)

                createNotificationChannel(header)

                val intent = Intent(requireContext(), ReminderBroadcast::class.java).apply {
                    putExtra("header", header)
                    putExtra("comment", comment)
                    putExtra("id", idU)
                }

                val pendingIntent = PendingIntent.getBroadcast(
                    requireContext(),
                    idU,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                val alarmManager = requireContext().getSystemService(ALARM_SERVICE) as AlarmManager

                val dateInMillis = calendar.timeInMillis
                alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    dateInMillis,
                    pendingIntent
                )

            }

        }
    }

    private fun deleteClicked(){
        binding.deleteImageView.setOnClickListener {
            binding.apply {
                viewModel.deleteEvent(EventRequest(id = eventResponse!!.id, header = eventResponse!!.header!!, comment = eventResponse!!.comment!!, date = eventResponse!!.date!!))

                val idD = eventResponse!!.id!!

                val notificationManager = NotificationManagerCompat.from(requireContext())
                notificationManager.cancel(idD)

                val intent = Intent(requireContext(), ReminderBroadcast::class.java)
                val pendingIntent = PendingIntent.getBroadcast(requireContext(), idD, intent, PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE)
                pendingIntent?.cancel()

            }
        }
    }

    private fun observeReadyToGoEventFragment(){
        viewModel.readyToGoEventFragmentB.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it==true) toEventFragment()
        })
    }

    private fun toEventFragment(){
        Navigation.findNavController(requireView()).navigate(EventDetailsFragmentDirections.actionEventDetailsFragmentToEventFragment())
    }

    private fun createNotificationChannel(header: String) {

        val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel("EVENT_CHANNEL", header, NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = header
        notificationManager.createNotificationChannel(channel)

    }

}