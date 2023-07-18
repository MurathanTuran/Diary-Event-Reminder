package com.turanapps.eventreminder.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.turanapps.eventreminder.CONST.Const.DIARY_TABLE_NAME
import java.util.*

@Entity(tableName = DIARY_TABLE_NAME)
data class Diary(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val header: String,
    val comment: String,
    val date: Date
)
