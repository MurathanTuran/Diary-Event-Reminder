package com.turanapps.eventreminder.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.turanapps.eventreminder.CONST.Const.EVENT_TABLE_NAME
import java.util.*

@Entity(tableName = EVENT_TABLE_NAME)
data class Event(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val header: String,
    val comment: String,
    val date: Date
)
