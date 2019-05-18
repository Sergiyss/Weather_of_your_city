package com.example.todaysweatherforecast.mvp

import com.example.todaysweatherforecast.base.BaseView

interface BaseMvpPresenter<V: BaseView> {
    var isAttached: Boolean
    fun attach(view: V)
    fun detach()

}