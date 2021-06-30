package com.endrollmodel.notesample.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.endrollmodel.notesample.base.SingleLiveEvent
import com.endrollmodel.notesample.data.TodoChange
import com.endrollmodel.notesample.room.db.DataBaseHelper
import com.endrollmodel.notesample.room.db.DataBaseHelper.Companion.getInstance
import com.endrollmodel.notesample.room.entity.TodoEntity
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FragTodoListVM(application: Application) : AndroidViewModel(application) {
    private val TAG = "FragTodoListVM"
    private val mDisposable = CompositeDisposable()
    private val dataBaseHelper: DataBaseHelper? = null
    var todoList = MutableLiveData<List<TodoEntity>>()
    var todoData = MutableLiveData<TodoEntity>()
    var todoRemoveItem = MutableLiveData<TodoChange>()
    var todoInsertItem = MutableLiveData<TodoChange>()
    var todoChangeStatus = MutableLiveData<TodoChange>()
    var todoNotify = MutableLiveData<TodoChange>()
    var isLoading: Flowable<Boolean>? = null
    private var todoUpdate = SingleLiveEvent<Int>()

    fun getTodoData(action: Int) {
        mDisposable.add(getInstance(getApplication())!!.todoDao()
            .getTodoData(action)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t ->
                todoList.value = t
            })
    }

    fun getTodoData() {
        mDisposable.add(getInstance(getApplication())!!.todoDao()
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t -> todoList.value = t })
    }

    fun chargeCheckStatus(action : Int, status: Int, todoId: Int, index: Int) {
        mDisposable.add(getInstance(getApplication())!!.todoDao()
                .updateTodoCheck(status, todoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it > 0) {
//                        todoChangeStatus.value = TodoChange(action, index)
                    }
                }, {
                    Log.e("chargeCheckStatus: ", it.message.toString())
                })
        )
    }

    fun addTodoData(action : Int, data: TodoEntity?) {
        mDisposable.add(getInstance(getApplication())!!.todoDao()
                .addTodo(data!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e(TAG, "addTodoData: 成功")
                    if(it > 0){
//                        todoInsertItem.value = TodoChange(action, data)
                    }
                }, {
                    Log.e(TAG, "addTodoData: 失敗")
                })
        )
    }

    fun deleteTodoData(id : Int, index : Int) {
        mDisposable.add(getInstance(getApplication())!!.todoDao()
                .deleteTodo(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    if (it > 0) {
//                        todoRemoveItem.value = TodoChange(BaseConfig.TODO_LIST_REMOVE, index)
                    }
                }, {
                    Log.e("deleteDataError ", it.message.toString())
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        mDisposable.clear()
    }
}