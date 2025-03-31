package com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from.model

import android.os.Parcelable
import com.iraklyoda.transferapp.domain.common.enum.Currency
import com.iraklyoda.transferapp.domain.common.enum.DebitCardType
import kotlinx.parcelize.Parcelize

@Parcelize
data class AccountUi(
    val id: Int,
    val accountName: String,
    val accountNumber: String,
    val valueType: Currency,
    val cardType: DebitCardType,
    val balance: Int,
    val cardLogo: Int?
) : Parcelable