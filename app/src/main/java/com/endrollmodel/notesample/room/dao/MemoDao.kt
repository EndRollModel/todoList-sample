package com.endrollmodel.notesample.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.endrollmodel.notesample.config.BaseData
import com.endrollmodel.notesample.room.entity.MemoEntity
import io.reactivex.Flowable

@Dao
interface MemoDao {
    @Query("select * from ${BaseData.memoListTable}")
    fun getAllMemo() : Flowable<List<MemoEntity>>
}