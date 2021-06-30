package com.endrollmodel.notesample.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.endrollmodel.notesample.config.BaseData

@Entity(tableName = BaseData.memoListTable)
class MemoEntity {
    @PrimaryKey(autoGenerate = true) var memoId : Int? = null // id
}