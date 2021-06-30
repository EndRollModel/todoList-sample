package com.endrollmodel.notesample.data


class TodoCheck {
    var checked : Boolean? = false
    var text : String? = ""
    override fun toString(): String {
        return "{ checked : {$checked}, text : {$text} }"
    }
}