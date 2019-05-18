package com.example.todaysweatherforecast.base

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todaysweatherforecast.base.BaseFragment

abstract class BaseRvFragment : BaseFragment(){

    abstract fun setAdapter() : RecyclerView.Adapter<*>

    fun initRV(mRecyclerview : RecyclerView, mLayoutManager : LinearLayoutManager) {

        mRecyclerview.apply {
            adapter = setAdapter()
            layoutManager = mLayoutManager
        }

    }
}