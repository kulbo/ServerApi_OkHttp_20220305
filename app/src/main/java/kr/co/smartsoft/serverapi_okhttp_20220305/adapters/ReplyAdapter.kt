package kr.co.smartsoft.serverapi_okhttp_20220305.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import kr.co.smartsoft.serverapi_okhttp_20220305.R
import kr.co.smartsoft.serverapi_okhttp_20220305.datas.ReplyData
import org.w3c.dom.Text

class ReplyAdapter(
    val mContext : Context,
    resId : Int,
    val mList:List<ReplyData>
) : ArrayAdapter<ReplyData>(mContext, resId, mList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tempRow = convertView
        if (tempRow == null) {
            tempRow = LayoutInflater.from(mContext).inflate(R.layout.replay_list_item, null)
        }
        val row = tempRow!!

        val data = mList[position]

        val txtSelectedSide = row.findViewById<TextView>(R.id.txtSelectedSide)
        val txtWriteNickname = row.findViewById<TextView>(R.id.txtWriterNickname)
        val txtReplyContent = row.findViewById<TextView>(R.id.txtReplyContent)

        txtReplyContent.text = data.content
        txtWriteNickname.text = data.writer.nickname
        txtSelectedSide.text = "[${data.selectedSide.title}]"

        return row
    }

}