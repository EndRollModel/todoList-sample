package com.endrollmodel.notesample.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.endrollmodel.notesample.R
import com.endrollmodel.notesample.databinding.FragInfoBinding

class FragInfo : Fragment() {
    private lateinit var binding : FragInfoBinding

    private val navController by lazy {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_info, container, false)
        init()
        return binding.root
    }

    private fun init() {
        binding.mToolbar.setNavigationOnClickListener{ navController.popBackStack() }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FragInfo().apply {
                arguments = Bundle().apply {
                }
            }
    }
}