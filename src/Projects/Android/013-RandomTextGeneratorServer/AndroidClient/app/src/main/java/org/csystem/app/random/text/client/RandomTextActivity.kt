package org.csystem.app.random.text.client

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.karandev.util.net.TcpUtil
import org.csystem.app.random.text.client.constant.SERVER_INFO_KEY
import org.csystem.app.random.text.client.databinding.ActivityRandomTextBinding
import org.csystem.app.random.text.client.viewmodel.ServerInfo
import org.csystem.app.random.text.client.viewmodel.ServerParam
import java.net.Socket
import kotlin.concurrent.thread

class RandomTextActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityRandomTextBinding

    private lateinit var mServerInfo: ServerInfo


    private fun getTextsForEachCallback(socket: Socket) {
        val text = TcpUtil.receiveStringViaLength(socket)

        runOnUiThread { mBinding.adapter?.add(text) }
    }

    private fun getTextsCallback(count:Long, minLength: Int, maxLength: Int) {
        try {
            Socket(mServerInfo.host, mServerInfo.port.toInt()).use { s ->
                TcpUtil.sendLong(s, count)
                TcpUtil.sendInt(s, minLength)
                TcpUtil.sendInt(s, maxLength)

                val statusCode = TcpUtil.receiveInt(s)

                Log.i("Status Code", "$statusCode")

                if (statusCode != 0) {
                    runOnUiThread {
                        Toast.makeText(this, "Invalid values", Toast.LENGTH_LONG).show()
                    }
                    return
                }

                generateSequence(0) {it + 1}.takeWhile { it < count }.forEach { _ -> getTextsForEachCallback(s)  }
            }
        }
        catch (ex: Exception) {
            runOnUiThread {
                Toast.makeText(this, "Exception occurred: ${ex.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun serverInfo() = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU)
        intent.getSerializableExtra(SERVER_INFO_KEY, ServerInfo::class.java) as ServerInfo
    else
        intent.getSerializableExtra(SERVER_INFO_KEY) as ServerInfo

    private fun initServerInfo() {
        mServerInfo = serverInfo()
    }

    fun initBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_random_text)
        mBinding.activity = this
        mBinding.serverParam = ServerParam()
        mBinding.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ArrayList<String>())
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

    fun onGetButtonClicked() {
        try {
            val count = mBinding.serverParam!!.count.toLong()
            val minLength = mBinding.serverParam!!.minLength.toInt()
            val maxLength = mBinding.serverParam!!.maxLength.toInt()

            thread{ getTextsCallback(count, minLength, maxLength) }
        }
        catch (ex: NumberFormatException) {
            Log.i("number-format", ex.message.toString())
            Toast.makeText(this, resources.getString(R.string.not_number_message), Toast.LENGTH_LONG).show()
        }
    }
}