package org.csystem.app.android.first

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import org.csystem.app.android.first.databinding.ActivityMakeUpperBinding

class MakeUpperActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMakeUpperBinding

    private fun initUI() {
        val text = intent.getStringExtra("TEXT")

        if (text == null) {
            Toast.makeText(this, "Invalid TEXT", Toast.LENGTH_LONG).show()
            return
        }

        mBinding.text = text
        mBinding.upperText = text.uppercase()
    }

    private fun initBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_make_upper)
        initUI()
    }

    private fun initialize() {
        enableEdgeToEdge()
        initBinding()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.makeUpper)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }
}