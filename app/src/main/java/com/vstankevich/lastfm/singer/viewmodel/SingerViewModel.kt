package com.vstankevich.lastfm.singer.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.vstankevich.lastfm.singer.Artist
import com.vstankevich.lastfm.singer.repository.SingerRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by victor.stankevich on 18.07.2018.
 */
class SingerViewModel(app: Application) : AndroidViewModel(app) {

    val TAG = SingerViewModel::class.java.simpleName

    private val repository: SingerRepository by lazy {
        SingerRepository()
    }

    val name: MutableLiveData<List<Artist>> by lazy {
        MutableLiveData<List<Artist>>()
    }

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    fun searchArtistByName(artistName: String) {
        compositeDisposable.add(repository.getSingersByName(artistName)
                                        .subscribe({
                                                       name.postValue(
                                                               it.results.artistmatches.artist)
                                                   }, {
                                                       Log.d(TAG,
                                                             "Error: " + it.message)
                                                   }))

    }

    fun loadTopArtists() {
        compositeDisposable.add(repository.getTopSingers()
                                        .subscribe({
                                                       name.postValue(
                                                               it.artists.artist)
                                                   }, {
                                                       Log.d(TAG,
                                                             "Error: " + it.message)
                                                   }))
    }

    fun clear() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
    }

}