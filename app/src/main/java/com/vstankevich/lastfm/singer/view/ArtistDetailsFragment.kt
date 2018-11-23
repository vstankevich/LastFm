package com.vstankevich.lastfm.singer.view

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vstankevich.lastfm.GlideApp
import com.vstankevich.lastfm.R
import com.vstankevich.lastfm.singer.Artist
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
        signerDetailsViewModel.getDetailsById(arguments!!.getString(ARTIST_ID))
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