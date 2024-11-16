package org.csystem.app.android.basicviews

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var mEditTextUsername: EditText
    private lateinit var mEditTextPassword: EditText
    private lateinit var mButtonLogin: Button
    private lateinit var mTextViewStatus: TextView
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var mSwitchAccept: Switch

    private fun doLogin() {
        mTextViewStatus.text = ""
        Toast.makeText(this, R.string.login_prompt, Toast.LENGTH_LONG).show()
        val username = mEditTextUsername.text.toString()
        val password = mEditTextPassword.text.toString()

        //...

        Toast.makeText(this, "$username, $password", Toast.LENGTH_LONG).show()
    }

    private fun loginButtonClickedCallback() {
        if (!mSwitchAccept.isChecked) {
            Toast.makeText(this, R.string.check_accept_message, Toast.LENGTH_SHORT).show()
            mTextViewStatus.text = resources.getString(R.string.check_accept_message)
            return
        }
        doLogin()
    }

    private fun initLoginButton() {
        mButtonLogin = findViewById(R.id.mainActivityButtonLogin)
        mButtonLogin.setOnClickListener{ loginButtonClickedCallback() }
    }

    private fun initViews() {
        mEditTextUsername = findViewById(R.id.mainActivityEditTextUsername)
        mEditTextPassword = findViewById(R.id.mainActivityEditTextPassword)
        initLoginButton()
        mTextViewStatus = findViewById(R.id.mainActivityTextViewStatus)
        mSwitchAccept = findViewById(R.id.mainActivitySwitchAccept)
    }

    private fun initialize() {
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
        mTextViewStatus.text = ""
    }

    fun onTitleTextClicked(view: View) {
        Toast.makeText(this, R.string.click_title_prompt, Toast.LENGTH_SHORT).show()
    }
}