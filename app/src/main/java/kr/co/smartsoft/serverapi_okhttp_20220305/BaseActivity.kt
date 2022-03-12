package kr.co.smartsoft.serverapi_okhttp_20220305

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

abstract class BaseActivity : AppCompatActivity() {
//    Context 계열의 파라미터에 대입할때, 보통 this 로 대입
//      인터페이스가 엮이기 시작하면 this@어느화면 추가로 고려
    val mContext = this
//        setupEvents / setValues 함수를 만들어두고 물려주자.
//        실제 함수를 구현해서 물려줘봐야
//        => 추사 메쏘드로 물려줘서 반드시 오버라이딩하게 만들자
    abstract fun setupEvents()
    abstract fun setValues()

//    실제 구현 내용을 같이 물려주는 함수
//    액션바 설저 기능
    fun setCustomActionBar(){
        //        임시 테스트 > 액션바 커스텀 모드
        val defaultActionBar = supportActionBar!!
        defaultActionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM

        defaultActionBar.setCustomView(R.layout.my_custom_action_bar)

        val toolbar = defaultActionBar.customView.parent as Toolbar
        toolbar.setContentInsetsAbsolute(0,0)

    }
}