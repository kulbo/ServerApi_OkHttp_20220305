package kr.co.smartsoft.serverapi_okhttp_20220305

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import kr.co.smartsoft.serverapi_okhttp_20220305.databinding.ActivityViewTopicDetailBinding
import kr.co.smartsoft.serverapi_okhttp_20220305.datas.TopicData
import kr.co.smartsoft.serverapi_okhttp_20220305.utils.ServerUtil
import org.json.JSONObject

class ViewTopicDetailActivity : BaseActivity() {

    lateinit var binding:ActivityViewTopicDetailBinding

    lateinit var mTopicData : TopicData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_topic_detail)
        mTopicData = intent.getSerializableExtra("topic") as TopicData
        setupEvents()
        setValues()
    }
    override fun setupEvents() {

//        btnVote1 클릭 => 첫 진영의 id값을 찾아서 거기에 투표
//        서버에 전달 => API 활용.
        binding.btnVote1.setOnClickListener {
//            서버의 투표 API 호출
//            투표 현황 새로고침()
        }
    }

    override fun setValues() {
        setTopicDataToUi()

        getTopicDetailFromServer()

    }

    fun setTopicDataToUi() {
        binding.txtTitle.text = mTopicData.title
        Glide.with(mContext).load(mTopicData.imageURL).into(binding.imgTopicBackground)

//        1번진여 제목, 2번진영 제목
        binding.txtSide1.text = mTopicData.sideList[0].title
        binding.txtSide2.text = mTopicData.sideList[1].title

//        1번 진영 득표수, 2번진영 득표수
        binding.txtVoteCount1.text = "${mTopicData.sideList[0].voteCount}표"
        binding.txtVoteCount2.text = "${mTopicData.sideList[1].voteCount}표"


    }
    fun getTopicDetailFromServer() {

        ServerUtil.getRequestTopicDetail(mContext, mTopicData.id, object :ServerUtil.JsonResponseHandler{
            override fun onResponse(jsonObj: JSONObject) {
                val dataObj = jsonObj.getJSONObject("data")
                val topicObj = dataObj.getJSONObject("topic")
//                토론 정보 JSONObject(topicObj) => TopicData() 형태로 변환(함수로 만들자)
                val topicData = TopicData.getTopicDataFromJson(topicObj)
//                변환된 객체를 mTopicData로 다시 대입. => UI 반영
                mTopicData = topicData

                runOnUiThread {
                    setTopicDataToUi()
                }
            }

        })
    }
}