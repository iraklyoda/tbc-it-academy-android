package com.example.tricholog.ui.mapper

import com.example.tricholog.domain.common.DateHelper
import com.example.tricholog.domain.model.TrichoLog
import com.example.tricholog.ui.dashboard.logs.model.TrichoLogUi

fun TrichoLog.toTrichoLogUi(): TrichoLogUi {
    return TrichoLogUi(
        id, trigger, body, date = DateHelper.convertMillisToTime(this.createdAt)
        )
}