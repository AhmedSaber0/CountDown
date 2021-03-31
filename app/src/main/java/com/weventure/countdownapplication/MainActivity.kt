package com.weventure.countdownapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.weventure.countdownapplication.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private var countDownTimer: CountDownTimer? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        binding.showCountDown.setOnClickListener {
            val timeValue = binding.timeEt.text.toString()
            if (validValue(timeValue)) {
                showCountDown(timeValue)
            }
        }
    }

    private fun validValue(timeValue: String): Boolean {
        return timeValue.toLong() > System.currentTimeMillis()
    }

    private fun showCountDown(endDate: String) {
        val currentTime = Calendar.getInstance().time
        val different = endDate.toLong() - currentTime.time
        countDownTimer = object : CountDownTimer(different, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var diff = millisUntilFinished
                val secondsInMilli: Long = 1000
                val minutesInMilli = secondsInMilli * 60
                val hoursInMilli = minutesInMilli * 60
                val daysInMilli = hoursInMilli * 24

                val elapsedDays = diff / daysInMilli
                diff %= daysInMilli

                val elapsedHours = diff / hoursInMilli
                diff %= hoursInMilli

                val elapsedMinutes = diff / minutesInMilli
                diff %= minutesInMilli

                val elapsedSeconds = diff / secondsInMilli

                binding.countdownTxv.text = String.format(getString(R.string.time_format), elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds)
            }

            override fun onFinish() {
                binding.countdownTxv.text = getString(R.string.done)
            }
        }.start()
    }

    override fun onPause() {
        super.onPause()
        countDownTimer?.cancel()
    }
}