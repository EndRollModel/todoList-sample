package com.endrollmodel.notesample.view

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.endrollmodel.notesample.R
import com.endrollmodel.notesample.adapter.TodoPageAdapter
import com.endrollmodel.notesample.base.BaseFragment
import com.endrollmodel.notesample.config.BaseConfig
import com.endrollmodel.notesample.config.BaseValue
import com.endrollmodel.notesample.databinding.FragTodoMainV2Binding
import com.endrollmodel.notesample.room.entity.TodoEntity
import com.endrollmodel.notesample.viewmodel.FragTodoListVM
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FragTodoMainV2 : BaseFragment() {
    // v2因為會重複讀取產生錯誤 目前暫時放置
    private lateinit var binding: FragTodoMainV2Binding
    private lateinit var viewModel: FragTodoListVM
    private lateinit var adapter: TodoPageAdapter
    private lateinit var dataList: MutableList<TodoEntity>
    private lateinit var navController: NavController
    private lateinit var navigation: NavigationView
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_todo_main_v2, container, false)
        viewModel = ViewModelProvider(this).get(FragTodoListVM::class.java)

        setObserve()
        return binding.root
    }

    private fun setObserve() {
        viewModel.todoList.observe(viewLifecycleOwner, {
            Log.e( "setObserve: ", it.toString())
            // 回傳資訊時
            dataList.clear()
            dataList.addAll(it!!)
            adapter.notifyDataSetChanged()
        })
    }

    private fun init() {
        dataList = ArrayList()
        adapter = TodoPageAdapter(0, navController, viewModel)
        adapter.items = dataList
        val manager = GridLayoutManager(context, BaseValue.todoGridCount)
        binding.mRecycler.layoutManager = manager
        binding.mRecycler.adapter = adapter
//        viewModel.getTodoData()
        binding.mFAButton.setOnClickListener {
//            viewModel.addTodoData(
//                TodoEntity(
//                    null,
//                    "我是標題${dataList.size + 1}",
//                    "我是內容${dataList.size + 1}",
//                    "",
//                    "",
//                    "",
//                    "#33faccd0",
//                    0
//                )
//            )
        }
        navigation = binding.mNavView
        drawerLayout = binding.mDrawLay
        binding.mOpenDraw.setOnClickListener {
            binding.mDrawLay.openDrawer(Gravity.LEFT)
        }
        binding.mNavView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuSetting -> { // 設定
                    binding.mDrawLay.closeDrawer(Gravity.LEFT)
                    GlobalScope.launch {
                        delay(200)
                        navController.navigate(R.id.action_fragTodoMainV2_to_fragSetting)
                    }
                }
                R.id.menuInfo -> { // 資訊
                    binding.mDrawLay.closeDrawer(Gravity.LEFT)
                    GlobalScope.launch {
                        delay(200)
                        navController.navigate(R.id.action_fragTodoMainV2_to_fragInfo)
                    }
                }
                else -> {
                }
            }
            false
        }

        val spinnerAdapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, BaseConfig.todoListTab) }
        spinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.mSpinner.adapter = spinnerAdapter
        binding.mSpinner.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                Log.e(TAG, "onItemSelected選了${position} " )
                when (position) {
                    0, 1 -> {
                        viewModel.getTodoData(position)
                    }
                    2 -> {
                        viewModel.getTodoData()
                    }
                    else -> {
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        init()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FragTodoMainV2().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onDestroy() {
        Log.e(TAG, "onDestroy: " )
        super.onDestroy()
    }
}