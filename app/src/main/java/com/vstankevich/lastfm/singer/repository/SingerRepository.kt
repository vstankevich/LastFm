package com.vstankevich.lastfm.singer.repository

import com.vstankevich.lastfm.NetworkModule
import com.vstankevich.lastfm.singer.ArtistContent
import com.vstankevich.lastfm.singer.TopArtistContent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by victor.stankevich on 18.07.2018.
 */
class SingerRepository {

    val singerService: SingerService by lazy {
        NetworkModule().createSingerService()
    }

    fun getSingersByName(name: String): Single<ArtistContent> {
        return singerService.getArtists(name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    fun getTopSingers(): Single<TopArtistContent> {
        return singerService.getTopArtists()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }
}