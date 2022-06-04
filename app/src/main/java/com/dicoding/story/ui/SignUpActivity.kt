package com.dicoding.story.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.trimmedLength
import androidx.lifecycle.ViewModelProvider
import com.dicoding.story.databinding.ActivitySignUpBinding
import com.dicoding.story.viewModel.SignUpViewModel

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var signUpViewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        animation()
        initAction()
        startViewModel()
    }

    private fun animation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val nameEditText = ObjectAnimator.ofFloat(binding.nameEditText, View.ALPHA, 1f).setDuration(200)
        val nameEditTextLayout = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(200)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailEditText, View.ALPHA, 1f).setDuration(200)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(200)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordEditText, View.ALPHA, 1f).setDuration(200)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(200)
        val tab = ObjectAnimator.ofFloat(binding.tableLayout, View.ALPHA, 1f).setDuration(200)
        val signUp = ObjectAnimator.ofFloat(binding.btnSignup, View.ALPHA, 1f).setDuration(200)

        AnimatorSet().apply {
            playSequentially(
                nameEditText,
                nameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                tab,
                signUp)
            startDelay = 100
        }.start()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun startViewModel() {
        signUpViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[SignUpViewModel::class.java]

        signUpViewModel.getRegisterResponse().observe(this) {
            if (it != null) {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
                startActivity(Intent(this, LoginActivity::class.java))
            }
            if (it == null) {
                Toast.makeText(applicationContext, "Failed to create User", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun initAction() {
        val actionbar = supportActionBar
        actionbar!!.hide()

        binding.btnSignup.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            when {
                name.isEmpty() -> {
                    binding.nameEditTextLayout.error = "Masukkan email"
                }
                email.isEmpty() -> {
                    binding.emailEditTextLayout.error = "Masukkan email"
                }
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    binding.emailEditTextLayout.error = "Email Tidak Valid"
                }
                password.isEmpty() -> {
                    binding.passwordEditTextLayout.error = "Masukkan password"
                }
                password.trimmedLength() < 6 -> {
                    binding.passwordEditTextLayout.error = "Password harus lebih dari 6 karakter"
                }
                else -> {
                    signUpViewModel.register(name, email, password)
                }
            }
        }

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }


}