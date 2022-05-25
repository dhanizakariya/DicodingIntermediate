package com.dicoding.story.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.trimmedLength
import androidx.lifecycle.ViewModelProvider
import com.dicoding.story.databinding.ActivityLoginBinding
import com.dicoding.story.preference.Preference
import com.dicoding.story.preference.Preference.Companion.PREF_TOKEN
import com.dicoding.story.preference.Preference.Companion.STATE_KEY
import com.dicoding.story.viewModel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private lateinit var preference: Preference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAction()
        startViewModel()
        animation()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun animation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailEditText, View.ALPHA, 1f).setDuration(200)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(200)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordEditText, View.ALPHA, 1f).setDuration(200)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(200)
        val tab = ObjectAnimator.ofFloat(binding.tableLayout, View.ALPHA, 1f).setDuration(200)
        val login = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(200)

        AnimatorSet().apply {
            playSequentially(
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                tab,
                login)
            startDelay = 100
        }.start()
    }

    private fun startViewModel() {
        loading(false)
        loginViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[LoginViewModel::class.java]
        loginViewModel.getLoginResponse().observe(this) {
            loading(true)
            if (it != null) {
                Toast.makeText(
                    applicationContext,
                    "user ${it.loginResult.name} telah login",
                    Toast.LENGTH_SHORT
                ).show()
                val tokenUser = ("Bearer " + it.loginResult.token)
                Log.d("Failure", tokenUser)

                preference.put(PREF_TOKEN, tokenUser)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                loading(false)
            }
            if (it == null) {
                Toast.makeText(applicationContext, "Password atau Email Salah", Toast.LENGTH_LONG)
                    .show()
                loading(false)
            }
        }
    }

    private fun initAction() {
        val actionbar = supportActionBar
        actionbar!!.hide()

        loginViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[LoginViewModel::class.java]
        preference = Preference(this)
        binding.btnLogin.setOnClickListener {
            loading(true)
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            when {
                email.isEmpty() -> {
                    binding.emailEditTextLayout.error = "Masukkan email"
                    loading(false)
                }
                password.isEmpty() -> {
                    binding.passwordEditTextLayout.error = "Masukkan password"
                    loading(false)
                }
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    binding.emailEditTextLayout.error = "Email Salah"
                }
                password.trimmedLength() < 6 -> {
                    binding.passwordEditTextLayout.error = "Password kurang dari 6 karakter"
                }

                else -> {
                    loginViewModel.login(email, password)
                    preference.put(STATE_KEY, true)
                }
            }
        }
        loading(false)

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun loading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
}