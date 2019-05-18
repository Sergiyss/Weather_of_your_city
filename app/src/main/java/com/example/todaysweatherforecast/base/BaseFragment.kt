package com.example.todaysweatherforecast.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment(), BaseFragmentInterface {

    private lateinit var myView : View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = init(inflater, container, savedInstanceState)
        return myView
    }

    protected abstract fun init(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View

    override fun getViews(): View {
        return myView
    }
}