package kr.co.smartsoft.serverapi_okhttp_20220305

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
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

//        만약 이메일 /닉네임 중복검사를 통과하지 못한 상태라면
//        토스트로 "이메일 중복검사를 통과해야 합니다." 등의 문구만 출력 가입 진행 x
//        hint) 진행상황이 아니라면 return 처리하면 함수 종료
        binding.edtEmail.addTextChangedListener {
//        내용이 한글자라도 바뀌면, 무조건 재검사 요구 문장.
            binding.txtEmailCheckResult.text = "중복 확인을 해주세요"
        }
        binding.edtNickname.addTextChangedListener {
//        내용이 한글자라도 바뀌면, 무조건 재검사 요구 문장.
            binding.txtNicknameCheckResult.text = "중복 확인을 해주세요"
        }
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
        binding.btnNicknameCheck.setOnClickListener {
//            입력 이메일 값 추출
            val inputNickname = binding.edtNickname.text.toString()

//            서버의 중복확인 기능(/User_check - Get) Api 활용 => ServerUtil에 함수 추가, 가져다 활용
//            그 응답 code값에 따라 다른 문구 배치
            ServerUtil.getRequestDuplicatedCheck("NICK_NAME", inputNickname, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(jsonObject: JSONObject) {
                    val code = jsonObject.getInt("code")
                    runOnUiThread {
                        when(code) {
                            200 -> {
                                binding.txtNicknameCheckResult.text = "사용해도 좋은 닉네임입니다."
                            }
                            else -> {
                                binding.txtNicknameCheckResult.text = "다른 닉네임을 다시 검사해주세요"
                            }
                        }
                    }


                }

            })

        }
        binding.btnSignUp.setOnClickListener {

//            [도전과제] 이메일 - @가 반드시 포함되어야함. 비밀번호 - 8자리 이상이어야함. 닉네임 - 2자 이상.
//            [도전과제] 만약 이메일 / 닉네임 중복검사를 통과하지 못하면
//            토스트로 "이메일 중복검사를 통과해야 합니다." 등의 문구만 출력, 가입 진행 X
//            hint) 진행할 상황이 아니라면, return 처리 하면 함수 종료

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