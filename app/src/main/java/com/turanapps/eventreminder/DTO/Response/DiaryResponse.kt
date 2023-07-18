package com.turanapps.eventreminder.DTO.Response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class DiaryResponse(
    val id: Int? = null,
    val header: String? = null,
    val comment: String? = null,
    val date: Date? = null
) : Parcelable
