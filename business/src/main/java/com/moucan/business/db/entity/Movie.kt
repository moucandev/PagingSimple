package com.moucan.business.db.entity

import androidx.databinding.BaseObservable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Movie: BaseObservable() {

    @PrimaryKey(autoGenerate = true)
    var id = 0

    @ColumnInfo(name = "movie_name", defaultValue = "Spider Man")
    lateinit var name: String

    @ColumnInfo(name = "actor_name", defaultValue = "Tobey Maguire")
    lateinit var actor: String

    @ColumnInfo(name = "post_year", defaultValue = "2002")
    var year = 2002

    @ColumnInfo(name = "review_score", defaultValue = "8.0")
    var score = 8.0

}