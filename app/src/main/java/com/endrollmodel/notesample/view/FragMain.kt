package com.endrollmodel.notesample.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.endrollmodel.notesample.R
import com.endrollmodel.notesample.base.BaseFragment
import com.endrollmodel.notesample.base.BaseTabAdapter
import com.endrollmodel.notesample.config.BaseConfig
import com.endrollmodel.notesample.config.BaseValue
import com.endrollmodel.notesample.databinding.FragMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class FragMain : BaseFragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var tabList : MutableList<Fragment>
    private lateinit var baseTabAdapter : BaseTabAdapter
    private lateinit var binding : FragMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_main, container, false)
        init()
        return binding.root
    }

    private fun init() {
        tabList = ArrayList()
        tabList.add(FragTodoMain.newInstance())
//        tabList.add(FragSetting.newInstance())
        baseTabAdapter = BaseTabAdapter(this, tabList)
        binding.mViewPager.isUserInputEnabled = false // 禁止滑動
        binding.mViewPager.adapter = baseTabAdapter
        TabLayoutMediator(binding.mTab, binding.mViewPager){ tab, position->
            tab.text = BaseConfig.mainTitle[position]
        }.attach()
        binding.mBaseLine.setGuidelinePercent(1 - BaseValue.mainTabBaseLine)
//        binding.mTab.background = ThemeUtil().gradientStyle(ContextCompat.getColor(binding.root.context, R.color.colorThemePinkSelect), 10, 10, 0, 0);
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragMain().apply {arguments = Bundle().apply {}}
    }
}