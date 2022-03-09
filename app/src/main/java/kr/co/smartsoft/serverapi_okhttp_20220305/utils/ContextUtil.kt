package kr.co.smartsoft.serverapi_okhttp_20220305.utils

import android.content.Context

class ContextUtil {

    companion object {

//        메모장 파일 이름처럼 SharedPreferences의 이름 설정.
        private val prefName = "OkHttpPracticePref"

//        저장할 데이터의 항목명도 변수로 만들어두자.
        val TOKEN = "TOKEN"

//        데이터를 저장 함수(setter) / 조회 함수(getter) 별개로 작성
//        TOKEN 항목에 저장 => token 항목 조회? 데이터 인식 X 대소문자까지 동일해야함.
//        오타를 줄이고 코딩을 편일하게 조치.

        fun setToken( context: Context, token:String ) {
//            메모장 파일을 열자.
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
//            입력 들어온 token 내용(TOKEN 항목에) 저장.
            pref.edit().putString(TOKEN, token).apply()
        }
    }
}