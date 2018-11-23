package com.vstankevich.lastfm.singer.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import by.synesis.common.adapters.RecyclerAdapter
import com.vstankevich.lastfm.FragmentsRouter
import com.vstankevich.lastfm.R
import com.vstankevich.lastfm.singer.Artist
import com.vstankevich.lastfm.singer.items.SingerItem
import com.vstankevich.lastfm.singer.viewmodel.SingerViewModel
import kotlinx.android.synthetic.main.fragment_signer.*


/**
 * Created by victor.stankevich on 11.07.2018.
 */
class ArtistListFragment : Fragment() {

    private val signerViewModel: SingerViewModel by lazy {
        ViewModelProviders.of(activity!!).get(SingerViewModel::class.java)
    }

    private var searchView: SearchView? = null
    private var queryTextListener: SearchView.OnQueryTextListener? = null

    private lateinit var adapter: RecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signer, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        singers.layoutManager = LinearLayoutManager(context!!)
        adapter = RecyclerAdapter(context!!, arrayListOf()) {
            it as SingerItem
            FragmentsRouter.to(activity?.supportFragmentManager!!,
                               ArtistDetailsFragment.newInstance(it.artist.mbid),
                               ArtistDetailsFragment.TAG)
        }
        singers.adapter = adapter

        signerViewModel.name.observe(this, Observer<List<Artist>> { it?.let { setSingerName(it) } })
        signerViewModel.loadTopArtists()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchManager = activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView = searchItem?.actionView as SearchView
        searchView?.let {
            it.setSearchableInfo(searchManager.getSearchableInfo(activity!!.componentName))
            queryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean = true

                override fun onQueryTextSubmit(query: String): Boolean {
                    signerViewModel.searchArtistByName(query)
                    return true
                }
            }
            it.setOnQueryTextListener(queryTextListener)
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search ->
                return false
        }
        searchView?.setOnQueryTextListener(queryTextListener)
        return super.onOptionsItemSelected(item)
    }

    private fun setSingerName(artists: List<Artist>) {
        adapter.updateAdapter(SingerItem.generateItems(artists))
    }

    override fun onDestroy() {
        super.onDestroy()
        signerViewModel.clear()
    }
}