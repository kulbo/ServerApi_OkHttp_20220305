package kr.co.smartsoft.serverapi_okhttp_20220305

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import kr.co.smartsoft.serverapi_okhttp_20220305.databinding.ActivityEditReplyBinding
import kr.co.smartsoft.serverapi_okhttp_20220305.datas.TopicData
import kr.co.smartsoft.serverapi_okhttp_20220305.utils.ServerUtil
import org.json.JSONObject

class EditReplyActivity : BaseActivity() {

    lateinit var binding : ActivityEditReplyBinding

//    어느 토론 주제에서 온건지 무조건 선택한 진영도 있다.
    lateinit var mTopicData : TopicData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_reply)
        mTopicData = intent.getSerializableExtra("topic") as TopicData
        setupEvents()
        setValues()
    }
    override fun setupEvents() {
        binding.btnPostReply.setOnClickListener {
            val inputContent = binding.edtReplyContent.text.toString()

            if(inputContent.length < 5) {
                Toast.makeText(mContext, "5자 이상으로 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            ServerUtil.postRequestTopicReply(
                mContext,
                mTopicData.id,
                inputContent,
                object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(jsonObj: JSONObject) {
                        val code = jsonObj.getInt("code")

                        runOnUiThread {
                            if (code == 200) {
                                Toast.makeText(mContext, "댓글 등록에 성공했습니다.", Toast.LENGTH_SHORT)
                                    .show()
                                finish()
                            }
                            else {
                                val message = jsonObj.getString("messge")
                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                }
            )
        }

    }

    override fun setValues() {
        binding.txtTopicTitle.text = mTopicData.title
        binding.txtSideTitle.text = mTopicData.mySelectedSide!!.title
    }
}