package com.vstankevich.lastfm.singer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.vstankevich.lastfm.singer.Artist
import com.vstankevich.lastfm.singer.DataState
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

    val dataState: MutableLiveData<DataState> by lazy {
        MutableLiveData<DataState>()
    }

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    fun searchArtistByName(artistName: String) {
        compositeDisposable.add(repository.getSingersByName(artistName)
                                        .doOnSubscribe {
                                            dataState.postValue(DataState.START)
                                        }
                                        .subscribe({
                                                       name.postValue(
                                                               it.results.artistmatches.artist)
                                                       dataState.postValue(DataState.SUCCESS)
                                                   }, {
                                                       dataState.postValue(DataState.ERROR)
                                                   }))

    }

    fun loadTopArtists() {
        compositeDisposable.add(repository.getTopSingers()
                                        .doOnSubscribe {
                                            dataState.postValue(DataState.START)
                                        }
                                        .subscribe({
                                                       name.postValue(
                                                               it.artists.artist)
                                                       dataState.postValue(DataState.SUCCESS)
                                                   }, {
                                                       dataState.postValue(DataState.ERROR)

                                                   }))
    }

    fun clear() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
    }
}