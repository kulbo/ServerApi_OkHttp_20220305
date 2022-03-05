package kr.co.smartsoft.serverapi_okhttp_20220305.utils

import android.util.Log
import okhttp3.*
import java.io.IOException

class ServerUtil {

//    서버에 Request를 날리는 역할
//    코틀린에서 static 에 해당하는 개념 conpanion object{  } 에 만들자
    companion object {
//    서버 컴퓨터 주소만 변수로 저장(관리 일원화)
        private val BASE_URL = "http://54.180.52.26"
        private val EMAIL = "email"
        private val PASSWORD = "password"
//      로그인 기는 호출 함수
        fun postRequestLogin(id:String, pw:String) {
//            Request 제작 -> 실제 호출 -> 서버의 응답을 화면에 전달
//            제작 1) 어느 주소로(url)로 접근할 지? 서버 주소 + 기능 주소
            val urlString = "${BASE_URL}/user"
//            제작 2 파라미터 담아주기 => 어떤 이름표/어느 공간에
            val formData = FormBody.Builder()
                .add(EMAIL, id)
                .add(PASSWORD, pw)
                .build()
//            제작 3) 모든 Request 정보를 종합한 객체 생성.(어느 주소로 + 어느 메쏘드로 + 어떤 파라미터를)
            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .build()
//          종합한 Request로 실제 호출을 해줘야
//          실제 호출 : 앱이 클라이언트로써 동작 > OKHttpClient 클래스
            val client = OkHttpClient()
//            OkHttpClient 객체를 이용 > 서버에 로그인 기능 실제 호출
//            => 서버에 다녀와서 할 일을 등록 : enqueue(Callback)
            client.newCall(request).enqueue( object : Callback{

                override fun onFailure(call: Call, e: IOException) {
//                실패 : 서버 연결 자체를 실패. 응답이 오지 않았다.
//                ex. 인터넷 끊김 등

                }

                override fun onResponse(call: Call, response: Response) {
//                    어떤 내용이던, 응답 자체는 잘 들어온 경우(그 내용은 성공/실패 일 수 있다)
//                    응답 : reponse 변수 > 응답의 본문(body) 만 가져온다.
                    val bodyString = response.body!!.string()   // toString() 아님!

                    Log.d("서버스트리",bodyString)

                }

            })

        }

    }
}