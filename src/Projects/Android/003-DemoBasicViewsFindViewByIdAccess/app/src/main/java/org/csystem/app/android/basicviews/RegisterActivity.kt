package org.csystem.app.android.basicviews

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.csystem.app.android.basicviews.constant.MARITAL_STATUS_TAGS
import java.io.BufferedWriter
import java.io.File
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets

class RegisterActivity : AppCompatActivity() {
    private lateinit var mEditTextUsername: EditText
    private lateinit var mEditTextName: EditText
    private lateinit var mEditTextEmail: EditText
    private lateinit var mRadioGroupMaritalStatus: RadioGroup
    private lateinit var mRadioGroupLastEducationDegree: RadioGroup

    private fun saveAtCloseCallback() {
        //To be continued!...
        val username = mEditTextUsername.text.toString()
        val name = mEditTextName.text.toString()
        val email = mEditTextEmail.text.toString()
        val maritalStatus = findViewById<RadioButton>(mRadioGroupMaritalStatus.checkedRadioButtonId).tag as Char
        val lastEducationId = mRadioGroupLastEducationDegree.checkedRadioButtonId
        val lastEducation = if (lastEducationId != -1)  findViewById<RadioButton>(lastEducationId).tag.toString().toInt() else 0

        if (username.isBlank()) {
            Toast.makeText(this, R.string.username_missing_prompt, Toast.LENGTH_LONG).show()
            return
        }
        val file = File(filesDir, "${username}.txt")

        if (file.exists()) {
            Toast.makeText(this, R.string.username_already_saved_prompt, Toast.LENGTH_LONG).show()
            return
        }

        BufferedWriter(OutputStreamWriter(openFileOutput("${username}.txt", MODE_PRIVATE), StandardCharsets.UTF_8))
            .use {
                it.write("$username ")
                it.write("$name ")
                it.write("$email ")
                it.write("$maritalStatus ")
                it.write("$lastEducation ")
            }

        finish()
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

    private fun initialize() {
        initEditTexts()
        initRadioGroups()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initialize()
    }

    fun onRegisterButtonClicked(view: View) {
        val rbLastEducationDegreeId = mRadioGroupLastEducationDegree.checkedRadioButtonId
        val rbMaritalStatusValue = findViewById<RadioButton>(mRadioGroupMaritalStatus.checkedRadioButtonId).tag as Char
        val rbLastEducationDegreeValue = when (rbLastEducationDegreeId) {
            -1 -> 0
            else -> findViewById<RadioButton>(rbLastEducationDegreeId).tag.toString().toInt()
        }

        Toast.makeText(this, "$rbMaritalStatusValue, $rbLastEducationDegreeValue", Toast.LENGTH_LONG).show()
    }

    fun onClearButtonClicked(view: View) = mRadioGroupLastEducationDegree.clearCheck()

    fun onCloseButtonClicked(view: View) {
        val dlg = AlertDialog.Builder(this)
            .setTitle(R.string.alert_dialog_close_title)
            .setMessage(R.string.alert_dialog_close_message)
            .setPositiveButton(R.string.alert_dialog_close_save) { _, _ -> saveAtCloseCallback()}
            .setNegativeButton(R.string.alert_dialog_close_close) { _, _ -> finish()}
            .setNeutralButton(R.string.alert_dialog_close_cancel) {_, _ -> Toast.makeText(this, R.string.continue_register_prompt, Toast.LENGTH_SHORT).show()}
            .setOnCancelListener{Toast.makeText(this, R.string.continue_register_prompt, Toast.LENGTH_SHORT).show()}
            .create()
        dlg.show()
    }

}