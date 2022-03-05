package kr.co.smartsoft.serverapi_okhttp_20220305

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
//    Context 계열의 파라미터에 대입할때, 보통 this 로 대입
//      인터페이스가 엮이기 시작하면 this@어느화면 추가로 고려
    val mContext = this
//        setupEvents / setValues 함수를 만들어두고 물려주자.
//        실제 함수를 구현해서 물려줘봐야
//        => 추사 메쏘드로 물려줘서 반드시 오버라이딩하게 만들자
    abstract fun setupEvents()
    abstract fun setValues()

}