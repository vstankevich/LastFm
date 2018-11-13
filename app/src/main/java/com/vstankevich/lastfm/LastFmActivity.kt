package com.vstankevich.lastfm

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.vstankevich.lastfm.singer.view.ArtistListFragment

class LastFmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lastfm)
        FragmentsRouter.to(supportFragmentManager, ArtistListFragment())
    }
}
