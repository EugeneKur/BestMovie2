package com.example.bestmovie2.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val idDTO: Long,
    val name: String,
    val about: String,
    val rating: Int,
    val year: String,
    val timestamp: Long,
    val note: String
)