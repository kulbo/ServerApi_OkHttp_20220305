package kr.co.smartsoft.serverapi_okhttp_20220305

import androidx.appcompat.app.AppCompatActivity

// 다른 모둔 화면이 공통적으로 가질 기능 / 멤버변수
abstract class BaseActivity : AppCompatActivity() {
//        setupEvents / setValues 함수를 만들어두고 물려주자.
//        실제 함수를 구현해서 물려줘봐야
//        => 추사 메쏘드로 물려줘서 반드시 오버라이딩하게 만들자
    abstract fun setupEvents()
    abstract fun setValues()

}