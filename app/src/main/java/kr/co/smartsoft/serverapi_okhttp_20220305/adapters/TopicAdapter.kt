package kr.co.smartsoft.serverapi_okhttp_20220305.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import kr.co.smartsoft.serverapi_okhttp_20220305.R
import kr.co.smartsoft.serverapi_okhttp_20220305.datas.TopicData

class TopicAdapter(
    val mContext: Context,
    resId: Int,
    val mList: List<TopicData>
) : ArrayAdapter<TopicData>(mContext, resId, mList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView
        if (tempRow == null){
            tempRow = LayoutInflater.from(mContext).inflate(R.layout.topic_list_item, null)
        }

        val row = tempRow!!

        val data = mList[position]

        val txtTitle = row.findViewById<TextView>(R.id.txtTitle)

        txtTitle.text = data.title

        return row
    }
}