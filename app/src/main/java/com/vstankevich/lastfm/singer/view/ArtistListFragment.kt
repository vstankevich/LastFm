package com.vstankevich.lastfm.singer.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.vstankevich.lastfm.R
import com.vstankevich.lastfm.singer.viewmodel.SingerViewModel
import kotlinx.android.synthetic.main.fragment_signer.view.*

/**
 * Created by victor.stankevich on 11.07.2018.
 */
class SignerListFragment : Fragment() {

    private val signerViewModel: SingerViewModel by lazy {
        ViewModelProviders.of(activity!!).get(SingerViewModel::class.java)
    }

    var signerName: TextView?  = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_signer, container, false)
        signerName = view.findViewById<TextView>(R.id.fs_signer_name)
        signerViewModel.loadData("Led Zeppelin")
        signerViewModel.name.observe(this, Observer<String> {this.setSingerName(it!!)})
        return view
    }

    private fun setSingerName(name: String) {
        signerName?.text = name
    }
}