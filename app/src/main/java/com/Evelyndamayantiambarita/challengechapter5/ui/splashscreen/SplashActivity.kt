package com.Evelyndamayantiambarita.challengechapter5.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.viewModels
import com.Evelyndamayantiambarita.challengechapter5.Constant
import com.Evelyndamayantiambarita.challengechapter5.MainActivity
import com.Evelyndamayantiambarita.challengechapter5.databinding.ActivitySplashBinding
import com.Evelyndamayantiambarita.challengechapter5.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = this.getSharedPreferences(Constant.Preferences.PREF_NAME, MODE_PRIVATE)
        val timer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                viewModel.onViewLoaded(pref)
            }
        }
        timer.start()

        bindViewModel()
        bindView()
    }

    private fun bindViewModel() {
        viewModel.shouldOpenOnMainActivity.observe(this) {
            if (it) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        viewModel.shouldOpenLoginActivity.observe(this) {
            if (it) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun bindView() {

    }
}