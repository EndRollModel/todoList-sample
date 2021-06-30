package com.endrollmodel.notesample.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.endrollmodel.notesample.R
import com.endrollmodel.notesample.base.BaseViewHolder
import com.endrollmodel.notesample.data.TodoCheck
import com.endrollmodel.notesample.databinding.AdapterTodoCheckItemBinding
import com.endrollmodel.notesample.util.AutoUpdatableAdapter
import kotlin.properties.Delegates

class TodoCheckItemAdapter : RecyclerView.Adapter<BaseViewHolder>(), AutoUpdatableAdapter{

    private lateinit var binding: AdapterTodoCheckItemBinding
//    lateinit var fdService : FileDownloadService

    var checkItems: List<TodoCheck> by Delegates.observable(emptyList()){
        _, oldValue, newValue ->
//        autoNotify(oldValue, newValue){ oldValue, n -> oldValue == n}
//        Log.e( "getclass","資料變更${checkItems.size}")
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_todo_check_item, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        binding = holder.binding as AdapterTodoCheckItemBinding
        Log.e( "onBindViewHolder:", "${binding.mLayout.height}" )
        checkItems[position].text?.let {
            binding.mEdText.setText(it)
        }
        checkItems[position].checked?.let {
            binding.mCheckBox.isChecked = it
        }
        binding.mCheckBox.setOnClickListener{
            checkItems[position].checked != (checkItems[position].checked)
        }
        binding.mIvClear.setOnClickListener {
            (checkItems as MutableList).removeAt(position)
            this.notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return checkItems.size
    }
}