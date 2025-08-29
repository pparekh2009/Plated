package com.priyanshparekh.core.utils

import android.view.View

interface OnRvItemClickListener {

    fun onClick(position: Int, view: View) {}

    fun onClick(position: Int) {}

}