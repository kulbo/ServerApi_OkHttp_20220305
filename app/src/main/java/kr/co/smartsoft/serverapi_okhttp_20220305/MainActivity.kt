package kr.co.smartsoft.serverapi_okhttp_20220305

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import kr.co.smartsoft.serverapi_okhttp_20220305.databinding.ActivityMainBinding
import kr.co.smartsoft.serverapi_okhttp_20220305.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {
        binding.btnSignUp.setOnClickListener {
//            단순 화면 이동
            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)
        }
        binding.btnLogin.setOnClickListener {
            val inputId = binding.edtId.text.toString()
            val inputPw = binding.edtPassword.text.toString()

            ServerUtil.postRequestLogin(inputId, inputPw, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObject: JSONObject) {
//                    화면의 입장에서 로그인 결과를 받아서 처리할 코드
//                    서버에 다녀오고 실행 : 라이브러러가 자동으로 백구라운드에서 만들어 둔 코드.

                    val code = jsonObject.getInt("code")
                    if (code == 200) {
                        val dataObj = jsonObject.getJSONObject("data")
                        val userObj = dataObj.getJSONObject("user")
                        val nickname = userObj.getString("nick_name")
                        runOnUiThread {
                            Toast.makeText(mContext, "${nickname}님 환영합니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else {
                        val message = jsonObject.getString("message")
//                        토스트 : UI조작. => 백그라운드에서 UI를 건드리면 위험한동작으로 간주하고 앱을 강제 종료
                        runOnUiThread {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            })
        }
    }

    override fun setValues() {

    }
}