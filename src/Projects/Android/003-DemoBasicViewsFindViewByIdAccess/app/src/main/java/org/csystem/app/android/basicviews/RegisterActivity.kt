package org.csystem.app.android.basicviews

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.csystem.app.android.basicviews.constant.MARITAL_STATUS_TAGS
import org.csystem.app.android.basicviews.constant.REGISTER_INFO
import org.csystem.app.android.basicviews.model.RegisterInfoModel
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets

const val SAVE_REGISTER_INFO = "SAVE_REGISTER_INFO"
const val LOAD_REGISTER_INFO = "LOAD_REGISTER_INFO"
const val DELIMITER = ":"

class RegisterActivity : AppCompatActivity() {
    private lateinit var mEditTextUsername: EditText
    private lateinit var mEditTextName: EditText
    private lateinit var mEditTextEmail: EditText
    private lateinit var mRadioGroupMaritalStatus: RadioGroup
    private lateinit var mRadioGroupLastEducationDegree: RadioGroup
    private lateinit var mRegisterInfo: RegisterInfoModel

    private fun fillRegisterInfo() {
        val username = mEditTextUsername.text.toString()
        val name = mEditTextName.text.toString()
        val email = mEditTextEmail.text.toString()
        val maritalStatus = findViewById<RadioButton>(mRadioGroupMaritalStatus.checkedRadioButtonId).tag as Char
        val lastEducationId = mRadioGroupLastEducationDegree.checkedRadioButtonId
        val lastEducation = if (lastEducationId != -1)  findViewById<RadioButton>(lastEducationId).tag.toString().toInt() else 0

        mRegisterInfo.also { it.username = username; it.name = name; it.email = email
            it.maritalStatus = maritalStatus; it.lastEducation = lastEducation }
    }

    private fun loadRegisterInfo(br: BufferedReader) {
        val str = br.readLine() ?: throw IOException()
        val info = str.split(DELIMITER)

        mEditTextName.setText(info[1])
        mEditTextEmail.setText(info[2])
        (mRadioGroupMaritalStatus.getChildAt(MARITAL_STATUS_TAGS.indexOf(info[3][0])) as RadioButton).isChecked = true
        mRadioGroupLastEducationDegree.clearCheck()
        val lastEducation = info[4].toInt()

        if (lastEducation != 0)
            (mRadioGroupLastEducationDegree.getChildAt(lastEducation - 1) as RadioButton).isChecked = true
    }

    private fun writeRegisterInfo(bw: BufferedWriter) {
        bw.write("${mRegisterInfo.username}$DELIMITER")
        bw.write("${mRegisterInfo.name}$DELIMITER")
        bw.write("${mRegisterInfo.email}$DELIMITER")
        bw.write("${mRegisterInfo.maritalStatus}$DELIMITER")
        bw.write("${mRegisterInfo.lastEducation}")
    }

    private fun saveRegisterInfo() {
        try {
            fillRegisterInfo()
            if (mRegisterInfo.username.isBlank()) {
                Toast.makeText(this, R.string.username_missing_prompt, Toast.LENGTH_LONG).show()
                return
            }
            val file = File(filesDir, "${mRegisterInfo.username}.txt")

            if (file.exists()) {
                Log.w(SAVE_REGISTER_INFO, "user already saved")

                Toast.makeText(this, R.string.username_already_saved_prompt, Toast.LENGTH_LONG)
                    .show()
                return
            }

            BufferedWriter(OutputStreamWriter(openFileOutput("${mRegisterInfo.username}.txt", MODE_PRIVATE),
                StandardCharsets.UTF_8)).use(::writeRegisterInfo)

            Log.i(SAVE_REGISTER_INFO, "User saved successfully")
            Toast.makeText(this, R.string.user_saved_successfully_prompt, Toast.LENGTH_SHORT).show()
        } catch (ex: IOException) {
            Log.e(SAVE_REGISTER_INFO, ex.message ?: "")
            Toast.makeText(this, R.string.io_problem_occurred_prompt, Toast.LENGTH_LONG).show()

        } catch (ex: Exception) {
            Log.e(SAVE_REGISTER_INFO, ex.message, ex)
            Toast.makeText(this, R.string.problem_occurred_prompt, Toast.LENGTH_LONG).show()
        }
    }

    private fun initMaritalStatusRadioGroup() {
        mRadioGroupMaritalStatus = findViewById(R.id.registerActivityRadioGroupMaritalStatus)
        (0..<mRadioGroupMaritalStatus.childCount).forEach { mRadioGroupMaritalStatus.getChildAt(it).tag = MARITAL_STATUS_TAGS[it] }
        mRadioGroupMaritalStatus.setOnCheckedChangeListener{ _, id -> Toast.makeText(this, findViewById<RadioButton>(id).text, Toast.LENGTH_SHORT).show() }
    }

    private fun initRadioGroups() {
        initMaritalStatusRadioGroup()
        mRadioGroupLastEducationDegree = findViewById(R.id.registerActivityRadioGroupLastEducationDegree)
    }

    private fun initEditTexts() {
        mEditTextUsername = findViewById(R.id.registerActivityEditTextUsername)
        mEditTextName = findViewById(R.id.registerActivityEditTextName)
        mEditTextEmail = findViewById(R.id.registerActivityEditTextEmail)
    }

    private fun initViews() {
        initEditTexts()
        initRadioGroups()
    }

    private fun initialize() {
        mRegisterInfo = RegisterInfoModel()
        initViews()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initialize()
    }

    fun onContinueButtonClicked(view: View) {
        fillRegisterInfo()
        Intent(this, RegisterPasswordActivity::class.java)
            .apply { putExtra(REGISTER_INFO, mRegisterInfo); startActivity(this) }
        finish()
    }

    fun onClearButtonClicked(view: View) = mRadioGroupLastEducationDegree.clearCheck()

    fun onCloseButtonClicked(view: View) {
        val dlg = AlertDialog.Builder(this)
            .setTitle(R.string.alert_dialog_close_title)
            .setMessage(R.string.alert_dialog_close_message)
            .setPositiveButton(R.string.alert_dialog_close_save) { _, _ -> saveRegisterInfo(); finish()}
            .setNegativeButton(R.string.alert_dialog_close_close) { _, _ -> finish()}
            .setNeutralButton(R.string.alert_dialog_close_cancel) {_, _ -> Toast.makeText(this, R.string.continue_register_prompt, Toast.LENGTH_SHORT).show()}
            .setOnCancelListener{Toast.makeText(this, R.string.continue_register_prompt, Toast.LENGTH_SHORT).show()}
            .create()
        dlg.show()
    }

    fun onLoadButtonClicked(view: View) {
        try {
            val username = mEditTextUsername.text.toString()

            if (username.isBlank()) {
                Toast.makeText(this, R.string.username_missing_prompt, Toast.LENGTH_SHORT).show()
                return
            }

            val file = File(filesDir, "${username}.txt")

            if (!file.exists()) {
                Toast.makeText(this, R.string.username_not_found_prompt, Toast.LENGTH_SHORT)
                    .show()
                return
            }

            BufferedReader(InputStreamReader(openFileInput("${username}.txt"), StandardCharsets.UTF_8))
                .use(::loadRegisterInfo)

            Toast.makeText(this, R.string.user_loaded_successfully_prompt, Toast.LENGTH_SHORT).show()
        } catch (ex: IOException) {
            Log.e(LOAD_REGISTER_INFO, ex.message ?: "")
            Toast.makeText(this, R.string.io_problem_occurred_prompt, Toast.LENGTH_LONG).show()
        } catch (ex: Exception) {
            Log.e(LOAD_REGISTER_INFO, ex.message, ex)
            Toast.makeText(this, R.string.problem_occurred_prompt, Toast.LENGTH_LONG).show()
        }
    }

    fun onSaveButtonClicked(view: View) = saveRegisterInfo()


}