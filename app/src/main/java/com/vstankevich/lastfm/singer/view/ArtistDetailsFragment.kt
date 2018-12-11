package com.vstankevich.lastfm.singer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vstankevich.lastfm.FragmentsRouter
import com.vstankevich.lastfm.GlideApp
import com.vstankevich.lastfm.R
import com.vstankevich.lastfm.singer.Artist
import com.vstankevich.lastfm.singer.DataState
import com.vstankevich.lastfm.singer.ImageSize
import com.vstankevich.lastfm.singer.viewmodel.SingerDetailsViewModel
import kotlinx.android.synthetic.main.fragment_singer_details.*

/**
 * victor.stankevich
 *  16.11.2018.
 */
class ArtistDetailsFragment: Fragment() {

    private val signerDetailsViewModel: SingerDetailsViewModel by lazy {
        ViewModelProviders.of(this).get(SingerDetailsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_singer_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signerDetailsViewModel.artist.observe(this, Observer<Artist>{it?.let { initArtistDetails(it)}})
        signerDetailsViewModel.dataState.observe(this, Observer<DataState> {updateProgressBar(it)})
        signerDetailsViewModel.getDetailsById(arguments!!.getString(ARTIST_ID))

        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.setSupportActionBar(detailsToolbar)
        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appCompatActivity.supportActionBar?.setDisplayShowHomeEnabled(true)
        setHasOptionsMenu(true)
    }

    private fun initArtistDetails(artist: Artist) {
        GlideApp.with(context!!)
                .asBitmap()
                .placeholder(R.drawable.ic_square_placeholder)
                .error(R.drawable.ic_square_placeholder)
                .load(artist.image?.find { it.size == ImageSize.EXTRALARGE.name.toLowerCase()}?.url)
                .into(photo)

        biography.text = artist.bio?.content
        toolbarLayout.title = artist.name
    }

    override fun onDestroyView() {
        super.onDestroyView()
        signerDetailsViewModel.artist.removeObservers(this)
        signerDetailsViewModel.clear()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> FragmentsRouter.back(activity!!.supportFragmentManager)
        }
        return true
    }

    private fun updateProgressBar(state: DataState) {
        when(state) {
            DataState.START -> progressBar.visibility = View.VISIBLE
            DataState.SUCCESS, DataState.ERROR -> progressBar.visibility = View.GONE
        }
    }

    companion object {
        private const val ARTIST_ID = "artist_id"
        val TAG = ArtistDetailsFragment::class.java.simpleName

        fun newInstance(id: String): ArtistDetailsFragment = ArtistDetailsFragment().apply {
            arguments = Bundle().apply {
                putString(ARTIST_ID, id)
            }
        }
    }
}