package com.vstankevich.lastfm.singer.items

import android.content.Context
import android.graphics.Bitmap
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.vstankevich.lastfm.GlideApp
import com.vstankevich.lastfm.R
import com.vstankevich.lastfm.RecyclerItem
import com.vstankevich.lastfm.singer.Artist
import com.vstankevich.lastfm.singer.ImageSize


/**
 * victor.stankevich
 *  30.10.2018.
 */
class SingerItem(val artist: Artist) : RecyclerItem {

    private lateinit var singerName: TextView
    private lateinit var singerImage: ImageView

    override fun getLayoutId(): Int = R.layout.item_singer

    override fun renderView(context: Context, view: View) {
        singerName.text = artist.name
        val image = artist.image.find { it.size == ImageSize.MEDIUM.name.toLowerCase() }
        GlideApp.with(context)
                .asBitmap()
                .centerCrop()
                .load(image?.url)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(RoundedImageTarget(singerImage, context))
    }

    override fun initView(context: Context, view: View) {
        singerName = view.findViewById(R.id.name)
        singerImage = view.findViewById(R.id.image)
    }

    inner class RoundedImageTarget(private val imageView: ImageView, val context: Context)
        : BitmapImageViewTarget(imageView) {
        override fun setResource(resource: Bitmap?) {
            val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources,
                                                                             resource)
            circularBitmapDrawable.isCircular = true
            imageView.setImageDrawable(circularBitmapDrawable)
        }
    }

    companion object {
        fun generateItems(artists: List<Artist>): List<SingerItem> = artists.map {
            SingerItem(it)
        }
    }
}