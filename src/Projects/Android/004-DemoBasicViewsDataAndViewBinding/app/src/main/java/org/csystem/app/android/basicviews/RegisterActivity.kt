package org.csystem.app.android.basicviews

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.csystem.app.android.basicviews.constant.MARITAL_STATUS_TAGS
import org.csystem.app.android.basicviews.constant.REGISTER_INFO
import org.csystem.app.android.basicviews.data.service.UserService
import org.csystem.app.android.basicviews.model.UserInfoModel
import org.csystem.data.exception.DataServiceException

private const val SAVE_REGISTER_INFO_LOG_TAG = "SAVE_REGISTER_INFO"
private const val LOAD_REGISTER_INFO_LOG_TAG = "LOAD_REGISTER_INFO"

class RegisterActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener  {
    private lateinit var mEditTextUsername: EditText
    private lateinit var mEditTextName: EditText
    private lateinit var mEditTextEmail: EditText
    private lateinit var mRadioGroupLastEducationDegree: RadioGroup
    private lateinit var mUserInfoInfo: UserInfoModel
    private lateinit var mUserService: UserService
    private lateinit var mSpinnerMaritalStatus: Spinner

    private fun fillRegisterInfo() {
        val username = mEditTextUsername.text.toString()
        val name = mEditTextName.text.toString()
        val email = mEditTextEmail.text.toString()
        val lastEducationId = mRadioGroupLastEducationDegree.checkedRadioButtonId
        val lastEducation = if (lastEducationId != -1)  findViewById<RadioButton>(lastEducationId).tag.toString().toInt() else 0

        mUserInfoInfo.also { it.username = username; it.name = name; it.email = email
            it.maritalStatus = MARITAL_STATUS_TAGS[mSpinnerMaritalStatus.selectedItemPosition]; it.lastEducation = lastEducation }
    }

    private fun loadRegisterInfo() {
        mEditTextName.setText(mUserInfoInfo.name)
        mEditTextEmail.setText(mUserInfoInfo.email)
        mSpinnerMaritalStatus.setSelection(MARITAL_STATUS_TAGS.indexOf(mUserInfoInfo.maritalStatus))
        mRadioGroupLastEducationDegree.clearCheck()

        if (mUserInfoInfo.lastEducation != 0)
            (mRadioGroupLastEducationDegree.getChildAt(mUserInfoInfo.lastEducation - 1) as RadioButton).isChecked = true
    }

    private fun selectOptionIfUserSaved(close: Boolean) {
        Log.w(SAVE_REGISTER_INFO_LOG_TAG, "user already saved")

        AlertDialog.Builder(this)
            .setTitle(R.string.alert_dialog_user_already_saved_title)
            .setMessage(R.string.alert_dialog_user_already_saved_message)
            .setPositiveButton(R.string.alert_dialog_save) { _, _ -> saveData(close) }
            .setNegativeButton(R.string.alert_dialog_cancel) { _, _ -> }
            .create()
            .show()
    }

    private fun saveData(close: Boolean) {
        mUserService.saveUserData(mUserInfoInfo)
        Log.i(SAVE_REGISTER_INFO_LOG_TAG, "User saved successfully")
        Toast.makeText(this, R.string.user_saved_successfully_prompt, Toast.LENGTH_SHORT).show()

        if (close)
            finish()
    }

    private fun saveRegisterInfo(close: Boolean) {
        try {
            fillRegisterInfo()
            if (mUserInfoInfo.username.isBlank()) {
                Toast.makeText(this, R.string.username_missing_prompt, Toast.LENGTH_LONG).show()
                return
            }

            if (!mUserService.isUserSaved(mUserInfoInfo.username))
                saveData(close)
            else
                selectOptionIfUserSaved(close)
        } catch (ex: DataServiceException) {
            Log.e(SAVE_REGISTER_INFO_LOG_TAG, ex.message ?: "")
            Toast.makeText(this, R.string.data_problem_occurred_prompt, Toast.LENGTH_LONG).show()

        } catch (ex: Exception) {
            Log.e(SAVE_REGISTER_INFO_LOG_TAG, ex.message, ex)
            Toast.makeText(this, R.string.problem_occurred_prompt, Toast.LENGTH_LONG).show()
        }
    }

    private fun initMaritalStatusSpinner() {
        val maritalStatus = arrayOf(resources.getString(R.string.spinner_marital_status_single),
            resources.getString(R.string.spinner_marital_status_married),
            resources.getString(R.string.spinner_marital_status_divorced))

        mSpinnerMaritalStatus = findViewById<Spinner>(R.id.registerActivitySpinnerMaritalStatus)
            .apply {
                adapter = ArrayAdapter(this@RegisterActivity, android.R.layout.simple_spinner_dropdown_item, maritalStatus)
                onItemSelectedListener = this@RegisterActivity
            }
    }

    private fun initRadioGroups() {
        initMaritalStatusSpinner()
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
        mUserService = UserService(this)
        mUserInfoInfo = UserInfoModel()
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
            .apply { putExtra(REGISTER_INFO, mUserInfoInfo); startActivity(this) }
        finish()
    }

    fun onClearButtonClicked(view: View) = mRadioGroupLastEducationDegree.clearCheck()

    fun onCloseButtonClicked(view: View) {
        val dlg = AlertDialog.Builder(this)
            .setTitle(R.string.alert_dialog_close_title)
            .setMessage(R.string.alert_dialog_close_message)
            .setPositiveButton(R.string.alert_dialog_save) { _, _ -> saveRegisterInfo(true)}
            .setNegativeButton(R.string.alert_dialog_close_close) { _, _ -> finish()}
            .setNeutralButton(R.string.alert_dialog_cancel) { _, _ -> Toast.makeText(this, R.string.continue_register_prompt, Toast.LENGTH_SHORT).show()}
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

            val ri  = mUserService.findByUsername(username)

            if (ri == null) {
                Toast.makeText(this, R.string.username_not_found_prompt, Toast.LENGTH_SHORT)
                    .show()
                return
            }

            mUserInfoInfo = ri
            loadRegisterInfo()
            Toast.makeText(this, R.string.user_loaded_successfully_prompt, Toast.LENGTH_SHORT).show()
        } catch (ex: DataServiceException) {
            Log.e(LOAD_REGISTER_INFO_LOG_TAG, ex.message ?: "")
            Toast.makeText(this, R.string.data_problem_occurred_prompt, Toast.LENGTH_LONG).show()
        } catch (ex: Exception) {
            Log.e(LOAD_REGISTER_INFO_LOG_TAG, ex.message, ex)
            Toast.makeText(this, R.string.problem_occurred_prompt, Toast.LENGTH_LONG).show()
        }
    }

    fun onSaveButtonClicked(view: View) = saveRegisterInfo(false)

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p2: Long) {
        Toast.makeText(this, mSpinnerMaritalStatus.selectedItem as String, Toast.LENGTH_LONG).show()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}