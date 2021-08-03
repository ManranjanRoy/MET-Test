package com.dca.mettest.view


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.dca.mettest.R
import com.dca.mettest.adaptor.FragmentAdapter
import com.dca.mettest.model.catagorymodel


import com.dca.mettest.viewmodel.MainActivityViewmodel


class MainActivity : AppCompatActivity() {
    lateinit var viewmodel: MainActivityViewmodel
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var catagorymodellist: List<catagorymodel>? = null
        viewmodel = ViewModelProviders.of(this).get(MainActivityViewmodel::class.java)
        viewmodel.refresh(applicationContext)


        tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        viewPager = findViewById<ViewPager>(R.id.viewPager)
        tabLayout!!.tabGravity = TabLayout.GRAVITY_CENTER
        tabLayout!!.tabMode = TabLayout.MODE_SCROLLABLE

        viewmodel.catagorymodel.observe(this, Observer { catagorymodel ->
            catagorymodel?.let {
                catagorymodellist = it
               tabcotrol(catagorymodellist!!)
            }
        })

    }

    fun tabcotrol(catagorymodellist: List<catagorymodel>) {
        for (i in 0 until catagorymodellist.size) {
            tabLayout!!.addTab(
                tabLayout!!.newTab().setText(catagorymodellist!!.get(i).cat_name)
            )
        }
        val adapter = FragmentAdapter(this, supportFragmentManager, tabLayout!!.tabCount)
        viewPager!!.adapter = adapter
        viewPager!!.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                tabLayout
            )
        )

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }
}