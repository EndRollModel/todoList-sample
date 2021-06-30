package com.endrollmodel.notesample.base

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.endrollmodel.notesample.R
import com.endrollmodel.notesample.config.BaseConfig
import com.endrollmodel.notesample.config.BaseValue
import com.endrollmodel.notesample.util.DisplayManager


open class BaseActivity : AppCompatActivity() {
    private var sp: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sp = getSharedPreferences(BaseConfig.appName, MODE_PRIVATE)
        application.getSharedPreferences(BaseConfig.appName, MODE_PRIVATE)
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) // 根據主題切換顏色
        getScreen()
    }

    private fun getScreen() {
        if(sp!!.getInt(BaseConfig.USER_SCREEN_HEIGHT, -1) == -1 &&
            sp!!.getInt(BaseConfig.USER_SCREEN_WIDTH, -1) == -1){
            // 由於app只有一個activity 初次啟動 - 取得參數
            val screen = DisplayManager.getScreenPixel(this)
            val statusHeight = DisplayManager.getStatusHeight(this)
            sp!!.edit().putInt(BaseConfig.USER_THEME, R.style.AppThemeLightPink).apply() // 寫入預設主題
            sp!!.edit().putString(BaseConfig.USER_THEME_COLOR, "粉色").apply() // 寫入預設主題
            sp!!.edit().putInt(BaseConfig.USER_SCREEN_HEIGHT, screen.heightPixels).apply() // 寫入螢幕高
            sp!!.edit().putInt(BaseConfig.USER_SCREEN_WIDTH, screen.heightPixels).apply() // 寫入螢幕寬
            sp!!.edit().putInt(BaseConfig.USER_STATUS_HEIGHT, statusHeight).apply() // 寫入狀態列高度
            sp!!.edit().putInt(BaseConfig.USER_HANDEDNESS, BaseConfig.USER_HANDEDNESS_RIGHT).apply() // 寫入慣用手 預設右手
            sp!!.edit().putInt(BaseConfig.USER_TODO_ITEM_COUNT, BaseValue.todoItemCount).apply() // 寫入待辦事務一行要顯示幾個item (使用者可調整)
            sp!!.edit().putInt(BaseConfig.USER_TODO_GRID_COUNT, BaseValue.todoGridCount).apply() // GridView一行幾個item (使用者可調整)
        }
        setTheme(sp!!.getInt(BaseConfig.USER_THEME, R.style.AppTheme))
        val androidId: String =
            Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
        Log.e("android id ", androidId)
    }

    override fun onConfigurationChanged(configuration: Configuration) {
        // android 深色模式切換設定 app主題指定為深色主題 設定MODE_NIGHT_FOLLOW_SYSTEM 切換時會觸發以下監聽 可以進行切換模式
        super.onConfigurationChanged(configuration)
        when (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {                 // Night mode is not active, we're using the light theme
                setTheme(R.style.AppThemeLightPink)
                sp?.let { sp!!.edit().putBoolean(BaseConfig.USER_DARK_THEME, false) }
            }
            Configuration.UI_MODE_NIGHT_YES -> {                 // Night mode is active, we're using dark theme
                setTheme(R.style.AppThemeDayNightPink)
                sp?.let { sp!!.edit().putBoolean(BaseConfig.USER_DARK_THEME, true) }
            }
        }
        val intent = Intent(this, javaClass)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}