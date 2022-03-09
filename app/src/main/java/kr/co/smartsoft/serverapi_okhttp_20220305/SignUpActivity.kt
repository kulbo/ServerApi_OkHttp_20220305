package kr.co.smartsoft.serverapi_okhttp_20220305

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import kr.co.smartsoft.serverapi_okhttp_20220305.databinding.ActivitySignUpBinding
import kr.co.smartsoft.serverapi_okhttp_20220305.utils.ServerUtil
import org.json.JSONObject

class SignUpActivity : BaseActivity() {

    lateinit var binding:ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        
        binding.btnEmailCheck.setOnClickListener { 
//            입력 이메일 값 추출
            val inputEmail = binding.edtEmail.text.toString()

//            서버의 중복확인 기능(/User_check - Get) Api 활용 => ServerUtil에 함수 추가, 가져다 활용
//            그 응답 code값에 따라 다른 문구 배치
            ServerUtil.getRequestDuplicatedCheck("EMAIL", inputEmail, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(jsonObject: JSONObject) {
                    val code = jsonObject.getInt("code")
                    runOnUiThread {
                        when(code) {
                            200 -> {
                                binding.txtEmailCheckResult.text = "사용해도 좋은 이메일입니다."
                            }
                            else -> {
                                binding.txtEmailCheckResult.text = "다른 이메일로 다시 검사해주세요"
                            }
                        }
                    }


                }

            })

        }
        binding.btnSignUp.setOnClickListener {
            val inputEmail = binding.edtEmail.text.toString()
            val inputPw = binding.edtPassword.text.toString()
            val inputNickname = binding.edtNickname.text.toString()

            ServerUtil.putRequestSignUp(inputEmail, inputPw, inputNickname, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObject: JSONObject) {
//                  회원가입 성공/실패 분기
                    val code = jsonObject.getInt("code")
                    if (code == 200) {
                        val dataObj = jsonObject.getJSONObject("data")
                        val userObj = dataObj.getJSONObject("user")
                        val nickname = userObj.getString("nick_name")

                        runOnUiThread {
                            Toast.makeText(mContext, "${nickname}님, 가입을 축하합니다.", Toast.LENGTH_SHORT).show()
                        }
                        finish()
                    }
                    else {
                        val message = jsonObject.getString("message")
                        runOnUiThread {
                            Toast.makeText(mContext, "실패 사유 : ${message}", Toast.LENGTH_SHORT).show()
                        }

                    }

                }

            })
        }
    }

    override fun setValues() {
    }


}