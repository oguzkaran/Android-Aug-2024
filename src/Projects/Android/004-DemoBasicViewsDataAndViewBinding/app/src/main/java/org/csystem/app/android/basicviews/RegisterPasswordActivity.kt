package org.csystem.app.android.basicviews

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import org.csystem.app.android.basicviews.constant.REGISTER_INFO
import org.csystem.app.android.basicviews.databinding.ActivityRegisterPasswordBinding
import org.csystem.app.basicviews.data.service.model.UserInfoModel
import org.csystem.app.basicviews.data.service.UserService
import com.karandev.data.exception.service.DataServiceException

private const val USER_EXISTS_INFO_LOG_TAG = "USER_EXIST_INFO"

class RegisterPasswordActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityRegisterPasswordBinding
    private lateinit var mUserService: UserService

    private fun registerUserInfo(password: String)  {
        if (userExists()) {
            Toast.makeText(this, R.string.user_already_registered_prompt, Toast.LENGTH_LONG).show()
            return
        }
        mBinding.userInfo?.password = password
        mUserService.registerUser(mBinding.userInfo!!)
        Toast.makeText(this, R.string.user_registered_successfully_prompt, Toast.LENGTH_LONG).show()
        finish()
    }

    private fun userExists(): Boolean {
        var result = false

        try {
            result = mUserService.existsByUsername(mBinding.userInfo!!.username)
        } catch (ex: DataServiceException) {
            Log.e(USER_EXISTS_INFO_LOG_TAG, ex.message ?: "")
            Toast.makeText(this, R.string.data_problem_occurred_prompt, Toast.LENGTH_LONG).show()
        } catch (ex: Exception) {
            Log.e(USER_EXISTS_INFO_LOG_TAG, ex.message, ex)
            Toast.makeText(this, R.string.problem_occurred_prompt, Toast.LENGTH_LONG).show()
        }

        return result
    }

    private fun initModels() {
        mBinding.activity = this
        mBinding.userInfo = UserInfoModel()
        mBinding.userInfo = when {
            Build.VERSION.SDK_INT < 33 -> intent.getSerializableExtra(REGISTER_INFO) as UserInfoModel
            else -> intent.getSerializableExtra(REGISTER_INFO, UserInfoModel::class.java)!!
        }
        mBinding.title = resources.getString(R.string.text_view_register_password_title).format(mBinding.userInfo!!.username)
    }

    private fun initBinding() {
        enableEdgeToEdge()
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register_password)
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initModels()
    }

    private fun initialize() {
        initBinding()
        mUserService = UserService(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    fun onRegisterButtonClicked() {
        val password = mBinding.userInfo?.password
        val confirmPassword = mBinding.confirmPassword

        if (password == confirmPassword)
            registerUserInfo(password!!)
        else
            AlertDialog.Builder(this)
                .setTitle(R.string.alert_dialog_confirm_password_title)
                .setMessage(R.string.alert_dialog_confirm_password_message)
                .setPositiveButton(R.string.alert_dialog_ok) { _, _ -> }
                .create()
                .show()
    }

    fun onCloseButtonClicked() = finish()
}