package com.endrollmodel.notesample.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.endrollmodel.notesample.R
import com.endrollmodel.notesample.adapter.TodoPageAdapter
import com.endrollmodel.notesample.base.BaseFragment
import com.endrollmodel.notesample.config.BaseConfig
import com.endrollmodel.notesample.config.BaseValue
import com.endrollmodel.notesample.databinding.FragTodoPageBinding
import com.endrollmodel.notesample.room.entity.TodoEntity
import com.endrollmodel.notesample.viewmodel.FragTodoListVM
import kotlinx.android.synthetic.main.frag_todo_page.*


class FragTodoPage : BaseFragment() {
    private var action: Int? = -1
    private lateinit var binding: FragTodoPageBinding
    private lateinit var navController: NavController
    private lateinit var pageAdapter: TodoPageAdapter
    private lateinit var dataList: MutableList<TodoEntity>
    private lateinit var viewModel: FragTodoListVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            action = it.getInt(BaseConfig.TODO_LIST_ACTION)
        }
//        log("建立動作 : $action")
        viewModel = ViewModelProvider(this).get(FragTodoListVM::class.java)

        when (action) {
            BaseConfig.TODO_LIST_UNDONE, BaseConfig.TODO_LIST_DONE -> viewModel.getTodoData(action!!)
            BaseConfig.TODO_LIST_ALL -> viewModel.getTodoData()
            else -> {
            }
        }
        dataList = ArrayList()
        navController = findNavController()
        pageAdapter = TodoPageAdapter(action!!, navController, viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_todo_page, container, false)

        return binding.root

    }

    private fun initData() {

        viewModel.todoList.observe(viewLifecycleOwner, {
            // 回傳資訊時
            if (mSwipe.isRefreshing) {
                mSwipe.isRefreshing = false
            }
//            log("取得所有資料")
            pageAdapter.items = it!!
            postponeEnterTransition()
            binding.mRecycler.doOnPreDraw {
                startPostponedEnterTransition()
            }
        })
//        viewModel.todoInsertItem.observe(viewLifecycleOwner, {
//            it?.let {
//                when (it.action) {
//                    BaseConfig.TODO_LIST_UNDONE, BaseConfig.TODO_LIST_ALL -> {
//                        dataList.add(it.data!!)
//                        pageAdapter.notifyItemInserted(dataList.size)
//                        pageAdapter.notifyItemRangeChanged(dataList.size, 1)
//                    }
//                    BaseConfig.TODO_LIST_DONE -> { }
//                }
//            }
//        })
//        viewModel.todoRemoveItem.observe(viewLifecycleOwner, {
//            log("收到刪除動作 動作:${action} ${it.toString()} 資料長度: ${dataList.size}")
//            dataList.removeAt(it!!.index!!)
//            pageAdapter.notifyItemRemoved(it.index!!)
//            pageAdapter.notifyItemRangeChanged(it.index!!, dataList.size)
//        })
//        viewModel.todoChangeStatus.observe(viewLifecycleOwner, {
//            it?.let {
//                log("變更了狀態 ${it.action} index ${it.index}")
//                if (it.action == action) {
//                    when (action) {
//                        BaseConfig.TODO_LIST_UNDONE, BaseConfig.TODO_LIST_DONE -> {
//                            dataList.removeAt(it.index!!)
//                            pageAdapter.notifyItemRemoved(it.index!!)
//                            pageAdapter.notifyItemRangeChanged(it.index!!, dataList.size)
//                        }
//                        BaseConfig.TODO_LIST_ALL -> {
//                            pageAdapter.notifyDataSetChanged()
//                        }
//                    }
//                } else {
//                    pageAdapter.notifyDataSetChanged()
//                }
//            }
//        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        scheduleStartPostponedTransition(binding.mRecycler)
    }

    private fun initView() {
        log("init了一次 動作${action} 長度${dataList.size} ")
        val layoutManager = GridLayoutManager(context, sp!!.getInt(BaseConfig.USER_TODO_GRID_COUNT,
            BaseValue.todoGridCount))
        binding.mRecycler.layoutManager = layoutManager
        binding.mRecycler.apply {
            this.adapter = pageAdapter
            postponeEnterTransition()
            viewTreeObserver
                .addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
        }
        binding.mRecycler.adapter = pageAdapter

        val colorHex = "#" + Integer.toHexString(ContextCompat.getColor(requireContext(),
            R.color.colorWhite))

        (binding.mRecycler.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        binding.mFloatButton.setOnClickListener {
            viewModel.addTodoData(
                action!!,
                TodoEntity(
                    null,
                    "新標題",
                    "",
                    "",
                    "",
                    "",
                    colorHex,
                    0
                )
            )
        }
        binding.mSwipe.setOnRefreshListener {
            when (action) {
                BaseConfig.TODO_LIST_UNDONE, BaseConfig.TODO_LIST_DONE -> viewModel.getTodoData(
                    action!!
                )
                BaseConfig.TODO_LIST_ALL -> viewModel.getTodoData()
                else -> {
                }
            }
        }
        if (action == BaseConfig.TODO_LIST_DONE) {
            binding.mFloatButton.visibility = View.GONE
        }
//        binding.mSwipe.setProgressBackgroundColorSchemeResource(R.color.colorThemePink)
        binding.mSwipe.setColorSchemeColors(Color.parseColor("#ffffff"))
//        binding.mSwipe.setColorSchemeResources(R.color.colorThemePink)
        binding.mSwipe.setProgressBackgroundColorSchemeResource(R.color.colorThemePink)
    }

    companion object {
        @JvmStatic
        fun newInstance(action: Int) =
            FragTodoPage().apply {
                arguments = Bundle().apply {
                    putInt(BaseConfig.TODO_LIST_ACTION, action)
                }
            }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun scheduleStartPostponedTransition(sharedElement: View) {
        sharedElement.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    sharedElement.viewTreeObserver.removeOnPreDrawListener(this)
                    parentFragment!!.startPostponedEnterTransition()
                    return true
                }
            })
    }

}