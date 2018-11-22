package com.vstankevich.lastfm.singer.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.vstankevich.lastfm.singer.Artist
import com.vstankevich.lastfm.singer.repository.SingerRepository
import io.reactivex.disposables.Disposable

/**
 * victor.stankevich
 *  16.11.2018.
 */
class SingerDetailsViewModel(app: Application) : AndroidViewModel(app) {

    private val repository: SingerRepository by lazy {
        SingerRepository()
    }

    val artist: MutableLiveData<Artist> by lazy {
        MutableLiveData<Artist>()
    }

    private var artistDisposable: Disposable? = null

    fun getDetailsById(id: String) {
        artistDisposable = repository.getSingersDatails(id).subscribe({
                                                                         artist.postValue(it.artist)
                                                                     }, {})
    }

    fun clear() {
        artistDisposable?.dispose()
    }
}
