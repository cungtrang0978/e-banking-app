package com.example.e_banking_app.ui.input_otp

data class InputOtpState(
    val codeSent: Boolean = false,
    val isVerified: Boolean = false,
    val phoneNumber: String
) {
    fun copyWith(
        codeSent: Boolean?,
        isVerified: Boolean?,
        phoneNumber: String?
    ): InputOtpState {
        return InputOtpState(
            codeSent = codeSent ?: this.codeSent,
            isVerified = isVerified ?: this.isVerified,
            phoneNumber = phoneNumber ?: this.phoneNumber,
        )
    }

}
