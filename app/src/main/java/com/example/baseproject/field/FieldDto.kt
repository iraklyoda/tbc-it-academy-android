package com.example.baseproject.field

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FieldDto(
    @SerialName("field_Id")
    val fieldId: Int,
    @SerialName("hint")
    val hint: String,
    @SerialName("field_type")
    val fieldType: String,
    @SerialName("keyboard")
    val keyboard: String? = null,
    @SerialName("required")
    val required: Boolean,
    @SerialName("is_active")
    val isActive: Boolean,
    @SerialName("icon")
    val icon: String
) {
    val fieldTypeEnum: FieldType = if (keyboard?.uppercase() == "TEXT") {
        FieldType.TEXT
    } else if (keyboard?.uppercase() == "NUMBER") {
        FieldType.NUMBER
    } else if (hint.uppercase() == "BIRTHDAY") {
        FieldType.DATE
    } else if (hint.uppercase() == "GENDER"){
        FieldType.GENDER
    } else {
        FieldType.TEXT
    }

    var value: String = ""
}