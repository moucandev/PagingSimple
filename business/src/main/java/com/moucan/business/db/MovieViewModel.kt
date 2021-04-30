package com.moucan.business.db

import android.app.Application
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.moucan.business.db.entity.Movie

class MovieViewModel(application: Application) : ViewModel() {
    private val mediatorLiveData = MediatorLiveData<List<Movie?>?>()
    private val db: MovieDataBase?
    private val mContext: Context

    init {
        mContext = application
        db = MovieDataBase.getInstance(mContext)
        if (db != null) {
            mediatorLiveData.addSource(db.movieDao().allMovieTable) { movieList ->
                if (db.databaseCreated.value != null) {
                    mediatorLiveData.postValue(movieList)
                }
            }
        }
    }
    fun getMovieList(owner: LifecycleOwner?, observer: Observer<List<Movie?>?>?) {
        if (owner != null && observer != null) {
            mediatorLiveData.observe(owner, observer)
        }
    }
}