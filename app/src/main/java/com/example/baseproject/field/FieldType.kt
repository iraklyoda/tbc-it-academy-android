package com.example.baseproject.field

import android.text.InputType

enum class FieldType(val inputType: Int = InputType.TYPE_CLASS_TEXT) {
    TEXT(inputType = InputType.TYPE_CLASS_TEXT),
    NUMBER(inputType = InputType.TYPE_CLASS_NUMBER),
    DATE(),
    GENDER(),
}