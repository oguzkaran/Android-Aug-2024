package org.csystem.app.android.randomusers

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import org.csystem.app.android.randomusers.api.me.dto.RandomUser
import org.csystem.app.android.randomusers.api.me.dto.RandomUserInfo
import org.csystem.app.android.randomusers.api.me.service.IRandomUserService
import org.csystem.app.android.randomusers.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    @Inject
    lateinit var randomUserService: IRandomUserService

    private fun findRandomUserCallback() : Callback<RandomUserInfo> {
        return object: Callback<RandomUserInfo> {
            override fun onResponse(call: Call<RandomUserInfo?>, response: Response<RandomUserInfo?>) {
                Log.i("Response:", response.raw().toString())
                if (response.code() != HttpURLConnection.HTTP_OK) {
                    Log.e("Status", response.code().toString())
                    Toast.makeText(this@MainActivity, "Unsuccessful operation", Toast.LENGTH_LONG).show()
                    return
                }

                response.headers().names().forEach { Log.i("Header", it) }
                if (response.body()?.users == null) {
                    Toast.makeText(this@MainActivity, "Limit exhausted", Toast.LENGTH_LONG).show()
                    return
                }

                response.body()!!.users.forEach { mBinding.adapter!!.add(it) }
            }

            override fun onFailure(call: Call<RandomUserInfo?>, e: Throwable) {
                Log.e("onFailure", e.message.toString())
                Toast.makeText(this@MainActivity, "Error occurred while connection", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.count = "10"
        mBinding.activity = this
        mBinding.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ArrayList<RandomUser>())
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

    fun onGetUsersButtonClicked() {
        try {
            mBinding.adapter?.clear()
            val count = mBinding.count!!.toInt()
            if (count <= 0) {
                Toast.makeText(this, R.string.message_positive, Toast.LENGTH_LONG).show()
                return
            }

            randomUserService.findUser(count).enqueue(findRandomUserCallback())

        } catch (_: NumberFormatException) {
            Toast.makeText(this, R.string.message_invalid_value, Toast.LENGTH_LONG).show()
        }
    }

    fun onUserSelected(pos: Int) {
        Intent(this, RandomUserDetailsActivity::class.java).apply { putExtra("RANDOM_USER", mBinding.adapter!!.getItem(pos)); startActivity(this) }
    }
}