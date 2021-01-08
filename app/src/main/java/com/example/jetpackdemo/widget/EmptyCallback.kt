package com.example.jetpackdemo.widget

import com.example.jetpackdemo.R
import com.kingja.loadsir.callback.Callback

class EmptyCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_empty
    }

}