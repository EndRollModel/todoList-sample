package com.endrollmodel.notesample.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.endrollmodel.notesample.base.SingleLiveEvent
import com.endrollmodel.notesample.data.TodoCheck
import com.endrollmodel.notesample.room.db.DataBaseHelper
import com.endrollmodel.notesample.room.entity.TodoEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FragTodoInfoVM(application: Application) : AndroidViewModel(application) {
    private val TAG = "FragTodoInfoVM"
    private val mDisposable = CompositeDisposable()
    var updateCount = SingleLiveEvent<Int>()
    var changeBackground = MutableLiveData<String>()
    var checkItem = MutableLiveData<List<TodoCheck>>()

    fun updateTodoList(
        id: Int,
        title: String? = null,
        text: String? = null,
        checkItem: String? = null,
        date: String? = null,
        dateDone: String? = null,
        background: String? = null,
        check: Int = 0
    ) {
        val updateObject = TodoEntity()
        updateObject.todoId = id
        title?.let { updateObject.title = it }
        text?.let { updateObject.text = it }
        checkItem?.let { updateObject.checkItem = it }
        date?.let { updateObject.date = it }
        dateDone?.let { updateObject.doneDate = it }
        background?.let { updateObject.background = it }
        check.let { updateObject.check = it }

        Log.e(TAG, "updateTodoList: $updateObject" )

        mDisposable.add(
            DataBaseHelper.getInstance(getApplication())!!.todoDao().updateTodoData(updateObject)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
                    updateCount.value = it
                    Log.e(TAG, "updateTodoList: $it" )
                }, {
                    Log.e(TAG, "updateTodoList: ${it.message.toString()}")
                })
        )
    }

    fun changeBackground (color: String){
        changeBackground.value = color
    }

    fun addCheckItem () {

    }


}