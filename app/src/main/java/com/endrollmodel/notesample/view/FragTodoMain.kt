package com.endrollmodel.notesample.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.endrollmodel.notesample.R
import com.endrollmodel.notesample.base.BaseFragment
import com.endrollmodel.notesample.base.BaseTabAdapter
import com.endrollmodel.notesample.config.BaseConfig
import com.endrollmodel.notesample.config.BaseValue
import com.endrollmodel.notesample.databinding.FragTodoMainBinding
import com.endrollmodel.notesample.viewmodel.FragTodoMainVM
import com.google.android.material.tabs.TabLayoutMediator

class FragTodoMain : BaseFragment() {

    private lateinit var vm: FragTodoMainVM
    private lateinit var binding : FragTodoMainBinding
    private lateinit var baseTabAdapter: BaseTabAdapter
    private lateinit var pageList : MutableList<Fragment>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_todo_main, container, false)
        init()
        return binding.root
    }

    private fun init() {
        binding.mBaseLineTab.setGuidelinePercent(BaseValue.tabHeightBaseLine) //控制tab佔多少比例
        pageList = ArrayList()
        pageList.add(FragTodoPage.newInstance(BaseConfig.TODO_LIST_UNDONE)) // 未完成
        pageList.add(FragTodoPage.newInstance(BaseConfig.TODO_LIST_DONE))  // 完成
        pageList.add(FragTodoPage.newInstance(BaseConfig.TODO_LIST_ALL)) // 全部
//        binding.mViewPager.offscreenPageLimit = pageList.size
        baseTabAdapter = BaseTabAdapter(this, pageList)
        binding.mViewPager.adapter = baseTabAdapter
        TabLayoutMediator(binding.mTabLayout, binding.mViewPager) { tab, position ->
            tab.text = BaseConfig.todoListTab[position]
        }.attach()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm = ViewModelProvider(this)[FragTodoMainVM::class.java]
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FragTodoMain().apply {
                arguments = Bundle().apply {
                }
            }
    }
}