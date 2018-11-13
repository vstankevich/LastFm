package com.vstankevich.lastfm.singer.repository

import com.vstankevich.lastfm.singer.ArtistContent
import com.vstankevich.lastfm.singer.TopArtistContent
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by victor.stankevich on 18.07.2018.
 */
interface SingerService {
    @GET("/2.0/?method=artist.search&api_key=c407177e0dc1a711e9123bf8251ea405&format=json")
    fun getArtists(@Query("artist") artistName: String): Single<ArtistContent>

    @GET("/2.0/?method=chart.gettopartists&api_key=c407177e0dc1a711e9123bf8251ea405&format=json")
    fun getTopArtists(): Single<TopArtistContent>
}