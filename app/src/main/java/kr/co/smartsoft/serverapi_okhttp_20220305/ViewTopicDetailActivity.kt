package kr.co.smartsoft.serverapi_okhttp_20220305

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import kr.co.smartsoft.serverapi_okhttp_20220305.adapters.ReplyAdapter
import kr.co.smartsoft.serverapi_okhttp_20220305.databinding.ActivityViewTopicDetailBinding
import kr.co.smartsoft.serverapi_okhttp_20220305.datas.ReplyData
import kr.co.smartsoft.serverapi_okhttp_20220305.datas.TopicData
import kr.co.smartsoft.serverapi_okhttp_20220305.utils.ServerUtil
import org.json.JSONObject

class ViewTopicDetailActivity : BaseActivity() {

    lateinit var binding:ActivityViewTopicDetailBinding

    lateinit var mTopicData : TopicData

    val mReplyList = ArrayList<ReplyData>()

    lateinit var mAdapter: ReplyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_topic_detail)
        mTopicData = intent.getSerializableExtra("topic") as TopicData
        setupEvents()
        setValues()
    }
    override fun setupEvents() {

        binding.btnPostReply.setOnClickListener {
//            투표를 하지 않은 상태라면, 뎃글 작성도 불가.
            if (mTopicData.mySelectedSide == null) {
                Toast.makeText(mContext, "의경을 개진할 진영을 선택하셔야 합니다.", Toast.LENGTH_SHORT).show()
//                클릭 이벤트 자체를 강제 종료 (Intent 실행을 막자)
                return@setOnClickListener   // return : 함수의 결과를 지정 => 함수 강제 종료.
            }
            val myIntent = Intent(mContext, EditReplyActivity::class.java)
            myIntent.putExtra("topic", mTopicData)
            startActivity(myIntent)
        }
//        btnVote1 클릭 => 첫 진영의 id값을 찾아서 거기에 투표
//        서버에 전달 => API 활용.
        binding.btnVote1.setOnClickListener {
//            서버의 투표 API 호출
            ServerUtil.postRequestVote(mContext, mTopicData.sideList[0].id, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObj: JSONObject) {

//                    토스트로 서버가 알려준 현재 상황(신규 투표 or 재투표 or 취소 등)
                    val message = jsonObj.getString("message")

                    runOnUiThread {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                    }

                    getTopicDetailFromServer()
                }

            })
//            투표 현황 새로고침()
        }

        binding.btnVote2.setOnClickListener {
            ServerUtil.postRequestVote(mContext, mTopicData.sideList[1].id, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObj: JSONObject) {

//                    토스트로 서버가 알려준 현재 상황(신규 투표 or 재투표 or 취소 등)
                    val message = jsonObj.getString("message")

                    runOnUiThread {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                    }

                    getTopicDetailFromServer()
                }

            })
        }
    }

    override fun setValues() {
//***        뎃글 표시의 마지막 부분
        mAdapter = ReplyAdapter(mContext, R.layout.reply_list_item, mReplyList)
        binding.replyListView.adapter = mAdapter
//***
        setTopicDataToUi()

//        onResume 에서 실행되기 때문에 막음.
//        getTopicDetailFromServer()

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

//        내가 선택한 진영이 있을 때 (투표 해놨을때)
//        이미 투표한 진영은 못누를게 막아두자. (enabled = false)
        if (mTopicData.mySelectedSide != null) {
//            첫번째 진영을 투표했는지
//            두번째 진영을 투표했는지
            if (mTopicData.mySelectedSide!!.id == mTopicData.sideList[0].id) {
//                첫 진영에 투표 한 경우
                binding.btnVote1.text = "투표 취소"
                binding.btnVote2.text = "다시 투표"
            }
            else {
                binding.btnVote1.text = "다시 투표"
                binding.btnVote2.text = "투표 취소"
            }
        }
        else {
            binding.btnVote1.text = "투표 하기"
            binding.btnVote2.text = "투표 하기"
        }
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

//              mReplyList에 댓들목록이 추가된다.
//
                mReplyList.clear()

//***            뎃글 표시 데이터 작업 마지막부분
//                topicObj 내부에는 replies 라는 뎃글 목록 JSONArray 도 들어있아.
                val repliesArr = topicObj.getJSONArray("replies")

                for(i in 0 until repliesArr.length()) {
                    val replyObj = repliesArr.getJSONObject(i)

                    mReplyList.add(ReplyData.getReplyDataFromJson(replyObj))

                }
//                서버의 동작으로 어댑터 세팅보다 늦게 끝날수 있다.(notifyDataSetChanged)
                runOnUiThread {
                    mAdapter.notifyDataSetChanged()
                }
//***
            }

        })
    }
//    이 화면에 들어올때 마다, 댓들 목록 새로고침
    override fun onResume() {
        super.onResume()

        getTopicDetailFromServer()
    }
}