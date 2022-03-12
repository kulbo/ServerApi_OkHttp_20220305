package kr.co.smartsoft.serverapi_okhttp_20220305.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import kr.co.smartsoft.serverapi_okhttp_20220305.R
import kr.co.smartsoft.serverapi_okhttp_20220305.ViewTopicDetailActivity
import kr.co.smartsoft.serverapi_okhttp_20220305.datas.ReplyData
import kr.co.smartsoft.serverapi_okhttp_20220305.utils.ServerUtil
import org.json.JSONObject

class ReplyAdapter(
    val mContext : Context,
    resId : Int,
    val mList:List<ReplyData>
) : ArrayAdapter<ReplyData>(mContext, resId, mList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tempRow = convertView
        if (tempRow == null) {
            tempRow = LayoutInflater.from(mContext).inflate(R.layout.reply_list_item, null)
        }
        val row = tempRow!!

        val data = mList[position]

        val txtSelectedSide = row.findViewById<TextView>(R.id.txtSelectedSide)
        val txtWriteNickname = row.findViewById<TextView>(R.id.txtWriterNickname)
        val txtReplyContent = row.findViewById<TextView>(R.id.txtReplyContent)

        val txtReReplyCount = row.findViewById<TextView>(R.id.txtReReplyCount)
        val txtLikeCount = row.findViewById<TextView>(R.id.txtLikeCount)
        val txtHateCount = row.findViewById<TextView>(R.id.txtHateCount)

        txtReplyContent.text = data.content
        txtWriteNickname.text = data.writer.nickname
        txtSelectedSide.text = "[${data.selectedSide.title}]"

        txtReReplyCount.text = "답금 ${data.reReplyCount}"
        txtLikeCount.text = "좋아요 ${data.likeCount}"
        txtHateCount.text = "실어요 ${data.hateCount}"

        txtLikeCount.setOnClickListener {

//            서버에 이 뎃글에 좋아요 알림.
            ServerUtil.postRequestReplyLikeOrHate(
                mContext,
                data.id,
                true,
                object :ServerUtil.JsonResponseHandler {
                    override fun onResponse(jsonObject: JSONObject) {
//                        무조건 댓글 목록을 새로 고침
//                        Adapter 에서 액티비티의 기능 실행
//                        어댑터 객체화시 mContext 변수에 어느 화면에서 사용하는지 대입.
//                        mContext : Context 타입. 대입 객체 : ViewTopic 액티비티 객체 => 다형성
//                        부모 형태의 변숭에 담긴 자식 객체는 캐스팅을 통해서 원상 복구 사능.

                        (mContext as ViewTopicDetailActivity).getTopicDetailFromServer()
                    }

                }
            )
        }

//      [도전과제]  싫어요가 눌려도 마찬가지 처리 => 싫어요 API 호출(기존함수 활용) + 토론 상세화면 댓들 목록 새로고침

//        좋아요가 눌렸는지, 아닌지, 글씨 색상 변경.
        if (data.isMyLike) {
            txtLikeCount.setTextColor(ContextCompat.getColor(mContext, R.color.naver_red))
        }
        else {
            txtLikeCount.setTextColor(ContextCompat.getColor(mContext, R.color.deep_dark_gray))
        }
        return row
    }

}