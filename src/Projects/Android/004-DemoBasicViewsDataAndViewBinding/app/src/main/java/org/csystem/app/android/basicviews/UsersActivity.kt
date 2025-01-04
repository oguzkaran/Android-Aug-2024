package org.csystem.app.android.basicviews

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import org.csystem.app.android.basicviews.constant.DEFAULT_USER_COUNT
import org.csystem.app.android.basicviews.databinding.ActivityUsersBinding
import org.csystem.app.basicviews.data.service.model.UserModel
import org.csystem.app.basicviews.data.service.UserService
import com.karandev.data.exception.service.DataServiceException

class UsersActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityUsersBinding
    private lateinit var mUserService: UserService

    private fun initModels() {
        mBinding.activity = this
        mBinding.userText = ""
        mBinding.countText = "10"
        mBinding.usersAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ArrayList<UserModel>())
    }

    private fun initBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_users)
        initModels()
    }

    private fun initialize() {
        enableEdgeToEdge()
        initBinding()
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mUserService = UserService(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    fun onLoadUsersButtonClicked() {
        try {
            mBinding.usersAdapter!!.clear()

            val countStr = mBinding.countText!!
            var count = DEFAULT_USER_COUNT

            if (countStr.isNotBlank())
                count = countStr.toInt()

            if (count <= 0) {
                Toast.makeText(this, R.string.value_must_be_positive_prompt, Toast.LENGTH_LONG).show()
                return
            }

            val users = mUserService.findUsers(count) // Must be asynchronous

            mBinding.usersAdapter!!.addAll(users)
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

    fun onUserSelected(pos:Int) {
        val user = mBinding.usersAdapter!!.getItem(pos)

        mBinding.userText = user?.name
    }
}