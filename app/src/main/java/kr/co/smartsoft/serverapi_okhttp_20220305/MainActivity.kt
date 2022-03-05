package kr.co.smartsoft.serverapi_okhttp_20220305

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import kr.co.smartsoft.serverapi_okhttp_20220305.databinding.ActivityMainBinding
import kr.co.smartsoft.serverapi_okhttp_20220305.utils.ServerUtil

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupEvents()
        setValues()

    }

    fun setupEvents() {
        binding.btnLogin.setOnClickListener {
            val inputId = binding.edtId.text.toString()
            val inputPw = binding.edtPassword.text.toString()

            ServerUtil.postRequestLogin(inputId, inputPw)
        }
    }

    fun setValues() {

    }
}