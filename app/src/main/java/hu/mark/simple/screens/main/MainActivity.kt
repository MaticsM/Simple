package hu.mark.simple.screens.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.database.MatrixCursor
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.provider.BaseColumns
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.widget.FilterQueryProvider
import hu.mark.simple.R
import hu.mark.simple.databinding.ActivityMainBinding
import hu.mark.simple.screens.main.adapter.LiveSearchAdapter
import hu.mark.simple.screens.main.adapter.MainAdapter
import hu.mark.simple.screens.main.items.MainItemViewModel
import hu.mark.simple.util.Constants
import hu.mark.simple.util.Util
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val vm = MainViewModel()
    private val adapterList = MainAdapter()

    private var searchView: SearchView? = null

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (context != null && intent != null) {
                val status = Util.isDeviceOnline(context)
                if (Constants.CONNECTIVITY_CHANGED == intent.action) {
                    if (status) {
                        fetchData()
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        fetchData()
    }

    private fun fetchData() {
        if (vm.dataList == null || vm.dataList?.size == 0) {
            vm.getSimpleRepositories()
        }
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.data = vm

        setSupportActionBar(binding.tbMain)

        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapterList

        binding.srlMain.setOnRefreshListener {
            searchView!!.setQuery("", false)
            searchView!!.clearFocus()
            vm.getSimpleRepositories()
            setupSearchAdapter()
        }
    }

    private fun populateAdapter(query: String): Cursor {
        val tempData: ArrayList<String> = ArrayList()
        val liveData: ArrayList<String> = ArrayList()

        for (item in adapterList.data) {
            tempData.add((item.vm as MainItemViewModel).data.name)
        }

        for (item in tempData) {
            if (!liveData.contains(item)) {
                liveData.add(item)
            }
        }

        val c = MatrixCursor(arrayOf(BaseColumns._ID, Constants.FILTER))
        for (i in 0 until liveData.size) {
            if (liveData[i].toLowerCase().startsWith(query.toLowerCase()))
                c.addRow(arrayOf(i, liveData[i]))
        }
        return c
    }

    private fun setupSearchAdapter() {
        val from: Array<String> = arrayOf(Constants.FILTER)
        val adapterCursor = LiveSearchAdapter(this, 0, null, from, null, 0)

        adapterCursor.filterQueryProvider = FilterQueryProvider {
            populateAdapter(it as String)
        }

        searchView!!.suggestionsAdapter = adapterCursor
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu?.findItem(R.id.menu_filter)

        if (searchItem != null) {
            searchView = searchItem.actionView as SearchView
            setupSearchAdapter()

            searchView!!.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
                override fun onSuggestionSelect(position: Int): Boolean {
                    return true
                }

                override fun onSuggestionClick(position: Int): Boolean {
                    val cursor = searchView!!.suggestionsAdapter.getItem(position) as Cursor
                    val suggestion = cursor.getString(cursor.getColumnIndex(Constants.FILTER))
                    searchView!!.setQuery(suggestion, false)
                    vm.filterData(suggestion)
                    searchView!!.clearFocus()
                    return true
                }

            })

            searchView!!.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchView!!.clearFocus()
                    vm.filterData(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    vm.filterData(newText)
                    return true
                }

            })
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        vm.clearDisposable()
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter(Constants.CONNECTIVITY_CHANGED)
        registerReceiver(networkReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkReceiver)
    }

}
