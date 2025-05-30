package org.csystem.app.android.basicviews

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.csystem.app.android.basicviews.constant.USERNAME
import org.csystem.app.android.basicviews.data.service.UserService
import org.csystem.app.android.basicviews.model.LoginInfoModel
import org.csystem.data.exception.DataServiceException

class MainActivity : AppCompatActivity() {
    private lateinit var mToggleButtonOpenLogin: ToggleButton
    private lateinit var mLinearLayoutLoginScreen: LinearLayout
    private lateinit var mCheckBoxAnonymous: CheckBox
    private lateinit var mEditTextUsername: EditText
    private lateinit var mEditTextPassword: EditText
    private lateinit var mButtonLogin: Button
    private lateinit var mTextViewStatus: TextView
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var mSwitchAccept: Switch

    private lateinit var mLoginInfoModel: LoginInfoModel
    private lateinit var mUserService: UserService

    private fun fillLoginInfoModel() {
        mLoginInfoModel = LoginInfoModel(mEditTextUsername.text.toString(), mEditTextPassword.text.toString())
    }

    private fun checkUser() =  mUserService.existsByUsernameAndPassword(mLoginInfoModel.username, mLoginInfoModel.password)

    private fun doLogin() {
        try {
            mTextViewStatus.text = ""
            if (!mCheckBoxAnonymous.isChecked) {
                fillLoginInfoModel()
                if (checkUser())
                    Intent(this, ManagementActivity::class.java)
                        .apply { putExtra(USERNAME, mLoginInfoModel.username);startActivity(this) }
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

    private fun loginButtonClickedCallback() {
        doLogin()
    }

    private fun acceptSwitchCheckedChangeCallback(isChecked: Boolean) {
        mButtonLogin.isEnabled = isChecked
        mTextViewStatus.text = if (isChecked) resources.getString(R.string.status_accepted_message) else resources.getString(R.string.status_not_accepted_message)
    }

    private fun openLoginToggleButtonCheckedChangeCallback(isChecked: Boolean) {
        mLinearLayoutLoginScreen.visibility = if (isChecked) View.VISIBLE else View.GONE
    }

    private fun initOpenLoginToggleButton() {
        mToggleButtonOpenLogin = findViewById(R.id.mainActivityToggleButtonOpenLogin)
        mToggleButtonOpenLogin.setOnCheckedChangeListener{_, isChecked -> openLoginToggleButtonCheckedChangeCallback(isChecked)}
    }

    private fun initAnonymousCheckBox() {
        mCheckBoxAnonymous = findViewById(R.id.mainActivityCheckBoxAnonymous)
    }

    private fun initLoginButton() {
        mButtonLogin = findViewById(R.id.mainActivityButtonLogin)
        mButtonLogin.setOnClickListener{ loginButtonClickedCallback() }
    }

    private fun initAcceptSwitch() {
        mSwitchAccept = findViewById(R.id.mainActivitySwitchAccept)
        mSwitchAccept.setOnCheckedChangeListener{_, isChecked -> acceptSwitchCheckedChangeCallback(isChecked)}
    }

    private fun initViews() {
        initOpenLoginToggleButton()
        initAnonymousCheckBox()
        mLinearLayoutLoginScreen = findViewById<LinearLayout?>(R.id.mainActivityLinearLayoutLoginScreen).apply { visibility = View.GONE }
        mEditTextUsername = findViewById(R.id.mainActivityEditTextUsername)
        mEditTextPassword = findViewById(R.id.mainActivityEditTextPassword)
        initLoginButton()
        mTextViewStatus = findViewById(R.id.mainActivityTextViewStatus)
        initAcceptSwitch()
    }

    private fun initialize() {
        mUserService = UserService(this)
        initViews()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainActivityLinearLayoutMain)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initialize()
    }

    fun onCloseButtonClicked(view: View) {
        Toast.makeText(this, R.string.close_prompt, Toast.LENGTH_LONG).show()
        finish()
    }

    fun onClearUsernameTextButtonClicked(view: View) = mEditTextUsername.setText("")

    fun onClearPasswordTextButtonClicked(view: View) = mEditTextPassword.setText("")

    fun onClearAlleButtonClicked(view: View) {
        onClearUsernameTextButtonClicked(view)
        onClearPasswordTextButtonClicked(view)
    }

    fun onTitleTextClicked(view: View) {
        Toast.makeText(this, R.string.click_title_prompt, Toast.LENGTH_SHORT).show()
    }

    fun onRegisterButtonClicked(view: View) {
        Intent(this, RegisterActivity::class.java).apply { startActivity(this) }
    }
}