package com.endrollmodel.notesample.testClass

import androidx.recyclerview.widget.DiffUtil
import com.endrollmodel.notesample.room.entity.TodoEntity

class MyDiffUtil : DiffUtil.ItemCallback<TodoEntity>() {
    override fun areItemsTheSame(oldItem: TodoEntity, newItem: TodoEntity): Boolean {
        return oldItem.check == newItem.check
    }

    override fun areContentsTheSame(oldItem: TodoEntity, newItem: TodoEntity): Boolean {
        return oldItem.todoId == newItem.todoId
    }
}