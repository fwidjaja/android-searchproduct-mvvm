package com.example.nakama.searchproduct.view

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.nakama.searchproduct.R
import com.example.nakama.searchproduct.model.ui.DataItemUiModel
import com.example.nakama.searchproduct.model.ui.ResultSearchUiModel
import com.example.nakama.searchproduct.util.Constants
import com.example.nakama.searchproduct.util.GlideApp
import com.example.nakama.searchproduct.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val tag = this::class.java.simpleName

    private val viewModel: SearchViewModel by viewModel()

    private val itemAdapter = ListItemRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            menuInflater.inflate(R.menu.main_menu, menu)

            val searchMenu = menu.findItem(R.id.action_search)
            (searchMenu.actionView as SearchView).setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.search(query, 1)

                    viewModel.uiData.observe(this@MainActivity, Observer { updateUi(it!!) })

                    viewModel.isLoading.observe(this@MainActivity, Observer {
                        it?.let { progressBar.visibility = if (it) View.VISIBLE else View.GONE } })

                    viewModel.isError.observe(this@MainActivity, Observer {
                        Log.d(tag, "isError $it")
                    })
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun updateUi(newData: ResultSearchUiModel) {
        val status = newData.status?.message

        itemList.apply {
            layoutManager = GridLayoutManager(
                    this@MainActivity,
                    2
            )
            adapter = itemAdapter
        }

        itemList.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount = itemList.layoutManager?.itemCount
                val visibleItemCount = itemList.childCount
                Log.d(tag, "++ onScrolled! - totalItemCount = $totalItemCount, visibleItemCount = $visibleItemCount")
            }
        })

        if (status.equals(Constants.OK, true)) {
            itemAdapter.updateData(newData.datas!!)
        }
    }

    inner class ListItemRecyclerViewAdapter : RecyclerView.Adapter<ListItemRecyclerViewAdapter.ViewHolder>() {

        val data = mutableListOf<DataItemUiModel>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
        }

        override fun getItemCount(): Int {
            println("++ size data = ${data.size}")
            return data.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.itemName.text = data[position].itemName
            holder.itemView.itemPrice.text = data[position].itemPrice

            if (data[position].itemImg.isNullOrEmpty()) {
                holder.itemView.itemImg.visibility = View.GONE
            } else {
                holder.itemView.itemImg.visibility = View.VISIBLE

                GlideApp.with(this@MainActivity)
                    .load(data[position].itemImg)
                    .centerCrop()
                    .into(holder.itemView.itemImg)
            }
        }

        fun updateData(newData: List<DataItemUiModel>) {
            data.clear()
            data.addAll(newData)
            notifyDataSetChanged()
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    }
}
