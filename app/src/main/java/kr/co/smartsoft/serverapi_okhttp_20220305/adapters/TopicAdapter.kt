package kr.co.smartsoft.serverapi_okhttp_20220305.adapters

import android.content.Context
import android.widget.ArrayAdapter
import kr.co.smartsoft.serverapi_okhttp_20220305.datas.TopicData

class TopicAdapter(
    val mContext: Context,
    resId: Int,
    val mList: List<TopicData>
) : ArrayAdapter<TopicData>(mContext, resId, mList) {

}