package com.example.feature_login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.feature_login.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        viewModel.isLoginEnabled.observe(this) { enabled ->
            binding.btnLogin.isEnabled = enabled
        }

        viewModel.navigateToMain.observe(this) { shouldNavigate ->
            if (shouldNavigate) {
                navigateToMain()
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener {
            viewModel.onLoginClick()
        }

        binding.btnVK.setOnClickListener {
            viewModel.onVKClick()
            openUrl("https://vk.com/")
        }

        binding.btnOK.setOnClickListener {
            viewModel.onOKClick()
            openUrl("https://ok.ru/")
        }

        binding.etEmail.setOnTextChangedListener { text ->
            viewModel.onEmailChanged(text.toString())
        }

        binding.etPassword.setOnTextChangedListener { text ->
            viewModel.onPasswordChanged(text.toString())
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun navigateToMain() {
        val intent = Intent().apply {
            setClassName(
                "com.example.effective_mobile_test_android",
                "com.example.effective_mobile_test_android.MainActivity"
            )
        }
        startActivity(intent)
        finish()
    }
}

// Extension function для отслеживания изменений текста
fun android.widget.EditText.setOnTextChangedListener(listener: (CharSequence?) -> Unit) {
    this.addTextChangedListener(object : android.text.TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            listener(s)
        }

        override fun afterTextChanged(s: android.text.Editable?) {}
    })

}