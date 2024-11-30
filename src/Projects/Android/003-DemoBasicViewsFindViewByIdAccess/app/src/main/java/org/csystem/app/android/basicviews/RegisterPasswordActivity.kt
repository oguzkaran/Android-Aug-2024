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
import org.csystem.app.android.basicviews.constant.USERS_FILE_PATH
import org.csystem.app.android.basicviews.constant.USERS_FORMAT

import org.csystem.app.android.basicviews.model.RegisterInfoModel
import java.io.EOFException
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

private const val REGISTER_USER_INFO_LOG_TAG = "REGISTER_USER_INFO"
private const val USER_EXISTS_INFO_LOG_TAG = "USER_EXIST_INFO"

class RegisterPasswordActivity : AppCompatActivity() {
    private lateinit var mTextViewTitle: TextView
    private lateinit var mEditTextPassword: EditText
    private lateinit var mEditTextConfirmPassword: EditText
    private lateinit var mRegisterInfoModel: RegisterInfoModel

    private fun registerUser() {
        try {
            ObjectOutputStream(FileOutputStream(File(filesDir, USERS_FILE_PATH), true))
                .use { it.writeObject(mRegisterInfoModel) }
            File(filesDir, USERS_FORMAT.format("${mRegisterInfoModel.username}.txt")).delete()
        } catch (ex: IOException) {
            Log.e(REGISTER_USER_INFO_LOG_TAG, ex.message ?: "")
            Toast.makeText(this, R.string.io_problem_occurred_prompt, Toast.LENGTH_LONG).show()

        } catch (ex: Exception) {
            Log.e(REGISTER_USER_INFO_LOG_TAG, ex.message, ex)
            Toast.makeText(this, R.string.problem_occurred_prompt, Toast.LENGTH_LONG).show()
        }
    }

    private fun registerUserInfo(password: String)  {
        if (userExists()) {
            Toast.makeText(this, R.string.user_already_registered_prompt, Toast.LENGTH_LONG).show()
            return
        }
        mRegisterInfoModel.password = password
        registerUser()
        Toast.makeText(this, R.string.user_registered_successfully_prompt, Toast.LENGTH_LONG).show()
    }

    private fun userExistsCallback(fis: FileInputStream): Boolean {
        var result = false

        try {
            while (true) {
                val ois = ObjectInputStream(fis)
                val ri = ois.readObject() as RegisterInfoModel

                if (ri.username == mRegisterInfoModel.username) {
                    result = true
                    break
                }
            }
        } catch (_: EOFException) {

        }

        return result
    }

    private fun userExists(): Boolean {
        var result = false

        try {
            result = FileInputStream(File(filesDir, USERS_FILE_PATH)).use(::userExistsCallback)
        } catch (ex: IOException) {
            Log.e(USER_EXISTS_INFO_LOG_TAG, ex.message ?: "")
            Toast.makeText(this, R.string.io_problem_occurred_prompt, Toast.LENGTH_LONG).show()

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
                .setPositiveButton(R.string.alert_dialog_confirm_password_ok) { _, _ -> }
                .create()
                .show()
    }

    fun onCloseButtonClicked(view: View) = finish()
}