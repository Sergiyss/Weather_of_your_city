package com.example.todaysweatherforecast.base

import android.content.Context
import android.view.View

interface BaseView {

    fun showView(view: View, isShown: Boolean) {
        view.visibility = if (isShown) View.VISIBLE else View.GONE
    }

    fun getContext(): Context
}