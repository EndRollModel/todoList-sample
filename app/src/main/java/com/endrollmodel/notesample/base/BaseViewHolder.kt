package com.endrollmodel.notesample.base

import android.content.Context
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.endrollmodel.notesample.config.BaseConfig

class BaseViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    var binding = DataBindingUtil.bind<ViewDataBinding>(itemView)
    var sp = itemView.context.getSharedPreferences(BaseConfig.appName, Context.MODE_PRIVATE)!!
}