package org.csystem.app.random.text.client

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import org.csystem.app.random.text.client.databinding.ActivityRandomTextBinding
import org.csystem.app.random.text.client.viewmodel.ServerInfo

class RandomTextActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityRandomTextBinding

    private lateinit var mServerInfo: ServerInfo
    fun initServerInfo() {
        mServerInfo = intent.getSerializableExtra("SERVER_INFO") as ServerInfo
    }

    fun initBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_random_text)
    }

    private fun initialize() {
        enableEdgeToEdge()
        initBinding()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initServerInfo()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }
}