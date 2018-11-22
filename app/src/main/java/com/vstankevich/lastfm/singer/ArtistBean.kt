package com.vstankevich.lastfm.singer

import com.google.gson.annotations.SerializedName

/**
 * Created by victor.stankevich on 18.07.2018.
 */

data class Artist(val name: String, val mbid: String, val url: String, val image: List<Photo>, val bio: ArtistBio?)

data class ArtistDetails(val artist: Artist)

data class ArtistSearchResultContent(val results: ArtistSearchResult)

data class ArtistSearchResult(val artistmatches: ArtistsList)

data class ArtistsList(val artist: List<Artist>)

data class TopArtistContent(val artists: ArtistsList)

data class ArtistBio(val published: String, val content: String)

data class Photo(@SerializedName("#text") val url: String, val size: String)