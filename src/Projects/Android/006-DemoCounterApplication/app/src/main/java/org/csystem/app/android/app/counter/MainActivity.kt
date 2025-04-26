package org.csystem.app.android.app.counter

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.karandev.data.exception.service.DataServiceException
import dagger.hilt.android.AndroidEntryPoint
import org.csystem.android.library.util.datetime.module.annotation.DateTimeFormatterTRInterceptor
import org.csystem.android.library.util.datetime.module.local.CurrentLocalDateModule
import org.csystem.android.library.util.datetime.module.local.CurrentLocalDateTimeModule
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

    private fun removeAllCallback() {
        try {
            counterDataService.removeAll()
            runOnUiThread {  mBinding.adapter?.clear() }
        }
        catch (ex: DataServiceException) {
            runOnUiThread { Toast.makeText(this, R.string.message_data_problem, Toast.LENGTH_LONG).show() }
        }
    }

    private fun getSecondByRecord(str: String): Long {
        val info = str.split('@')

        return info[0].toLong()
    }

    private fun showAlertForLimit(second: Long) {
        AlertDialog.Builder(this)
            .setTitle(R.string.alert_dialog_limit_title)
            .setMessage(R.string.alert_dialog_limit_message)
            .setPositiveButton(R.string.alert_dialog_limit_save_and_load) { _, _ -> saveAndLoadCallback(second); loadSeconds()}
            .setNegativeButton(R.string.alert_dialog_limit_load_without_save) { _, _ -> loadSecondInUIThread(second); loadSeconds()}
            .setNeutralButton(R.string.alert_dialog_cancel) { _, _ -> }
            .show()
    }
    private fun saveAndLoadCallback(second: Long) {
        counterDataService.save(mSeconds)
        loadSecond(second)
    }

    private fun loadSecondInUIThread(second: Long) {
        mSeconds = second
        setCounters()
    }

    private fun loadSecond(second: Long) {
        mSeconds = second
        schedulerCallback()
    }

    private fun loadSecondThreadCallback(record: String) {
        try {
            val second = getSecondByRecord(record)

            if (counterDataService.saveSecond(mSeconds)) {
                loadSecond(second)
                loadSeconds()
            }
            else
                runOnUiThread { showAlertForLimit(second) }
        }
        catch (ex: DataServiceException) {
            runOnUiThread { Toast.makeText(this, R.string.message_data_problem, Toast.LENGTH_LONG).show() }
        }
    }

    private fun loadSeconds() {
        try {
            val seconds = counterDataService.findAll() //Burada demo olarak hepsi okundu. Senaryoya göre parça parça okunabilir.
            runOnUiThread { mBinding.adapter?.clear(); mBinding.adapter?.addAll(seconds) }
        }
        catch (ex: DataServiceException) {
            runOnUiThread { Toast.makeText(this, R.string.message_data_problem, Toast.LENGTH_LONG).show() }
        }
    }

    private fun dateTimeSchedulerCallback() {
        val now = CurrentLocalDateTimeModule.provideLocalDateTime()

        mBinding.dateTimeText = datetimeFormatter.format(now)
    }

    private fun showAlertForReset() {
        AlertDialog.Builder(this)
            .setTitle(R.string.alert_dialog_reset_title)
            .setMessage(R.string.alert_dialog_reset_message)
            .setPositiveButton(R.string.alert_dialog_reset_positive_button_text) { _, _ ->  }
            .show()
    }

    private fun resetCallback() {
        try {
            if (!counterDataService.saveSecond(if (mSeconds == 0L) mSeconds else mSeconds - 1)) {
                runOnUiThread { showAlertForReset() }
                return
            }
            mSeconds = 0
            mBinding.counterText = "0:0:0"
            runOnUiThread { mBinding.mainActivityTextViewCounter.text = mBinding.counterText }
        } catch (ex: DataServiceException) {
            runOnUiThread { Toast.makeText(this, R.string.message_data_problem, Toast.LENGTH_LONG).show() }
        }
    }

    private fun startDateTimeScheduler() {
        mDateTimeScheduledFuture = dateTimeScheduledThreadPool.scheduleWithFixedDelay({dateTimeSchedulerCallback()}, 0L, 1L, TimeUnit.SECONDS)
    }

    private fun schedulerCallback() = doCounter{ h, m, s -> setCounterText1InUIThread(h, m, s);  setCounterText2(h, m, s) }
    private fun setCounters() = doCounter{ h, m, s -> setCounterText1(h, m, s); setCounterText2(h, m, s) }

    private fun doCounter(block: (Long, Long, Long) -> Unit) {
        val hour = mSeconds / 60 / 60
        val minute = mSeconds / 60 % 60
        val second = mSeconds % 60

        block(hour, minute, second)
        ++mSeconds
    }

    private fun setCounterText1InUIThread(hour: Long, minute: Long, second: Long) {
        runOnUiThread { "$hour:$minute:$second".apply { mBinding.mainActivityTextViewCounter.text = this } }
    }

    private fun setCounterText1(hour: Long, minute: Long, second: Long) {
        "$hour:$minute:$second".apply { mBinding.mainActivityTextViewCounter.text = this }
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
        mBinding.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, mutableListOf<String>())
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

    fun onConfigureButtonClicked() {
        mBinding.adapter?.clear()
        Intent(this, LimitConfigurationActivity::class.java).apply { startActivity(this) }
    }

    fun onLoadButtonClicked() {
        val pos = mBinding.mainActivityListViewSeconds.checkedItemPosition

        if (pos < mBinding.adapter!!.count && pos != -1) {
            val record = mBinding.adapter!!.getItem(mBinding.mainActivityListViewSeconds.checkedItemPosition)!!

            threadPool.execute { loadSecondThreadCallback(record) }
        }
    }

    fun onLoadAllButtonClicked() {
        threadPool.execute{ loadSeconds() }
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

    fun onResetButtonClicked() {
        mBinding.adapter?.clear()
        threadPool.execute{ resetCallback() }
    }

    fun onRemoveAllButtonClicked() {
        AlertDialog.Builder(this)
            .setTitle(R.string.alert_dialog_remove_all_title)
            .setMessage(R.string.alert_dialog_remove_all_message)
            .setPositiveButton(R.string.alert_dialog_remove_all_positive_button_text) { _, _ -> threadPool.execute{ removeAllCallback() } }
            .setNegativeButton(R.string.alert_dialog_remove_all_negative_button_text) { _, _ -> }
            .show()
    }
}