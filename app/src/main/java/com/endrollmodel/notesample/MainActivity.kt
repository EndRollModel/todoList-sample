package com.endrollmodel.notesample

import android.os.Bundle
import android.view.Gravity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.navigation.Navigation
import com.endrollmodel.notesample.base.BaseActivity
import com.endrollmodel.notesample.databinding.ActivityMainBinding
import com.endrollmodel.notesample.viewmodel.FragTodoListVM
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel : FragTodoListVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setContentView(this, R.layout.activity_main)
        init()
    }

    private fun init() {
        binding.mNavView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuSetting -> { // 設定
                    binding.mDrawLay.closeDrawer(Gravity.LEFT)
                    GlobalScope.launch {
                        delay(200)
                        try {
                            Navigation.findNavController(this@MainActivity, R.id.main_fragment).navigate(R.id.action_fragMain_to_fragSetting)
                        } catch (e: Exception) {
                        }
                    }
                }
                R.id.menuInfo -> { // 資訊
                    binding.mDrawLay.closeDrawer(Gravity.LEFT)
                    GlobalScope.launch {
                        delay(200)
                        try {
                            Navigation.findNavController(this@MainActivity, R.id.main_fragment).navigate(R.id.action_fragMain_to_fragInfo)
                        } catch (e: Exception) {
                        }
                    }
                }
                else -> {
                }
            }
            false
        }
    }
}
