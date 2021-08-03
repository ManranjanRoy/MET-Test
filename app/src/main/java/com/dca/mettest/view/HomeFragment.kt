package com.dca.mettest.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.dca.mettest.R
import com.dca.mettest.adaptor.productListAdapter
import com.dca.mettest.model.catagorymodel
import com.dca.mettest.viewmodel.MainActivityViewmodel
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    var position=0
    var catagorymodellist:List<catagorymodel>?=null
    lateinit var viewmodel: MainActivityViewmodel
    private val productadaptor = productListAdapter(arrayListOf())
    lateinit var recyclerView:RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_home, container, false)
        position= getArguments()!!.getInt("position")
       // Toast.makeText(context,position.toString(),Toast.LENGTH_LONG).show( )
        viewmodel= ViewModelProviders.of(this).get(MainActivityViewmodel::class.java)
        viewmodel.refresh(context)
        recyclerView=view.findViewById(R.id.productList);
        recyclerView.layoutManager=LinearLayoutManager(context)
        recyclerView.adapter=productadaptor
        viewmodel.catagorymodel.observe(this, Observer {catagorymodel ->
            catagorymodel?.let {
                catagorymodellist = it
                recyclerView.visibility = View.VISIBLE
                productadaptor.updateitemslist(it.get(position).item)
            }
        })
        viewmodel.LoadError.observe(this, Observer { isError ->
            isError?.let { list_error.visibility = if(it) View.VISIBLE else View.GONE }
        })
        viewmodel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loading_view.visibility = if(it) View.VISIBLE else View.GONE
                if(it) {
                    list_error.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                }
            }
        })


        return view
    }
    companion object {
        fun newInstance(a: Int): HomeFragment {
            var fragment = HomeFragment()
            val args = Bundle()
            args.putInt("position", a)
            fragment.setArguments(args)
            Log.d("testno", a.toString())
            return fragment
        }
    }
}