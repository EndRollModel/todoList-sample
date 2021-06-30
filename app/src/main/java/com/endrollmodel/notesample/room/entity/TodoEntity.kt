package com.endrollmodel.notesample.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.endrollmodel.notesample.config.BaseData

@Entity(tableName = BaseData.todoListTable)
class TodoEntity (
    @PrimaryKey (autoGenerate = true) var todoId : Int? = null, // id
    @ColumnInfo(name = "title") var title : String? = "", // 標題 如果有需要
    @ColumnInfo(name = "text") var text : String? = "",// 記錄日期
    @ColumnInfo(name = "checkItem") var checkItem : String? = "{}", // check box
    @ColumnInfo(name = "date") var date : String? = "",
    @ColumnInfo(name = "doneDate") var doneDate : String? = "", // 完成日期 // 可空
    @ColumnInfo(name = "background") var background : String? = "#ffffff", // 背景色 採用色碼
    @ColumnInfo(name = "check") var check : Int? = 0, //完成否 // 預設未完成   0 : 尚未完成  1 : 完成
) {
    override fun toString(): String {
        return "TodoEntity(todoId=$todoId, title=$title, text=$text, checkItem=$checkItem, date=$date, doneDate=$doneDate, background=$background, check=$check)"
    }
}