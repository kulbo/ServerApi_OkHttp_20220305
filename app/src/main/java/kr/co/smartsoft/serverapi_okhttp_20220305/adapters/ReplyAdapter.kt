package kr.co.smartsoft.serverapi_okhttp_20220305.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import kr.co.smartsoft.serverapi_okhttp_20220305.R
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

                    }

                }
            )
        }
        return row
    }

}