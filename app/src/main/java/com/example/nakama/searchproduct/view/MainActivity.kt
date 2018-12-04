package com.example.nakama.searchproduct.view

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.example.nakama.searchproduct.R
import org.koin.android.ext.android.setProperty


class MainActivity : AppCompatActivity() {

    private val tag = this::class.java.simpleName

    private val viewModel: ListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sourceId = intent.getStringExtra("sourceId")
        setProperty("sourceId", sourceId)

        viewModel.uiData.observe(this, Observer { updateUi(it!!) })

        viewModel.isLoading.observe(this, Observer {
            it?.let { progressBar.visibility = if (it) View.VISIBLE else View.GONE } })

        viewModel.isError.observe(this, Observer {
            Log.d(tag, "isError $it")
        })

        viewModel.update()
        initUi()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            menuInflater.inflate(R.menu.main_menu, menu)

            val searchMenu = menu.findItem(R.id.action_search)
            (searchMenu.actionView as SearchView).setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.search(query)
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun initUi() {
        newsSourceList.adapter = ListSourceNewsRecyclerViewAdapter()
        newsSourceList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun updateUi(newData: ResultListUiModel) {
        val status = newData.status
        if (status.equals(Constants.OK, true)) {
            (newsSourceList.adapter as ListSourceNewsRecyclerViewAdapter).updateData(newData.articles!!)

            val title = newData.articles?.get(0)?.sourceName
            (this as AppCompatActivity).supportActionBar?.title = title
        }
    }

    inner class ListSourceNewsRecyclerViewAdapter : RecyclerView.Adapter<ListSourceNewsRecyclerViewAdapter.ViewHolder>() {

        val data = mutableListOf<ListUiModel>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
        }

        override fun getItemCount(): Int {
            println("++ size data = ${data.size}")
            return data.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.articleTitle.text = data[position].title

            if (data[position].urlToImage.isNullOrEmpty()) {
                holder.itemView.img.visibility = View.GONE
            } else {
                holder.itemView.img.visibility = View.VISIBLE
                GlideApp.with(this@ListActivity)
                    .load(data[position].urlToImage)
                    .centerCrop()
                    .into(holder.itemView.img)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                holder.itemView.articleDesc.text = Util.convertDateTimeToFormattedTime(data[position].publishedAt)
            } else {
                holder.itemView.articleDesc.text = data[position].description
            }

            holder.itemView.setOnClickListener {
                println("ID = ${data[position].url}")
                val intent = Intent(this@ListActivity, DetailActivity::class.java)
                intent.putExtra("url", data[position].url)
                intent.putExtra("sourceName", data[position].sourceName)
                startActivity(intent)
            }
        }

        fun updateData(newData: List<ListUiModel>) {
            data.clear()
            data.addAll(newData)
            notifyDataSetChanged()
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    }
}
