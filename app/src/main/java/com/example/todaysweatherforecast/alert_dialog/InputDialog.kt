package com.example.todaysweatherforecast.alert_dialog

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.widget.EditText
import com.example.todaysweatherforecast.R
import kotlinx.android.synthetic.main.input_dialog_box.view.*

class InputDialog{

    private lateinit var listenerInputDialog: ListenerInputDialog

    interface ListenerInputDialog{
        fun send(nameCity : String)
        fun cancel()
    }

    fun initIistenerInputDialog(listenerInputDialog: ListenerInputDialog){
        this.listenerInputDialog = listenerInputDialog
    }

    fun showInputDialog(act : Activity) : AlertDialog {
        val layoutInflaterAndroid = LayoutInflater.from(act)
        val mView = layoutInflaterAndroid.inflate(R.layout.input_dialog_box, null)
        val alertDialogBuilderUserInput = AlertDialog.Builder(act)
        alertDialogBuilderUserInput.setView(mView)

        val userInputDialogEditText = mView.findViewById(R.id.userInputDialog) as EditText
        alertDialogBuilderUserInput
            .setCancelable(false)
            .setPositiveButton("Сохранить", object : DialogInterface.OnClickListener {
                override fun onClick(dialogBox: DialogInterface, id: Int) {
                    listenerInputDialog.send(
                        mView.userInputDialog.getText().toString()
                    )
                }
            })

            .setNegativeButton("Закрыть",
                object : DialogInterface.OnClickListener {
                    override fun onClick(dialogBox: DialogInterface, id: Int) {
                        dialogBox.cancel()
                        listenerInputDialog.cancel()
                    }
                })

        return alertDialogBuilderUserInput.create()
    }
}