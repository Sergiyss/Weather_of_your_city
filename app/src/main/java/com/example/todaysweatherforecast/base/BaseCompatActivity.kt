package com.example.todaysweatherforecast.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseCompatActivity : AppCompatActivity(), BaseView {

    lateinit var loadActivity: LoadActivity

    interface LoadActivity{
        fun fullLoadActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
    }

    protected abstract fun init(savedInstanceState: Bundle?)

    override fun getContext(): Context = this

    fun initLoadActivity(loadActivity: LoadActivity){
        this.loadActivity = loadActivity
    }
}