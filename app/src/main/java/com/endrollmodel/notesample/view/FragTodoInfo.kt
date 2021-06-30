package com.endrollmodel.notesample.view

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.endrollmodel.notesample.R
import com.endrollmodel.notesample.adapter.TodoCheckItemAdapter
import com.endrollmodel.notesample.adapter.TodoInfoColorAdapter
import com.endrollmodel.notesample.base.BaseFragment
import com.endrollmodel.notesample.config.BaseConfig
import com.endrollmodel.notesample.data.TodoCheck
import com.endrollmodel.notesample.databinding.FragTodoInfoBinding
import com.endrollmodel.notesample.util.AnimatorUnit
import com.endrollmodel.notesample.util.ThemeUtil
import com.endrollmodel.notesample.viewmodel.FragTodoInfoVM

class FragTodoInfo : BaseFragment() {

    // 資料改變
    private var todoAction: Int? = null
    private var todoId: Int? = null
    private var todoTitle: String? = null
    private var todoText: String? = null
    private var todoCheckItem: String? = null
    private var todoDate: String? = null
    private var todoDoneDate: String? = null
    private var todoBackground: String? = null
    private var todoCheck: Int? = null
    private lateinit var binding: FragTodoInfoBinding
    private lateinit var navController: NavController
    private lateinit var viewModel: FragTodoInfoVM
    // 背景顏色選擇
    private lateinit var colorAdapter: TodoInfoColorAdapter
    private lateinit var colorBackground : MutableList<String>
    // CheckBox列表資料
    private lateinit var checkAdapter: TodoCheckItemAdapter
    private lateinit var checkList : MutableList<TodoCheck>
    private lateinit var checkData : String
    // 確認資料是否改變
    private var inTitle = false
    private var inText = false
    private var inCheckItem = false
    private var inDate = false
    private var inDoneDate = false
    private var inBackground = false
    private var inCheck = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 過渡動畫 a -> b 設定
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.change_image_transform)
        arguments?.let {
            todoAction = it.getInt(BaseConfig.TODO_LIST_ACTION, -1)
            todoId = it.getInt(BaseConfig.TODO_ITEM_ID, -1)
            todoTitle = it.getString(BaseConfig.TODO_ITEM_TITLE)
            todoText = it.getString(BaseConfig.TODO_ITEM_TEXT)
            todoCheckItem = it.getString(BaseConfig.TODO_ITEM_CHECK_ITEM)
            todoDate = it.getString(BaseConfig.TODO_ITEM_DATE)
            todoDoneDate = it.getString(BaseConfig.TODO_ITEM_DONE_DATE)
            todoBackground = it.getString(BaseConfig.TODO_ITEM_BACKGROUND)
            todoCheck = it.getInt(BaseConfig.TODO_ITEM_CHECK, 0)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 過渡動畫 只有單向 無法雙向
        ViewCompat.setTransitionName(binding.mTitle, "todoItem${todoAction}${todoId}")
        ViewCompat.setTransitionName(binding.mLayout, "todoLayout${todoAction}${todoId}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_todo_info, container, false)
        viewModel = ViewModelProvider(this)[FragTodoInfoVM::class.java]
        navController = findNavController()
        init()
        return binding.root
    }

    private fun setColor(color: Int) {
        val viewBackground = (binding.mLayout.background as ColorDrawable).color
        val animatorUnit = AnimatorUnit.setChangerColorAni(
            viewBackground,
            color,
            binding.mLayout,
            500
        )
        animatorUnit.start()
    }

    private fun init() {
        Glide.with(this).load(R.drawable.baseline_arrow_back_black_36dp).into(binding.mIvBack)
        Glide.with(this).load(R.drawable.baseline_add_black_48dp).into(binding.mIvAdd)
        binding.mIvBack.setOnClickListener {
            closeKey()
            navController.popBackStack()
        }
        binding.mTitle.text = todoTitle
        todoText?.let {
            binding.mEdTodoText.setText(it)
        }
        todoTitle?.let {
            binding.mEdTodoTitle.setText(it)
        }
        todoBackground?.let {
//            setColor(Color.parseColor(it))
            binding.mLayout.setBackgroundColor(Color.parseColor(it))
        }
        viewModel.updateCount.observe(viewLifecycleOwner, {
            log("init: $it")
        })

        binding.mEdTodoTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                binding.mTitle.text = p0
                todoTitle = p0.toString()
                inTitle = true
            }
        })

        binding.mEdTodoText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                todoText = p0.toString()
                inText = true
            }
        })


        colorBackground = ArrayList()
        for (i in BaseConfig.colorSelector) {
            colorBackground.add(ThemeUtil().colorToHex(binding.root.context, i))
        }
        colorAdapter = TodoInfoColorAdapter()
        colorAdapter.colorList = colorBackground
        colorAdapter.viewModel = viewModel
        binding.mRecyclerColor.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
        binding.mRecyclerColor.adapter = colorAdapter

        viewModel.changeBackground.observe(viewLifecycleOwner){
            setColor(Color.parseColor(it))
            inBackground = true
            todoBackground = it
        }

        todoCheckItem?.let {

        }
        binding.mIvAdd.visibility = View.GONE
        binding.mIvAdd.setOnClickListener {
            checkList.add(TodoCheck())
            checkAdapter.checkItems = checkList
        }

        checkAdapter = TodoCheckItemAdapter()
        checkList = ArrayList()
        binding.mRecyclerCheck.layoutManager = LinearLayoutManager(binding.root.context)
        binding.mRecyclerCheck.adapter = checkAdapter

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FragTodoInfo().apply {}
    }

    override fun onStop() {
        super.onStop()
        log("stop")
    }

    override fun onPause() {
        super.onPause()
        log("pause")
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun closeKey(){
        // 關閉小鍵盤
        val imm: InputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow((context as Activity).window.decorView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    override fun onDestroyView() {
        if (!inTitle && !inText && !inCheckItem && !inDate && !inDoneDate && !inBackground && !inCheck) {
            // 如果資料都無改變
            log("無改變資料: ")
        } else {
            viewModel.updateTodoList(
                todoId!!,
                todoTitle,
                todoText,
                todoCheckItem,
                todoDate,
                todoDoneDate,
                todoBackground,
                todoCheck!!
            )
        }
        super.onDestroyView()
    }

}