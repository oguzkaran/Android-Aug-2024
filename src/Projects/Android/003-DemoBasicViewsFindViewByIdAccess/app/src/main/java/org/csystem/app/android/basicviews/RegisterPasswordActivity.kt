package org.csystem.app.android.basicviews

import android.os.Build
import android.os.Bundle
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
import org.csystem.app.android.basicviews.model.RegisterInfoModel

class RegisterPasswordActivity : AppCompatActivity() {
    private lateinit var mTextViewTitle: TextView
    private lateinit var mEditTextPassword: EditText
    private lateinit var mEditTextConfirmPassword: EditText
    private lateinit var mRegisterInfoModel: RegisterInfoModel

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
        //To be continued!...
        val password = mEditTextPassword.text.toString()
        val confirmPasswword = mEditTextConfirmPassword.text.toString()

        if (password == confirmPasswword) {
            //...
            mRegisterInfoModel.password = password
            Toast.makeText(this, mRegisterInfoModel.toString(), Toast.LENGTH_LONG).show()
        } else {
            AlertDialog.Builder(this)
                .setTitle(R.string.alert_dialog_confirm_password_title)
                .setMessage(R.string.alert_dialog_confirm_password_message)
                .setPositiveButton(R.string.alert_dialog_confirm_password_ok) { _, _ -> }
                .create()
                .show()
        }
    }

    fun onCloseButtonClicked(view: View) = finish()
}