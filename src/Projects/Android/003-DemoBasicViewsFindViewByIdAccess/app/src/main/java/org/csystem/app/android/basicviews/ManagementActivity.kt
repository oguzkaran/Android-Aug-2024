package org.csystem.app.android.basicviews

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.csystem.app.android.basicviews.constant.USERNAME

class ManagementActivity : AppCompatActivity() {
    private lateinit var mTextViewTitle: TextView
    private fun initTitle() {
        mTextViewTitle = findViewById(R.id.managementActivityTextViewTitle)
        mTextViewTitle.text = intent.getStringExtra(USERNAME) ?: resources.getString(R.string.checkbox_anonymous_text)
    }
    private fun initViews() {
        initTitle()
    }

    private fun initialize() {
        initViews()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_management)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initialize()
    }
}