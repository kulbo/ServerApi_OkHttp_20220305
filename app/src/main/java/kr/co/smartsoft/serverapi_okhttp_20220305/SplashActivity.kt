package kr.co.smartsoft.serverapi_okhttp_20220305

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import kr.co.smartsoft.serverapi_okhttp_20220305.utils.ContextUtil
import kr.co.smartsoft.serverapi_okhttp_20220305.utils.ServerUtil
import org.json.JSONObject

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

//        2.5초가 지나기 전에 미리 사용자 정보 조회 시도(토큰 유효성 정보)

//        2.5초 지나면 -> 자동로그인을 해도 되는지 -> 상황에 맞는 화면으로 이동
        val userAutoLogin = ContextUtil.getAutoLogin(mContext)
//          2) 로그인 시에 받아낸 토큰값이 지금도 유효한지?
        var isTokenOk = false   // 검사를 통과하면 true로 변경 예정.
//              2-1) 저장된 토큰이 있는지?(임시 방편)
//              2-2) 그 토큰이 사용자 정보를 불러내는지?(실제)
        ServerUtil.getRequestMyInfo(mContext, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(jsonObj: JSONObject) {
//                    내 정보를 잘 가져왔다면? => code 값을 200으로 내려준다.

//                    200이 아니라면? 무슨 이유던, 사용자 정보 로드 실패.
                val code = jsonObj.getInt("code")

                isTokenOk = (code == 200)
            }

        })

        val myHandler = Handler(Looper.getMainLooper())

        myHandler.postDelayed({
//          자동로그인을 해도 되는가?
//          1) 사용자가 자동로그인 의사를 OK 했는지?
//            if () {
////            둘다 OK라면 바로 메인화면으로
//            }
//            else {
////                아니라면, 로그인 화면으로
//            }
        },2500)
    }

}