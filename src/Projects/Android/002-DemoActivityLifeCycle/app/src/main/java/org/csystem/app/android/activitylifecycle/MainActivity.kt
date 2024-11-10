package org.csystem.app.android.activitylifecycle

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var mFormatter: DateTimeFormatter

    private fun prompt(resId: Int, time: LocalTime) {
        Toast.makeText(this, resources.getString(resId).format(mFormatter.format(time)), Toast.LENGTH_LONG).show()
    }

    private fun initialize() {
        mFormatter = DateTimeFormatter.ofPattern("KK:mm:ss.n")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initialize()
        prompt(R.string.on_create_message, LocalTime.now())
        Toast.makeText(this, if (savedInstanceState == null) "Not created by system" else "Created by system", Toast.LENGTH_LONG).show()
    }

    override fun onStart() {
        super.onStart()
        prompt(R.string.on_start_message, LocalTime.now())
    }

    override fun onResume() {
        super.onResume()
        prompt(R.string.on_resume_message, LocalTime.now())
    }

    override fun onPause() {
        super.onPause()
        prompt(R.string.on_pause_message, LocalTime.now())
    }

    override fun onStop() {
        super.onStop()
        prompt(R.string.on_stop_message, LocalTime.now())
    }

    override fun onRestart() {
        super.onRestart()
        prompt(R.string.on_restart_message, LocalTime.now())
    }

    override fun onDestroy() {
        super.onDestroy()
        prompt(R.string.on_destroy_message, LocalTime.now())
    }
}