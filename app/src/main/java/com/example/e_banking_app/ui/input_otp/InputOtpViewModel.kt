package com.example.e_banking_app.ui.input_otp

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_banking_app.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class InputOtpViewModel : ViewModel() {
    private var verificationId: String? = null

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _inputOtpResult = MutableLiveData<InputOtpResult>()
    val inputOtpResult: LiveData<InputOtpResult> = _inputOtpResult

    private val _isValidated = MutableLiveData<Boolean>()
    val isValidated: LiveData<Boolean> = _isValidated


    fun otpChanged(otp: String) {
        _isValidated.value = otp.length == 6
    }

    fun sendVerificationCode(
        activity: Activity?,
        number: String,
    ) {
        // this method is used for getting
        // OTP on user phone number.
        val formattedNumber = "+84$number"
        val options = activity?.let {
            PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(formattedNumber) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(it) // Activity (for callback binding)
                .setCallbacks(
                    mCallBack(
                        activity = activity,
                        phoneNumber = number,
                    )
                )
                .build()
        }
        options?.let { PhoneAuthProvider.verifyPhoneNumber(it) }
    }


    // callback method is called on Phone auth provider.
    private fun mCallBack(
        activity: Activity?,
        phoneNumber: String,
    ): PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            // below method is used when
            // OTP is sent from Firebase
            override fun onCodeSent(
                s: String,
                forceResendingToken: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(s, forceResendingToken)
                setState(
                    InputOtpResult(
                        InputOtpState(
                            codeSent = true,
                            isVerified = false,
                            phoneNumber = phoneNumber
                        )
                    )
                )

                // when we receive the OTP it
                // contains a unique id which
                // we are storing in our string
                // which we have already created.
                verificationId = s
            }

            // this method is called when user
            // receive OTP from Firebase.
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                // below line is used for getting OTP code
                // which is sent in phone auth credentials.
                val code = phoneAuthCredential.smsCode
                Toast.makeText(activity, "$code  onVerificationCompleted", Toast.LENGTH_LONG).show()

                // checking if the code
                // is null or not.
                if (code != null) {
                    // if the code is not null then
                    // we are setting that code to
                    // our OTP edittext field.
//                    edtOTP.setText(code)

                    // after setting this code
                    // to OTP edittext field we
                    // are calling our verifyCode method.
                    verifyCode(code, phoneNumber, activity)
                }
            }

            // this method is called when firebase doesn't
            // sends our OTP code due to any error or issue.
            override fun onVerificationFailed(e: FirebaseException) {
                setState(
                    InputOtpResult(
                        error = R.string.error_message_fail_to_send_otp,
                    )
                )
            }

//            override fun onCodeAutoRetrievalTimeOut(p0: String) {
//                super.onCodeAutoRetrievalTimeOut(p0)
//                setState(
//                    InputOtpResult(
//                        error = R.string.1,
//                    )
//                )
//
//            }
        }

    // below method is use to verify code from Firebase.
    fun verifyCode(
        code: String,
        phoneNumber: String,
        activity: Activity?,
    ) {
        // below line is used for getting
        // credentials from our verification id and code.
        val credential = verificationId?.let { PhoneAuthProvider.getCredential(it, code) }

        // after getting credential we are
        // calling sign in method.
        credential?.let { _credential ->
            activity?.let { _activity ->
                auth.signInWithCredential(_credential)
                    .addOnCompleteListener(_activity) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success")

                            setState(
                                InputOtpResult(
                                    InputOtpState(
                                        codeSent = true,
                                        isVerified = true,
                                        phoneNumber = phoneNumber
                                    )
                                )
                            )

                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.exception)
//                            if (task.exception is FirebaseAuthInvalidCredentialsException) {
//                                // The verification code entered was invalid
//                            }
                            setState(
                                InputOtpResult(
                                    error = R.string.error_message_fail_to_verify_phone_number,
                                )
                            )
                        }
                    }
            }
        }

    }

    private fun setState(inputOtpResult: InputOtpResult) {
        _inputOtpResult.value = inputOtpResult
    }
}
