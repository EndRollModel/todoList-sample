package com.endrollmodel.notesample.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.endrollmodel.notesample.R
import com.endrollmodel.notesample.base.BaseViewHolder
import com.endrollmodel.notesample.databinding.AdapterItemColorSelectBinding
import com.endrollmodel.notesample.viewmodel.FragTodoInfoVM
import kotlin.properties.Delegates

class TodoInfoColorAdapter : RecyclerView.Adapter<BaseViewHolder>(){

    var checkIndex = -1
    var viewModel: FragTodoInfoVM? = null
    var colorList : List<String> by Delegates.observable(emptyList()){
        _, oldValue, newValue ->
        notifyItemChanged(0, newValue.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_item_color_select, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val binding = holder.binding as AdapterItemColorSelectBinding
        binding.mInLayout.setBackgroundColor( Color.parseColor(colorList[position]) )
        binding.mLayout.setOnClickListener{
            viewModel?.changeBackground(colorList[position])
            checkIndex = position
        }
    }

    override fun getItemCount(): Int {
        return colorList.size
    }

}