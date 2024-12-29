package org.csystem.app.android.basicviews

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.csystem.app.android.basicviews.constant.DEFAULT_USER_COUNT
import org.csystem.app.android.basicviews.model.UserModel
import org.csystem.app.basicviews.data.service.UserService
import org.csystem.data.exception.DataServiceException

class UsersActivity : AppCompatActivity() {
    private lateinit var mTextViewUser: TextView
    private lateinit var mEditTextCount: EditText
    private lateinit var mListViewUsers: ListView
    private lateinit var mArrayAdapterUsers: ArrayAdapter<UserModel>
    private lateinit var mUserService: UserService

    private fun itemClickListenerCallback(pos:Int) {
        val user = mArrayAdapterUsers.getItem(pos)

        Toast.makeText(this, user?.name, Toast.LENGTH_SHORT).show()
    }

    private fun initListViewUsers() {
        mListViewUsers = findViewById(R.id.usersActivityListViewUsers)
        mListViewUsers.setOnItemClickListener { _, _, pos, _ ->  itemClickListenerCallback(pos)}
    }

    private fun initViews() {
        mTextViewUser = findViewById(R.id.usersActivityTextViewUser)
        mEditTextCount = findViewById(R.id.usersActivityEditTextCount)
        initListViewUsers()
    }

    private fun initialize() {
        mUserService = UserService(this)
        initViews()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_users)
        initialize()
    }

    fun onLoadUsersButtonClicked(view: View) {
        try {
            if (this::mArrayAdapterUsers.isInitialized)
                mArrayAdapterUsers.clear()

            val countStr = mEditTextCount.text.toString()
            var count = DEFAULT_USER_COUNT

            if (countStr.isNotBlank())
                count = countStr.toInt()

            if (count <= 0) {
                Toast.makeText(this, R.string.value_must_be_positive_prompt, Toast.LENGTH_LONG).show()
                return
            }

            val users = mUserService.findUsers(count) // Must be asynchronous

            mArrayAdapterUsers = ArrayAdapter(this, android.R.layout.simple_list_item_1, users)
                    .apply { mListViewUsers.adapter = this }
        } catch (_: NumberFormatException) {
            AlertDialog.Builder(this)
                .setTitle(R.string.alert_dialog_user_login_problem_title)
                .setMessage(R.string.invalid_count_value_prompt)
                .setPositiveButton(R.string.alert_dialog_ok) {_, _ ->}
                .create()
                .show()
        }
        catch (ex: DataServiceException) {
            Toast.makeText(this, R.string.data_problem_occurred_prompt, Toast.LENGTH_LONG).show()
        } catch (ex: Exception) {
            Toast.makeText(this, R.string.problem_occurred_prompt, Toast.LENGTH_LONG).show()
        }
    }
}