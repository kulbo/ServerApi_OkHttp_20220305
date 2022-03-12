package kr.co.smartsoft.serverapi_okhttp_20220305

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

abstract class BaseActivity : AppCompatActivity() {
//    Context 계열의 파라미터에 대입할때, 보통 this 로 대입
//      인터페이스가 엮이기 시작하면 this@어느화면 추가로 고려
    lateinit var mContext:Context

//    액션바의 UI 변수를 멤버변수로 > 상속 가능
//    => 변수에 대입 : 커스텀 액션바 세팅 뒤에
    lateinit var btnBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this

        supportActionBar?.let{
//             supportActionBar 변수가 null이 아닐때만 실행할 코드
            setCustomActionBar()
        }
    }
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

//        xml에 그려둔 UI 가져오기
        btnBack = defaultActionBar.customView.findViewById(R.id.btnBack)

//      누르면 화면 종료 : 모든 화면 공통
        btnBack.setOnClickListener {
            finish()
        }

    }
}