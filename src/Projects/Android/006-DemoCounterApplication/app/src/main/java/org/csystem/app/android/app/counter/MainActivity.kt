package org.csystem.app.android.app.counter

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import org.csystem.android.library.util.datetime.module.annotation.DateTimeFormatterTRInterceptor
import org.csystem.app.android.app.counter.data.service.CounterDataService
import org.csystem.app.android.app.counter.databinding.ActivityMainBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.ExecutorService
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

private const val SECONDS_LIMIT = 20

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private var mStartedFlag = false
    private var mSeconds: Long = 0L
    private var mCounterScheduledFuture: ScheduledFuture<*>? = null
    private lateinit var mDateTimeScheduledFuture: ScheduledFuture<*>

    @Inject
    @Named("scheduledExecutorService")
    lateinit var counterScheduledThreadPool: ScheduledExecutorService

    @Inject
    @Named("scheduledExecutorService")
    lateinit var dateTimeScheduledThreadPool: ScheduledExecutorService

    @Inject
    @Named("threadPool")
    lateinit var threadPool: ExecutorService

    @Inject
    @DateTimeFormatterTRInterceptor
    lateinit var datetimeFormatter: DateTimeFormatter

    @Inject
    lateinit var counterDataService: CounterDataService

    private fun loadSecondsThreadCallback() {
        val seconds = counterDataService.findSecondsByCount(SECONDS_LIMIT)

        //...
    }

    private fun dateTimeSchedulerCallback() {
        val now = LocalDateTime.now()

        mBinding.dateTimeText = datetimeFormatter.format(now)
    }

    private fun startDateTimeScheduler() {
        mDateTimeScheduledFuture = dateTimeScheduledThreadPool.scheduleWithFixedDelay({dateTimeSchedulerCallback()}, 0L, 1L, TimeUnit.SECONDS)
    }

    private fun schedulerCallback() {
        val hour = mSeconds / 60 / 60
        val minute = mSeconds / 60 % 60
        val second = mSeconds % 60

        setCounterText1(hour, minute, second)
        setCounterText2(hour, minute, second)
        ++mSeconds
    }

    private fun setCounterText1(hour: Long, minute: Long, second: Long) {
        runOnUiThread { "$hour:$minute:$second".apply { mBinding.mainActivityTextViewCounter.text = this } }
    }

    private fun setCounterText2(hour: Long, minute: Long, second: Long) {
        "$hour:$minute:$second".apply { mBinding.counterText = this }
    }

    private fun initBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.activity = this
        mBinding.startStopButtonText = resources.getString(R.string.start_text)
        mBinding.counterText = "0:0:0"
        mBinding.dateTimeText = ""
    }

    private fun initialize() {
        enableEdgeToEdge()
        initBinding()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        startDateTimeScheduler()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mCounterScheduledFuture != null)
            mCounterScheduledFuture?.cancel(false)

        mDateTimeScheduledFuture.cancel(false)
    }

    fun onStartStopButtonClicked() {
        if (mStartedFlag) {
            mBinding.startStopButtonText = resources.getString(R.string.start_text)
            mCounterScheduledFuture?.cancel(false)
            mCounterScheduledFuture = null
        }
        else {
            mCounterScheduledFuture = counterScheduledThreadPool.scheduleWithFixedDelay({ schedulerCallback() }, 0, 1, TimeUnit.SECONDS)
            mBinding.startStopButtonText = resources.getString(R.string.stop_text)
        }
        mStartedFlag = !mStartedFlag
    }

    fun onLoadButtonClicked() {
        //Saniyeyi yükle
    }

    fun onLoadAllButtonClicked() {
        threadPool.execute{ loadSecondsThreadCallback() }
    }

    fun onResetButtonClicked() {
        counterDataService.saveSeconds(mSeconds - 1)
        mSeconds = 0
        mBinding.counterText = "0:0:0"
        mBinding.mainActivityTextViewCounter.text = mBinding.counterText
    }

    fun onRemoveAllButtonClicked() {
        threadPool.execute{ counterDataService.removeAll() }
    }
}