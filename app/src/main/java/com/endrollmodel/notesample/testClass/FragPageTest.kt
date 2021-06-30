package com.endrollmodel.notesample.testClass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.endrollmodel.notesample.R
import com.endrollmodel.notesample.base.BaseFragment
import com.endrollmodel.notesample.databinding.FragPageTestBinding
import com.endrollmodel.notesample.room.entity.TodoEntity
import com.endrollmodel.notesample.viewmodel.FragTodoListVM
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragPageTest : BaseFragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragPageTestBinding
    private lateinit var adapter: TodoPageListAdapter
    private lateinit var viewModel: FragTodoListVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        viewModel = ViewModelProvider(this)[FragTodoListVM::class.java]
        viewModel.getTodoData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_page_test, container, false)
        return binding.root
    }

    private fun init() {
        adapter = TodoPageListAdapter(0, findNavController(), viewModel)
        binding.mRecycler.layoutManager = LinearLayoutManager(context)
        binding.mRecycler.adapter = adapter
//        viewModel.todoList.observe(viewLifecycleOwner) {
//            adapter.submitList(it)
//            log("做事？ ${it.size}")
//        }
        viewModel.todoList.observe(viewLifecycleOwner,{
            adapter.submitList(it)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
//        waitForTransition(binding.mRecycler)
    }

    private fun Fragment.waitForTransition(targetView: View) {
        postponeEnterTransition()
        targetView.doOnPreDraw { startPostponedEnterTransition() }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragPageTest().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}