package com.moucan.business.db

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.moucan.business.db.entity.Movie
import java.util.concurrent.Executors

@Database(entities = [Movie::class], version = 1)
abstract class MovieDataBase: RoomDatabase() {
    val databaseCreated = MutableLiveData<Boolean?>()
    abstract fun movieDao(): MovieDao
    companion object{
        @Volatile
        private var instance: MovieDataBase? = null

        private const val DATA_BASE_NAME = "jetpack_movie.db"

        @JvmStatic
        fun getInstance(context: Context) : MovieDataBase? {
            if (instance == null) {
                synchronized(MovieDataBase::class.java) {
                    if (instance == null) {
                        instance = createInstance(context)
                    }
                }
            }
            return instance
        }
        private fun createInstance(context: Context): MovieDataBase {
            return Room.databaseBuilder(context.applicationContext, MovieDataBase::class.java, DATA_BASE_NAME)
                .addCallback(object : Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Executors.newFixedThreadPool(5).execute {
                            val dataBase = getInstance(context)
                            val ids = dataBase!!.movieDao().insert()
                            dataBase.databaseCreated.postValue(true)
                        }
                    }
                })
                .build()
        }
    }
}