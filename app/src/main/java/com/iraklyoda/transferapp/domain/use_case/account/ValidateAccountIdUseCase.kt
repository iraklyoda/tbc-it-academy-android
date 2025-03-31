package com.iraklyoda.transferapp.domain.use_case.account

import com.iraklyoda.transferapp.domain.common.ValidationResult
import javax.inject.Inject

interface ValidateAccountIdUseCase {
    operator fun invoke(identifier: String): ValidationResult
}

class ValidateAccountIdUseCaseImpl @Inject constructor(): ValidateAccountIdUseCase {
    override fun invoke(identifier: String): ValidationResult {

        if (identifier.isEmpty()) {
            return ValidationResult.Error(errorMessage = "This field should not be empty")
        }

        // Check Phone Number
        if (identifier.length == 9) {
            return ValidationResult.Success
        }

        // Check is Id
        if (identifier.length == 11) {
            return ValidationResult.Success
        }

        // Check is IBAN
        if (identifier.length == 23) {
            return ValidationResult.Success
        }

        return ValidationResult.Error(errorMessage = "Incorrect type")
    }
}