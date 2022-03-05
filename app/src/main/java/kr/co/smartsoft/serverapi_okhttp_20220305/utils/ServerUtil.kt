package kr.co.smartsoft.serverapi_okhttp_20220305.utils

class ServerUtil {

//    서버에 Request를 날리는 역할
//    코틀린에서 static 에 해당하는 개념 conpanion object{  } 에 만들자
    companion object {
//    서버 컴퓨터 주소만 변수로 저장(관리 일원화)
        private val BASE_URL = "http://54.180.52.26"
//      로그인 기는 호출 함수
        fun postRequestLogin(id:String, pw:String) {
//            Request 제작 -> 실제 호출 -> 서버의 응답을 화면에 전달
            val urlString = "${BASE_URL}/user"
        }

    }
}