package com.Evelyndamayantiambarita.challengechapter5.ui.register

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.snackbar.Snackbar
import com.Evelyndamayantiambarita.challengechapter5.Constant
import com.Evelyndamayantiambarita.challengechapter5.MainActivity
import com.Evelyndamayantiambarita.challengechapter5.databinding.ActivityRegisterBinding
import com.Evelyndamayantiambarita.challengechapter5.database.MyDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()
    private val progressDialog: ProgressDialog by lazy { ProgressDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindView()
        bindViewModel()

        val db = MyDatabase.getInstance(this.applicationContext)
        val pref = this.getSharedPreferences(Constant.Preferences.PREF_NAME, MODE_PRIVATE)
        viewModel.onViewLoaded(db, pref)
    }

    private fun bindView() {
        binding.etUsername.doAfterTextChanged {
            viewModel.onChangeName(it.toString())
        }
        binding.etJob.doAfterTextChanged {
            viewModel.onChangeJob(it.toString())
        }
        binding.etEmail.doAfterTextChanged {
            viewModel.onChangeEmail(it.toString())
        }
        binding.etPassword.doAfterTextChanged {
            viewModel.onChangePassword(it.toString())
        }
        binding.btnRegister.setOnClickListener {
            viewModel.onValidate()
        }
    }

    private fun bindViewModel() {
        viewModel.shouldShowError.observe(this) {
            val snackbar = Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
            snackbar.view.setBackgroundColor(Color.RED)
            snackbar.show()
        }

        viewModel.shouldShowLoading.observe(this) {
            if (it) {
                progressDialog.setMessage("Loading...")
                progressDialog.show()
            } else {
                progressDialog.hide()
            }
        }

        viewModel.shouldOpenUpdateProfile.observe(this) {
            if (it) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}