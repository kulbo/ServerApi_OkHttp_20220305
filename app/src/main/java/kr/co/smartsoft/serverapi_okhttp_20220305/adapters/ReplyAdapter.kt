package kr.co.smartsoft.serverapi_okhttp_20220305.adapters

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
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
        val txtCreatedAt = row.findViewById<TextView>(R.id.txtCreatedAt)

        val txtReReplyCount = row.findViewById<TextView>(R.id.txtReReplyCount)
        val txtLikeCount = row.findViewById<TextView>(R.id.txtLikeCount)
        val txtHateCount = row.findViewById<TextView>(R.id.txtHateCount)

        txtReplyContent.text = data.content
        txtWriteNickname.text = data.writer.nickname
        txtSelectedSide.text = "[${data.selectedSide.title}]"

        // 임시로 = 작성일만 "2022-03-10" 형태로 표현. => 연 / 월 / 일 데이터로 가공
//        txtCreatedAt.text = "${data.createdAt.get(Calendar.YEAR)}-${data.createdAt.get(Calendar.MONTH) + 1}-${data.createdAt.get(Calendar.DAY_OF_MONTH)}"
//
//        연습
//        양식 1) 2022년 3월 5일
//        양식 2) 220304
//        양식 3) 3월 5일 오전 2시 5분

//        val sdf = java.text.SimpleDateFormat("yy.MM.dd")
//        val sdf = java.text.SimpleDateFormat("yyyy년 MM월 dd일")
//        val sdf = java.text.SimpleDateFormat("M월 d일 a h시 m분")
        val sdf = java.text.SimpleDateFormat("yy년 M/d(E) HH:mm")

//        createdAt : Calendar/ format의 파라미터:Date => Calendar의 내용물인 time변수가 Date.
        txtCreatedAt.text = sdf.format(data.createdAt.time)

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

        txtHateCount.setOnClickListener {

//            서버에 이 뎃글에 좋아요 알림.
            ServerUtil.postRequestReplyLikeOrHate(
                mContext,
                data.id,
                false,
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
            txtLikeCount.setBackgroundResource(R.drawable.naver_red_border_box)
        }
        else {
            txtLikeCount.setTextColor(ContextCompat.getColor(mContext, R.color.deep_dark_gray))
            txtLikeCount.setBackgroundResource(R.drawable.dark_gray_border_box)
        }

        if (data.isMyHate) {
            txtHateCount.setTextColor(ContextCompat.getColor(mContext, R.color.naver_red))
            txtHateCount.setBackgroundResource(R.drawable.naver_red_border_box)
        }
        else {
            txtHateCount.setTextColor(ContextCompat.getColor(mContext, R.color.deep_dark_gray))
            txtHateCount.setBackgroundResource(R.drawable.dark_gray_border_box)
        }

        return row
    }

}