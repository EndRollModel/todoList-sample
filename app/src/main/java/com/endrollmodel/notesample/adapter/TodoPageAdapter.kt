package com.endrollmodel.notesample.adapter

import android.app.AlertDialog
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.endrollmodel.notesample.R
import com.endrollmodel.notesample.base.BaseViewHolder
import com.endrollmodel.notesample.config.BaseConfig
import com.endrollmodel.notesample.config.BaseData
import com.endrollmodel.notesample.config.BaseValue
import com.endrollmodel.notesample.databinding.AdapterTodoItemBinding
import com.endrollmodel.notesample.room.entity.TodoEntity
import com.endrollmodel.notesample.util.AutoUpdatableAdapter
import com.endrollmodel.notesample.viewmodel.FragTodoListVM
import kotlin.properties.Delegates

/**
 * 待辦事務Adapter
 */
class TodoPageAdapter(
    private val action: Int,
    private val navController: NavController,
    private val viewModel: FragTodoListVM
) : RecyclerView.Adapter<BaseViewHolder>(), AutoUpdatableAdapter {
    override fun getItemCount(): Int {
        return items.size
    }

    var items: List<TodoEntity> by Delegates.observable(emptyList()){
            _, oldValue, newValue ->
        autoNotify(oldValue, newValue){ oldValue, n -> oldValue.todoId == n.todoId}
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder {
        return BaseViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_todo_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        // 動態高度 （我不知道為什麼要寫這個 不過看起來會整齊多）
        val binding = (holder.binding as AdapterTodoItemBinding)
        val sp : SharedPreferences = binding.root.context.getSharedPreferences(BaseConfig.appName, MODE_PRIVATE)
        val longItem = if (items[position].check == 0) BaseConfig.todoLongItemUndone else BaseConfig.todoLongItemDone
        val hand = holder.sp.getInt(BaseConfig.USER_HANDEDNESS, 0) // 慣用手
        // 設定item高度
        val screenHeight = holder.sp.getInt(BaseConfig.USER_SCREEN_HEIGHT, -1) // 螢幕高度
        val statusHeight = holder.sp.getInt(BaseConfig.USER_STATUS_HEIGHT, -1) // 狀態列高度
        // 計算layout高度 螢幕高度 - 狀態列高度 - tabLayout高度 - (CardView間隔 * 2)
        val itemCorrect =
            binding.root.context.resources.getDimension(R.dimen.todoItemCorrect) * 2 // item間隔(dp單位) 上下都有 所以要 * 2
        val itemCount = sp.getInt(BaseConfig.USER_TODO_ITEM_COUNT, BaseValue.todoItemCount) // 頁面要有幾個item
        val mainTabHeight = screenHeight * (BaseValue.tabHeightBaseLine)  // mainTabLayout的高度
        val todoTabHeight = (screenHeight - mainTabHeight) * (BaseValue.mainTabBaseLine)  // tabLayout
        val layoutHeight = screenHeight - (statusHeight) - (mainTabHeight) - (todoTabHeight) - (itemCorrect * itemCount)
        binding.mInLayout.layoutParams.height = (layoutHeight / (itemCount)).toInt()
        when (hand) { //慣用手
            BaseConfig.USER_HANDEDNESS_RIGHT -> {
            }
            BaseConfig.USER_HANDEDNESS_LEFT -> {
            }
            else -> {
            }
        }

        binding.mTextView.text = items[position].title
        when (items[position].check) {
            0 -> {
                binding.mImageCheck.visibility = View.GONE
                binding.mTextView.setTextColor(ContextCompat.getColor(binding.root.context, R.color.colorBlack))
            }
            1 -> {
                binding.mImageCheck.visibility = View.VISIBLE
                binding.mTextView.setTextColor(ContextCompat.getColor(binding.root.context, R.color.colorPrimary))
            }
            else -> {
            }
        }

        binding.mInLayout.setBackgroundColor(Color.parseColor(items[position].background))

        binding.mLayout.setOnClickListener {
            val bundle = Bundle()
            items[position].todoId?.let { it1 -> bundle.putInt(BaseConfig.TODO_ITEM_ID, it1) }
            bundle.putInt(BaseConfig.TODO_LIST_ACTION, action)
            bundle.putString(BaseConfig.TODO_ITEM_TITLE, items[position].title)
            bundle.putString(BaseConfig.TODO_ITEM_TEXT, items[position].text)
            bundle.putString(BaseConfig.TODO_ITEM_BACKGROUND, items[position].background)
            bundle.putString(BaseConfig.TODO_ITEM_DATE, items[position].date)
            bundle.putString(BaseConfig.TODO_ITEM_CHECK_ITEM, items[position].checkItem)
            bundle.putString(BaseConfig.TODO_ITEM_DONE_DATE, items[position].doneDate)
            bundle.putInt(BaseConfig.TODO_ITEM_CHECK, items[position].check!!)
            ViewCompat.setTransitionName(binding.mTextView, "todoItem${action}${items[position].todoId}")
            ViewCompat.setTransitionName(binding.mLayout, "todoLayout${action}${items[position].todoId}")
            Log.e( "onBindViewHolder: ", "todoItem${action}${items[position].todoId}" )
            val extras = FragmentNavigatorExtras(binding.mTextView to "todoItem${action}${items[position].todoId}" , binding.mLayout to "todoLayout${action}${items[position].todoId}")
            try {
                navController.navigate(R.id.action_fragMain_to_todoInfo, bundle, null, extras)
//                navController.navigate(R.id.action_fragPageTest_to_todoInfo, bundle, null, extras)
            } catch (e: Exception) {
            }
        }

        binding.mLayout.setOnLongClickListener {
            val alertDialog = AlertDialog.Builder(binding.root.context).setItems(longItem) { _, i ->
                when (i) {
                    // 切換是否完成
                    0->
                    items[position].todoId?.let { itemId ->
                        when (items[position].check) {
                            0 -> {
                                viewModel.chargeCheckStatus(
                                    action, BaseData.TODO_CHECK_DONE, itemId, position
                                )
                            }
                            1 -> {
                                viewModel.chargeCheckStatus(
                                    action, BaseData.TODO_CHECK_UNDONE, itemId, position
                                )
                            }
                            else -> {
                            }
                        }
                    }
                    // 刪除
                    1 -> items[position].todoId?.let {
                        Toast.makeText(binding.root.context, position.toString(), Toast.LENGTH_SHORT).show()
                        viewModel.deleteTodoData(it, position)
                    }
                    else->{}
                }
            }.create()
            alertDialog.show()
            true
        }
    }
}