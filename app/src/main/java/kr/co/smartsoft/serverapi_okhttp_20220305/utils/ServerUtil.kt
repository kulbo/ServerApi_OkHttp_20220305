package kr.co.smartsoft.serverapi_okhttp_20220305.utils

import okhttp3.FormBody
import okhttp3.Request

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

        }

    }
}