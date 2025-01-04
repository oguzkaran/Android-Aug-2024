package org.csystem.app.android.basicviews

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import org.csystem.app.android.basicviews.constant.USERNAME
import org.csystem.app.android.basicviews.databinding.ActivityManagementBinding

class ManagementActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityManagementBinding

    private fun initBinding()  {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_management)
        mBinding.activity = this
        mBinding.title = intent.getStringExtra(USERNAME) ?: resources.getString(R.string.checkbox_anonymous_text)
    }

    private fun initialize() {
        enableEdgeToEdge()
        initBinding()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    fun onUsersButtonClicked() {
        Intent(this, UsersActivity::class.java).apply { startActivity(this) }
    }

    fun onUserOperationsButtonClicked() {
        //...
    }

    fun onCloseButtonClicked() = finish()
}