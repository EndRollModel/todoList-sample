package com.endrollmodel.notesample.data

import com.endrollmodel.notesample.room.entity.TodoEntity

class TodoChange {
    constructor()
    constructor(action : Int, index: Int) {
        this.action = action
        this.index = index
    }
    constructor(action: Int, data : TodoEntity){
        this.action = action
        this.data = data
    }
    var action: Int? = null
    var index: Int? = null
    var data : TodoEntity? = null

    override fun toString(): String {
        return "TodoChange(action=$action, index=$index)"
    }

}