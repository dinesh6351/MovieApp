package com.example.networkduo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey
    val id: Long,

    val title: String,

    @ColumnInfo(name = "poster_path")
    //    columnInfo change name in database formate
    @SerializedName("poster_path")
    val posterPath: String,

    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    //    json formate
    val backdropPath: String,

    val overview: String,

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")  //json formate
    val releaseDate: Date
)