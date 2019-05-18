package com.example.todaysweatherforecast.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.todaysweatherforecast.R
import com.example.todaysweatherforecast.base.BaseFragment
import com.example.todaysweatherforecast.isOnline

class ProgressBarFragment : BaseFragment(){

    private var errorTextView : TextView? = null

    override fun init(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.fragment_show_progress_bar, container, false)

        println("Init ProgressBarFragment")
        errorTextView = root.findViewById<TextView>(R.id.fragment_show_progress_bar_text_view_error_message)

        return root
    }

    fun errorMessage(errorText : String){
        errorTextView?.visibility = View.VISIBLE
        errorTextView?.text = errorText
    }

    fun newInstance(): ProgressBarFragment {
        return this
    }
}