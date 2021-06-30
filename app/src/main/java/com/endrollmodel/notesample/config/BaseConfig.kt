package com.endrollmodel.notesample.config

import androidx.collection.arrayMapOf
import com.endrollmodel.notesample.R

object BaseConfig {
    const val appName = "NyaHello@ _@b"
    const val EndModelKey = 12345678
    const val FirstName = "EndModel"
    const val alis = "key1"
    val mainTitle = arrayOf("待辦事項", "設定") // mainTabTitle
    val todoListTab = arrayOf("未完成", "已完成", "全部") //待辦事務的tabTitle
    val todoLongItemUndone = arrayOf("變更為完成", "刪除", "取消") // 長案選項
    val todoLongItemDone = arrayOf("變更為未完成", "刪除", "取消") // 長案選項
    val rowCountList = arrayOf("1", "2", "3", "4") // 設定中一列幾個item
    val columnCountList = arrayOf("8", "9", "10", "11", "12") // 一行幾個item

    //    val colorSelector = arrayOf(R.color.colorThemePinkSelect, R.color.colorPinkThemeBlue, R.color.colorPinkThemeDone, R.color.colorPinkThemeUndone)
    // 顏色選擇器內容
    val colorSelector = arrayOf(
        R.color.colorTodoRed,
        R.color.colorTodoOrange,
        R.color.colorTodoYellow,
        R.color.colorTodoGreen,
        R.color.colorTodoBlue,
        R.color.colorTodoIndigo,
        R.color.colorTodoPurple,
        R.color.colorTodoPurple2,
        R.color.colorTodoPlus,
        R.color.colorTodoPlus2,
        R.color.colorTodoWhite
    )

    var themeNameList = arrayOf(
        "紅色", "橙色", "黃色", "綠色",
        "藍色", "靛色", "紫色", "粉色"
    )

    // 主題選擇
    val themeList = arrayMapOf(
//        Pair("Red", R.style.AppDayNightTheme),
//        Pair("Orange", R.style.AppThemeLightOrange),
//        Pair("Yellow", R.style.AppThemeLightYellow),
//        Pair("Green", R.style.AppThemeLightGreen),
//        Pair("Blue", R.style.AppThemeLightBlue),
//        Pair("Indigo", R.style.AppThemeLightIndigo),
//        Pair("Purple", R.style.AppThemeLightPurple),
//        Pair("Pink", R.style.AppThemeLightPink)
        Pair("紅色", R.style.AppThemeLightRed),
        Pair("橙色", R.style.AppThemeLightOrange),
        Pair("黃色", R.style.AppThemeLightYellow),
        Pair("綠色", R.style.AppThemeLightGreen),
        Pair("藍色", R.style.AppThemeLightBlue),
        Pair("靛色", R.style.AppThemeLightIndigo),
        Pair("紫色", R.style.AppThemeLightPurple),
        Pair("粉色", R.style.AppThemeLightPink)
    )

    // 主題顏色選擇
    val themeColorList = arrayMapOf(
//        Pair("Red", R.color.colorThemeRed),
//        Pair("Orange", R.color.colorThemeOrange),
//        Pair("Yellow", R.color.colorThemeYellow),
//        Pair("Green", R.color.colorThemeGreen),
//        Pair("Blue", R.color.colorPinkThemeBlue),
//        Pair("Indigo", R.color.colorThemeIndigo),
//        Pair("Purple", R.color.colorThemePurple2),
//        Pair("Pink", R.color.colorThemePink)
        Pair("紅色", R.color.colorThemeRed),
        Pair("橙色", R.color.colorThemeOrange),
        Pair("黃色", R.color.colorThemeYellow),
        Pair("綠色", R.color.colorThemeGreen),
        Pair("藍色", R.color.colorPinkThemeBlue),
        Pair("靛色", R.color.colorThemeIndigo),
        Pair("紫色", R.color.colorThemePurple2),
        Pair("粉色", R.color.colorThemePink)
    )

    // sp參數
    const val USER_THEME = "USER_THEME" // 使用者主題
    const val USER_THEME_COLOR = "USER_THEME_COLOR" // 使用者主題
    const val USER_DARK_THEME = "USER_DARK_THEME" // 是否使用深色主題
    const val USER_HANDEDNESS = "USER_HANDEDNESS" //慣用手
    const val USER_HANDEDNESS_RIGHT = 0 // 右手
    const val USER_HANDEDNESS_LEFT = 1 // 左手
    const val USER_SCREEN_HEIGHT = "USER_SCREEN_HEIGHT" // 螢幕高度
    const val USER_SCREEN_WIDTH = "USER_SCREEN_WIDTH" // 螢幕寬度
    const val USER_STATUS_HEIGHT = "USER_STATUS_HEIGHT" // 狀態列高度
    const val USER_TODO_ITEM_COUNT = "USER_TODO_ITEM_COUNT" // 待辦事務整個View可以顯示幾個item
    const val USER_TODO_GRID_COUNT = "USER_TODO_GRID_COUNT" // 待辦事務一行可以顯示幾個View

    // bundle參數
    // todoList 待辦事項的內容
    const val TODO_ITEM_ID = "TODO_ITEM_ID" // ID
    const val TODO_ITEM_TITLE = "DAY_INFO_TITLE" // 標題
    const val TODO_ITEM_TEXT = "TODO_ITEM_TEXT" // 內容
    const val TODO_ITEM_BACKGROUND = "TODO_ITEM_BACKGROUND" // 背景顏色
    const val TODO_ITEM_DONE_DATE = "TODO_ITEM_DONE_DATE" // 完成日
    const val TODO_ITEM_DATE = "TODO_ITEM_DATE" // 建立日期
    const val TODO_ITEM_CHECK_ITEM = "TODO_ITEM_CHECK_ITEM" //是否有完成清單
    const val TODO_ITEM_CHECK = "TODO_ITEM_CHECK" // 完成是否

    const val TODO_LIST_ACTION = "TODO_LIST_ACTION" //
    const val TODO_LIST_UNDONE = 0 // 未完成
    const val TODO_LIST_DONE = 1 // 完成
    const val TODO_LIST_ALL = 2 // 全部
    const val TODO_LIST_ADD = 100 // 新增
    const val TODO_LIST_REMOVE = 101 // 刪除

}