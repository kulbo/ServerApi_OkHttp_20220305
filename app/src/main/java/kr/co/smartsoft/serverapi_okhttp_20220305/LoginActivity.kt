package kr.co.smartsoft.serverapi_okhttp_20220305

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import kr.co.smartsoft.serverapi_okhttp_20220305.databinding.ActivityLoginBinding
import kr.co.smartsoft.serverapi_okhttp_20220305.utils.ContextUtil
import kr.co.smartsoft.serverapi_okhttp_20220305.utils.ServerUtil
import org.json.JSONObject

class LoginActivity : BaseActivity() {

    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {
//        체크박스
        binding.autoLoginCheckbox.setOnCheckedChangeListener { compoundButton, isChecked ->
            Log.d("체크값변경", "${isChecked}로 변경됨")
            ContextUtil.setAutoLogIn(mContext, isChecked)
        }

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

//                        서버가 내려준 토큰값을 변수에 담아보자
                        val token = dataObj.getString("token")

                        ContextUtil.setToken(mContext, token)

//                        변수에 담진 토큰값(String)을 SharedPreferences에 담아두자.
//                        로구인 성공시에는 담기만 피료한 화면/클래스에서 꺼내서 사용.

//                        메인화면으로 이동 => 클래스의 객체화(UI 동작 X)

                        val myIntent = Intent(mContext, MainActivity::class.java)
                        startActivity(myIntent)
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

//        이전에 설정한 자동로그인 여부를 미리 체크해주자.
//        껏다 켜도 계속 반영 => 반 영구적으로 저장. => SharedPreferences에서 관리

//        저장되어있는 자동로그인 여부 값을 체크박스에 반영
        binding.autoLoginCheckbox.isChecked = ContextUtil.getAutoLogin(mContext)
    }
}