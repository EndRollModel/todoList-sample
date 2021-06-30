package com.endrollmodel.notesample.base

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.endrollmodel.notesample.config.BaseConfig

open class BaseFragment : Fragment() {
    private val openLog = true
    protected var sp : SharedPreferences? = null
    protected val TAG : String = javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sp = context?.getSharedPreferences(BaseConfig.appName, Context.MODE_PRIVATE) // 取得sp
    }
    fun log(message: String, functionName : String = ""){
        if (!openLog) return
        Log.e(TAG, "$functionName : $message" )
    }
}