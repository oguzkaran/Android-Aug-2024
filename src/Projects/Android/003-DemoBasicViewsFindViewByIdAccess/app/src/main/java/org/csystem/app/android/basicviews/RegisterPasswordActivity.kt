package org.csystem.app.android.basicviews

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.csystem.app.android.basicviews.constant.REGISTER_INFO
import org.csystem.app.android.basicviews.data.service.UserService
import org.csystem.app.android.basicviews.model.RegisterInfoModel
import org.csystem.data.exception.DataServiceException

private const val USER_EXISTS_INFO_LOG_TAG = "USER_EXIST_INFO"

class RegisterPasswordActivity : AppCompatActivity() {
    private lateinit var mTextViewTitle: TextView
    private lateinit var mEditTextPassword: EditText
    private lateinit var mEditTextConfirmPassword: EditText
    private lateinit var mRegisterInfoModel: RegisterInfoModel
    private lateinit var mUserService: UserService

    private fun registerUserInfo(password: String)  {
        if (userExists()) {
            Toast.makeText(this, R.string.user_already_registered_prompt, Toast.LENGTH_LONG).show()
            return
        }
        mRegisterInfoModel.password = password
        mUserService.registerUser(mRegisterInfoModel)
        Toast.makeText(this, R.string.user_registered_successfully_prompt, Toast.LENGTH_LONG).show()
        finish()
    }

    private fun userExists(): Boolean {
        var result = false

        try {
            result = mUserService.existsByUsername(mRegisterInfoModel.username)
        } catch (ex: DataServiceException) {
            Log.e(USER_EXISTS_INFO_LOG_TAG, ex.message ?: "")
            Toast.makeText(this, R.string.data_problem_occurred_prompt, Toast.LENGTH_LONG).show()
        } catch (ex: Exception) {
            Log.e(USER_EXISTS_INFO_LOG_TAG, ex.message, ex)
            Toast.makeText(this, R.string.problem_occurred_prompt, Toast.LENGTH_LONG).show()
        }

        return result
    }

    private fun initTextViewTitle() {
        mTextViewTitle = findViewById(R.id.registerPasswordActivityTextViewTitle)
        mTextViewTitle.text = resources.getString(R.string.text_view_register_password_title).format(mRegisterInfoModel.username)
    }

    private fun initViews() {
        initTextViewTitle()
        mEditTextPassword = findViewById(R.id.registerPasswordActivityEditTextPassword)
        mEditTextConfirmPassword = findViewById(R.id.registerPasswordActivityEditTextConfirmPassword)
    }

    private fun initialize() {
        mUserService = UserService(this)
        mRegisterInfoModel = when {
            Build.VERSION.SDK_INT < 33 -> intent.getSerializableExtra(REGISTER_INFO) as RegisterInfoModel
            else -> intent.getSerializableExtra(REGISTER_INFO, RegisterInfoModel::class.java)!!
        }
        initViews()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_password)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initialize()
    }

    fun onRegisterButtonClicked(view: View) {
        val password = mEditTextPassword.text.toString()
        val confirmPassword = mEditTextConfirmPassword.text.toString()

        if (password == confirmPassword)
            registerUserInfo(password)
        else
            AlertDialog.Builder(this)
                .setTitle(R.string.alert_dialog_confirm_password_title)
                .setMessage(R.string.alert_dialog_confirm_password_message)
                .setPositiveButton(R.string.alert_dialog_ok) { _, _ -> }
                .create()
                .show()
    }

    fun onCloseButtonClicked(view: View) = finish()
}