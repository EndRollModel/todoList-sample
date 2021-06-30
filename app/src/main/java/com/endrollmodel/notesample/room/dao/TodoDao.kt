package com.endrollmodel.notesample.room.dao

import androidx.room.*
import com.endrollmodel.notesample.config.BaseData
import com.endrollmodel.notesample.room.entity.TodoEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface TodoDao {
    // 待辦事務 ： 0 尚未完成  1 : 完成  2 : 全部

    @Query("select * from ${BaseData.todoListTable}") // 回傳全部事項
    fun getAll() : Flowable<List<TodoEntity>>

    @Query("select * from ${BaseData.todoListTable} where `check` = :action ") // 回傳指定事項 = 0 : 未完成 / 1 : 已完成
    fun getTodoData(action: Int): Flowable<List<TodoEntity>>

    @Query("update ${BaseData.todoListTable} set `check` = :action where `todoId` = :todoId ") // 回傳指定事項 = 0 : 未完成 / 1 : 已完成
    fun updateTodoCheck (action: Int, todoId: Int): Single<Int>

    @Update
    fun updateTodoData(todoList: TodoEntity) : Single<Int> // 更新todoList的資料

    @Insert(onConflict = OnConflictStrategy.REPLACE) // 如果輸入了一樣的資訊取代上一筆資訊
    fun addTodo (todoList: TodoEntity) : Single<Long>

    @Query("delete from ${BaseData.todoListTable} where `todoId` = :todoId") // 刪除
    fun deleteTodo (todoId : Int) : Single<Int>

    @Query("delete from ${BaseData.todoListTable}") // 刪除資料
    fun deleteTodo() : Completable
}