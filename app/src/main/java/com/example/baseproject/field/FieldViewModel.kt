package com.example.baseproject.field

import androidx.lifecycle.ViewModel
import org.json.JSONArray

class FieldViewModel : ViewModel() {

    private val jsonString =
        "[\n" +
                "  [\n" +
                "    {\n" +
                "      \"field_id\": 1,\n" +
                "      \"hint\": \"UserName\",\n" +
                "      \"field_type\": \"input\",\n" +
                "      \"keyboard\": \"text\",\n" +
                "      \"required\": false,\n" +
                "      \"is_active\": true,\n" +
                "      \"icon\": \"https://jemala.png\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"field_id\": 2,\n" +
                "      \"hint\": \"Email\",\n" +
                "      \"field_type\": \"input\",\n" +
                "      \"keyboard\": \"text\",\n" +
                "      \"required\": true,\n" +
                "      \"is_active\": true,\n" +
                "      \"icon\": \"https://jemala.png\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"field_id\": 3,\n" +
                "      \"hint\": \"Phone\",\n" +
                "      \"field_type\": \"input\",\n" +
                "      \"keyboard\": \"number\",\n" +
                "      \"required\": true,\n" +
                "      \"is_active\": true,\n" +
                "      \"icon\": \"https://jemala.png\"\n" +
                "    }\n" +
                "  ],\n" +
                "  [\n" +
                "    {\n" +
                "      \"field_id\": 4,\n" +
                "      \"hint\": \"FullName\",\n" +
                "      \"field_type\": \"input\",\n" +
                "      \"keyboard\": \"text\",\n" +
                "      \"required\": true,\n" +
                "      \"is_active\": true,\n" +
                "      \"icon\": \"https://jemala.png\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"field_id\": 14,\n" +
                "      \"hint\": \"Jemali\",\n" +
                "      \"field_type\": \"input\",\n" +
                "      \"keyboard\": \"text\",\n" +
                "      \"required\": false,\n" +
                "      \"is_active\": true,\n" +
                "      \"icon\": \"https://imgcdn.sigstick.com/O3wbBCKkogROvslwDac4/cover.thumb256.webp\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"field_id\": 89,\n" +
                "      \"hint\": \"Birthday\",\n" +
                "      \"field_type\": \"chooser\",\n" +
                "      \"required\": false,\n" +
                "      \"is_active\": true,\n" +
                "      \"icon\": \"https://jemala.png\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"field_id\": 898,\n" +
                "      \"hint\": \"Gender\",\n" +
                "      \"field_type\": \"chooser\",\n" +
                "      \"required\": false,\n" +
                "      \"is_active\": true,\n" +
                "      \"icon\": \"https://jemala.png\"\n" +
                "    }\n" +
                "  ]\n" +
                "]"

    private val jsonArray = JSONArray(jsonString)

    fun getFieldGroups(): MutableList<List<FieldDto>> {
        val groups: MutableList<List<FieldDto>> = mutableListOf<List<FieldDto>>()
        for (i in 0 until jsonArray.length()) {
            val innerArray = jsonArray.getJSONArray(i)
            val fields = mutableListOf<FieldDto>()

            for (j in 0 until innerArray.length()) {
                val field = innerArray.getJSONObject(j)
                fields.add(
                    FieldDto(
                        fieldId = field.optInt("field_id", -1),
                        hint = field.optString("hint", ""),
                        fieldType = field.optString("field_type", ""),
                        keyboard = if (field.has("keyboard")) field.getString("keyboard") else null,
                        required = field.optBoolean("required", false),
                        isActive = field.optBoolean("is_active", false),
                        icon = field.optString("icon", "")
                    )
                )
            }
            groups.add(fields)
        }
        return groups
    }

}