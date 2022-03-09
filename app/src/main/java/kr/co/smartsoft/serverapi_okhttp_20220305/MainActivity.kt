package kr.co.smartsoft.serverapi_okhttp_20220305

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import kr.co.smartsoft.serverapi_okhttp_20220305.databinding.ActivityMainBinding
import kr.co.smartsoft.serverapi_okhttp_20220305.datas.TopicData
import kr.co.smartsoft.serverapi_okhttp_20220305.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {
    lateinit var binding:ActivityMainBinding

//    실제로 서버가 내려주는 주제 목록을 담을 그룻
    val mTopicList = ArrayList<TopicData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
//        메인 화면 정보 가져오기 => API 호출 / 응답 처리
        getTopicListFromServer()
    }

    fun getTopicListFromServer() {

        ServerUtil.getRequestMainInfo(mContext, object :ServerUtil.JsonResponseHandler{
            override fun onResponse(jsonObj: JSONObject) {
//                서버가 주는 토론 주제 목록 파싱 => nTopicList
                val dataObj = jsonObj.getJSONObject("data")
                val topicsArr = dataObj.getJSONArray("topics")

//                topicsArr 내부를 하나씩 추출 (JSonobjct{}) => TopicData

//                JSONArray 는 for-each 문법 지원 X. (차후 ArrayList의 for-each 활용 예정)
//                for (int i=0; i<배열.length; i++)
                for (i in 0 until topicsArr.length()) {
//                    [] => {}, {}, {}, ... 순서에 맞는 {}를 변수에 담자.
                    val topicObj = topicsArr.getJSONObject(i)

                    Log.d("받아낸 주제", topicObj.toString())

//                    TopicData 변수 생성 => 멤버변수에 topicObj가 들고 있는 값들을 대입.
                    val topicData = TopicData()
                    topicData.id = topicObj.getInt("id")
                    topicData.title = topicObj.getString("title")
                    topicData.imageURL = topicObj.getString("img_utl")
                    topicData.replyCount = topicObj.getInt("reply_count")

//                    완성된 TopicData 객체를 목록에 추가.
                    mTopicList.add(topicData)

                }
            }

        })
    }

}