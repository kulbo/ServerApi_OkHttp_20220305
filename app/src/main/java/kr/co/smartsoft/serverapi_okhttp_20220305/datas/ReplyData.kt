package kr.co.smartsoft.serverapi_okhttp_20220305.datas

import org.json.JSONObject

class ReplyData(
    var id : Int,
    var content : String,
) {
    var writer = UserData()

    var selectedSide = SideData()

//    보조 생성자 추가 연습 : 파라미터 X
    constructor() : this(0, "내용없음")

    companion object {
        fun getReplyDataFromJson(jsonObj : JSONObject) : ReplyData {
            val replyData = ReplyData()

//            JSON 정보 > 멤버변수 채우기
            replyData.id = jsonObj.getInt("id")
            replyData.content = jsonObj.getString("content")
            replyData.writer = UserData.getSideDataFromJson(jsonObj.getJSONObject("user"))

//            replyData.selectedSide = SideData.getSideDataFromJson(jsonObj.getJSONObject("selected_side"))

            return replyData
        }
    }
}