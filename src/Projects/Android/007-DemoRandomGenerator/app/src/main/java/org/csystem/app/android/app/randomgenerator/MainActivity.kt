package org.csystem.app.android.app.randomgenerator

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import org.csystem.app.android.app.randomgenerator.databinding.ActivityMainBinding
import org.csystem.kotlin.util.string.randomTextEN
import java.lang.ref.WeakReference
import java.util.concurrent.ExecutorService
import javax.inject.Inject
import kotlin.random.Random

private const val STRING_GENERATE = 1
private const val END_OF_GENERATION = 2

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private var mHandler: Handler = GeneratorHandler(this)

    @Inject
    lateinit var threadPool: ExecutorService

    private class GeneratorHandler(activity: MainActivity): Handler(Looper.getMainLooper()) {
        private val mWeakReference = WeakReference(activity)
        private fun stringGenerateProc(text: String) {
            val activity = mWeakReference.get()!!

            activity.mBinding.text = text
            Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
        }

        private fun endOfGenerationProc() {
            Toast.makeText(mWeakReference.get(), R.string.end_of_generation_message, Toast.LENGTH_LONG).show()
            mWeakReference.get()!!.mBinding.enable = true
        }

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                STRING_GENERATE -> stringGenerateProc(msg.obj as String)
                END_OF_GENERATION -> endOfGenerationProc()
            }
        }
    }

    private fun forEachCallback() {
        val text = Random.randomTextEN(Random.nextInt(5, 11))

        mHandler.sendMessage(mHandler.obtainMessage(STRING_GENERATE, text))
        Thread.sleep(Random.nextLong(700, 1500))
    }

    private fun generateCallback(count: Int) {
        (1..count).forEach {_ -> forEachCallback() }
        mHandler.sendEmptyMessage(END_OF_GENERATION)
    }

    private fun initBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.activity = this
        mBinding.enable = true
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

    fun onGenerateButtonClicked() {
        try {
            val count = mBinding.count!!.toInt()
            mBinding.enable = false
            threadPool.execute { generateCallback(count) }
        } catch (_: NumberFormatException) {
            Toast.makeText(this, R.string.invalid_number_message, Toast.LENGTH_LONG).show()
        }
    }
}