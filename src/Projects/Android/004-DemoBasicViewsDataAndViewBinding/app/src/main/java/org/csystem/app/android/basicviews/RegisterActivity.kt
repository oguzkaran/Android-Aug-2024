package org.csystem.app.android.basicviews

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import org.csystem.android.library.util.datetime.DataTimeFormatterUtil
import org.csystem.app.android.basicviews.constant.MARITAL_STATUS_TAGS
import org.csystem.app.android.basicviews.constant.REGISTER_INFO
import org.csystem.app.android.basicviews.databinding.ActivityRegisterBinding
import org.csystem.app.basicviews.data.service.model.UserInfoModel
import org.csystem.app.basicviews.data.service.UserService
import com.karandev.data.exception.service.DataServiceException
import java.time.LocalDateTime

private const val SAVE_REGISTER_INFO_LOG_TAG = "SAVE_REGISTER_INFO"
private const val LOAD_REGISTER_INFO_LOG_TAG = "LOAD_REGISTER_INFO"

class RegisterActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityRegisterBinding
    private lateinit var mUserService: UserService

    private fun fillRegisterInfo() {
        val lastEducationId = mBinding.registerActivityRadioGroupLastEducationDegree.checkedRadioButtonId
        val lastEducation = if (lastEducationId != -1)  findViewById<RadioButton>(lastEducationId).tag.toString().toInt() else 0

        mBinding.userInfo!!.also {
            it.maritalStatus = MARITAL_STATUS_TAGS[mBinding.selectedItemPosition]; it.lastEducation = lastEducation }
    }

    private fun loadRegisterInfo() {
        val userInfo = mBinding.userInfo!!

        mBinding.registerActivityEditTextUsername.setText(userInfo.username)
        mBinding.registerActivityEditTextName.setText(userInfo.name)
        mBinding.registerActivityEditTextEmail.setText(userInfo.email)

        mBinding.selectedItemPosition = MARITAL_STATUS_TAGS.indexOf(userInfo.maritalStatus)
        onClearButtonClicked()

        if (userInfo.lastEducation != 0)
            (mBinding.registerActivityRadioGroupLastEducationDegree.getChildAt(userInfo.lastEducation - 1) as RadioButton).isChecked = true
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
        mUserService.saveUserData(mBinding.userInfo!!)
        Log.i(SAVE_REGISTER_INFO_LOG_TAG, "User saved successfully")
        Toast.makeText(this, R.string.user_saved_successfully_prompt, Toast.LENGTH_SHORT).show()

        if (close)
            finish()
    }

    private fun saveRegisterInfo(close: Boolean) {
        try {
            val userInfo = mBinding.userInfo!!
            fillRegisterInfo()
            if (userInfo.username.isBlank()) {
                Toast.makeText(this, R.string.username_missing_prompt, Toast.LENGTH_LONG).show()
                return
            }

            if (!mUserService.isUserSaved(userInfo.username))
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

    private fun initMaritalStatusModel() {
        val maritalStatus = arrayOf(resources.getString(R.string.spinner_marital_status_single),
            resources.getString(R.string.spinner_marital_status_married),
            resources.getString(R.string.spinner_marital_status_divorced))

        mBinding.maritalStatusAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, maritalStatus)
    }

    private fun initModels() {
        mBinding.activity = this
        mBinding.title = resources.getString(R.string.text_view_register_title).format(DataTimeFormatterUtil.DATETIME_FORMATTER_TR.format(LocalDateTime.now()))
        mBinding.userInfo = UserInfoModel()
        initMaritalStatusModel()
    }

    private fun initBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register)
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

    fun onContinueButtonClicked() {
        fillRegisterInfo()
        Intent(this, RegisterPasswordActivity::class.java)
            .apply { putExtra(REGISTER_INFO, mBinding.userInfo!!); startActivity(this) }
        finish()
    }

    fun onClearButtonClicked() = mBinding.registerActivityRadioGroupLastEducationDegree.clearCheck()

    fun onCloseButtonClicked() {
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

    fun onLoadButtonClicked() {
        try {
            val username = mBinding.userInfo!!.username

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

            mBinding.userInfo!!.also {
                it.username = ri.username
                it.password = ri.password
                it.name = ri.name
                it.email = ri.email
                it.maritalStatus = ri.maritalStatus
                it.lastEducation = ri.lastEducation
            }
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

    fun onSaveButtonClicked() = saveRegisterInfo(false)

    fun onItemSelected(pos: Int) {
        Toast.makeText(this, mBinding.maritalStatusAdapter!!.getItem(pos), Toast.LENGTH_LONG).show()
    }
}