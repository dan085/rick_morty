package com.betterfly.test.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.betterfly.test.ui.model.Item
import com.betterfly.test.R
import com.betterfly.test.domain.ItemUseCase
import com.betterfly.test.ui.home.adapter.ItemAdapterRecycler
import com.betterfly.test.ui.home.adapter.LoadMore.RecyclerViewItemsScrollListener
import com.betterfly.test.ui.home.adapter.OnItemClickListenerItemAdapterRecycler
import com.betterfly.test.ui.showItem.MainActivityShowItem
import com.betterfly.test.viewModel.ListViewModel
import com.betterfly.test.viewModel.ListViewModelFactory

///Clase Main de la aplicación
class MainActivityHome : AppCompatActivity(),OnItemClickListenerItemAdapterRecycler {

    private lateinit var viewModel: ListViewModel
    private lateinit var listItems:RecyclerView
    private lateinit var swipeRefreshLayout_items: SwipeRefreshLayout
    private lateinit var emptyList: RelativeLayout

    private lateinit var adapter:ItemAdapterRecycler
    private var pageInitial:Int = 1  ///Pagina Inicial para
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listItems = findViewById(R.id.list_items);
        listItems.layoutManager = LinearLayoutManager(this)
        listItems.addOnScrollListener(mRecyclerViewScrollListener)// esto permite activar un scroll infinito
        swipeRefreshLayout_items = findViewById(R.id.swipeRefreshLayout_items)
        emptyList = findViewById(R.id.empty_list)
        setUpSwipeRefreshLayout(swipeRefreshLayout_items)

        setupViewModelAndObserve()
        swipeRefreshLayout_items.isRefreshing = true
        viewModel.getListItems(this,this.pageInitial)//pagina 1

        swipeRefreshLayout_items.setOnRefreshListener {
            viewModel.getListItems(this,this.pageInitial)//pagina 1
        }
    }


///Listener encargado de el scroll infinito
    var mRecyclerViewScrollListener: RecyclerViewItemsScrollListener = object : RecyclerViewItemsScrollListener() {
        override fun onScrollUp() {}
        override fun onScrollDown() {}
        override fun onLoadMore() {
            try {
                adapter.showLoading(true)
                adapter.notifyItemChanged(adapter.itemCount - 1)
                viewModel.getListItems(this@MainActivityHome, pageInitial)//pagina 1
            }catch(ee:java.lang.IllegalStateException){
                //prevenir un error en el estado cuando carga mas items
                ee.printStackTrace()
                viewModel.getListItems(this@MainActivityHome, pageInitial)//pagina 1
            }
        }
    }

    ///Inicializador diseño de SwipeRefreshLayout
    fun  setUpSwipeRefreshLayout(swipeRefreshLayout: SwipeRefreshLayout){
        swipeRefreshLayout.setColorSchemeResources(R.color.plomo,
            R.color.green_minimalist,
            R.color.green_android,
            R.color.green_second)
    }

    ///Inicializador de ViewModel
    fun setupViewModelAndObserve() {
        viewModel = ViewModelProvider(this, ListViewModelFactory(ItemUseCase())).get(ListViewModel::class.java)
        val ItemsObserver = Observer<ArrayList<Item>> {
            swipeRefreshLayout_items.isRefreshing = false
            when {
                this.pageInitial>1 -> {

                    try {
                        adapter.add_more(it)
                        adapter.showLoading(false)
                        mRecyclerViewScrollListener.onDataCleared()
                        adapter.notifyItemChanged(adapter.itemCount - 1)
                        this.pageInitial = this.pageInitial + 1
                    } catch (ee: NullPointerException) {
                        ee.printStackTrace()
                    } catch (ee: IllegalArgumentException) {
                        ee.printStackTrace()
                    } catch (ee: java.lang.IllegalStateException) {
                        ee.printStackTrace()
                    }

                }
                else -> {

                    when {
                        it.size>0 -> {
                            this.emptyList.visibility= View.GONE
                            this.pageInitial++
                        }
                        else -> {
                            this.emptyList.visibility= View.VISIBLE
                        }
                    }

                    this.adapter = ItemAdapterRecycler(it, MainActivity@ this)
                    listItems.adapter = this.adapter
                }
            }
        }
        viewModel.getListItemsLiveData().observe(this, ItemsObserver)
    }

    override fun LongClick(data_local: Item, position: Int, ctx: Context) {
        Log.d("LongClick Item","OK")
    }

    override fun OnClick(data_local: Item, position: Int, ctx: Context) {
       Log.d("OnClick Item","OK")

        val intent = Intent(this, MainActivityShowItem::class.java)
        intent.putExtra("data", data_local);
        this.startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
          menuInflater.inflate(R.menu.main_null, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)

        }
    }
}