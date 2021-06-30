package com.endrollmodel.notesample.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.endrollmodel.notesample.MainActivity
import com.endrollmodel.notesample.R
import com.endrollmodel.notesample.base.BaseFragment
import com.endrollmodel.notesample.config.BaseConfig
import com.endrollmodel.notesample.databinding.FragSettingBinding

class FragSetting : BaseFragment() {

    private lateinit var binding: FragSettingBinding

    private val navController : NavController by lazy {
        findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_setting, container, false)
        init()
        return binding.root
    }

    private fun init() {
        binding.mToolbar.setNavigationOnClickListener { navController.popBackStack() }
        binding.includeColorLayout.includeTitle.text = "主題顏色"
//        binding.includeColorLayout.includeColor.visibility = View.VISIBLE
//        binding.includeColorLayout.includeColor.setBackgroundColor(ContextCompat.getColor(requireContext() ,R.color.colorThemePink))
        binding.includeColorLayout.includeInfo.text = sp?.getString(BaseConfig.USER_THEME_COLOR, "系統預設")
        binding.includeColorLayout.mLayout.setOnClickListener{
            AlertDialog.Builder(requireContext()).setItems(BaseConfig.themeNameList){ _, pos ->
                sp!!.edit().putString(BaseConfig.USER_THEME_COLOR, BaseConfig.themeNameList[pos]).apply()
                sp!!.edit().putInt(BaseConfig.USER_THEME, BaseConfig.themeList[BaseConfig.themeNameList[pos]]!!).apply()
                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }.show()
        }
        binding.includeWidthLayout.includeTitle.text = "一列顯示數量"
        binding.includeWidthLayout.includeInfo.visibility = View.VISIBLE
        binding.includeWidthLayout.includeInfo.text = sp?.getInt(BaseConfig.USER_TODO_GRID_COUNT, -1).toString()
        binding.includeWidthLayout.mLayout.setOnClickListener{
            AlertDialog.Builder(requireContext()).setItems(BaseConfig.rowCountList){ _, pos ->
                sp!!.edit().putInt(BaseConfig.USER_TODO_GRID_COUNT, pos + 1).apply()
                binding.includeWidthLayout.includeInfo.text = BaseConfig.rowCountList[pos]
            }.show()
        }
        binding.includeItemCount.includeTitle.text = "一行顯示數量"
        binding.includeItemCount.includeInfo.visibility = View.VISIBLE
        binding.includeItemCount.includeInfo.text = sp?.getInt(BaseConfig.USER_TODO_ITEM_COUNT, -1).toString()
        binding.includeItemCount.mLayout.setOnClickListener{
            AlertDialog.Builder(requireContext()).setItems(BaseConfig.columnCountList){ _, pos ->
                sp!!.edit().putInt(BaseConfig.USER_TODO_ITEM_COUNT, pos + 8).apply()
                binding.includeItemCount.includeInfo.text = BaseConfig.columnCountList[pos]
            }.show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FragSetting().apply {
                arguments = Bundle().apply {
                }
            }
    }
}