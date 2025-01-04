package org.csystem.app.android.basicviews

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import org.csystem.app.android.basicviews.constant.USERNAME
import org.csystem.app.android.basicviews.databinding.ActivityMainBinding
import org.csystem.app.basicviews.data.service.model.LoginInfoModel
import org.csystem.app.basicviews.data.service.UserService
import com.karandev.data.exception.service.DataServiceException

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mUserService: UserService

    private fun checkUser() =  mUserService.existsByUsernameAndPassword(mBinding.loginInfo!!.username, mBinding.loginInfo!!.password)

    private fun doLogin() {
        try {
            mBinding.statusText = ""
            if (!mBinding.anonymousChecked) {
                if (checkUser())
                    Intent(this, ManagementActivity::class.java)
                        .apply { putExtra(USERNAME, mBinding.loginInfo?.username);startActivity(this) }
                else
                    AlertDialog.Builder(this)
                        .setTitle(R.string.alert_dialog_user_login_problem_title)
                        .setMessage(R.string.alert_dialog_user_login_problem_message)
                        .setPositiveButton(R.string.alert_dialog_ok) { _, _ -> }
                        .create()
                        .show()
            } else
                Intent(this, ManagementActivity::class.java).apply { startActivity(this) }
        } catch (ex: DataServiceException) {
            Toast.makeText(this, R.string.data_problem_occurred_prompt, Toast.LENGTH_LONG).show()
        } catch (ex: Exception) {
            Toast.makeText(this, R.string.problem_occurred_prompt, Toast.LENGTH_LONG).show()
        }
    }

    private fun initModels() {
        mBinding.activity = this
        mBinding.loginInfo = LoginInfoModel()
        mBinding.loginLayoutEnable = View.VISIBLE
        mBinding.statusText = resources.getString(R.string.status_not_accepted_message)
    }

    private fun initBinding() {
        enableEdgeToEdge()
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initModels()
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.mainActivityLinearLayoutMain) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initialize() {
        initBinding()
        mUserService = UserService(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    fun onTitleTextClicked() {
        Toast.makeText(this, R.string.click_title_prompt, Toast.LENGTH_SHORT).show()
    }

    //For onClearUsernameTextButtonClicked and onClearPasswordTextButtonClicked different approaches used for demonstration
    fun onClearUsernameTextButtonClicked() = mBinding.mainActivityEditTextUsername.text.clear()

    fun onClearPasswordTextButtonClicked() {
        mBinding.loginInfo = mBinding.loginInfo!!.copy(password = "")
    }

    fun onLoginToggleButtonCheckedChange(checked: Boolean) {
        mBinding.loginLayoutEnable = if (checked) View.VISIBLE else View.GONE
    }

    fun onAcceptSwitchCheckedChange(checked: Boolean) {
        mBinding.buttonLoginEnable = checked
        mBinding.statusText = if (checked) resources.getString(R.string.status_accepted_message) else resources.getString(R.string.status_not_accepted_message)
    }

    fun onClearAllButtonClicked() {
        onClearUsernameTextButtonClicked()
        onClearPasswordTextButtonClicked()
    }

    fun onLoginButtonClicked() {
        doLogin()
    }

    fun onRegisterButtonClicked() {
        Intent(this, RegisterActivity::class.java).apply { startActivity(this) }
    }

    fun onCloseButtonClicked() {
        Toast.makeText(this, R.string.close_prompt, Toast.LENGTH_LONG).show()
        finish()
    }
}