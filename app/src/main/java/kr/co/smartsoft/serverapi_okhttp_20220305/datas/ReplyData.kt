package kr.co.smartsoft.serverapi_okhttp_20220305.datas

import org.json.JSONObject
import java.util.*

class ReplyData(
    var id : Int,
    var content : String,
) {
    var writer = UserData()

    var selectedSide = SideData()

//    작성 일시를 담아둘 변수
//    일시 데이터를 변경 => 내부의 숫자만 변경. 변수에 새 객체 대입 X. -> val로 써도 됨.
    val createdAt = Calendar.getInstance()

//    답글 / 좋아요/ 싫어요 갯수
    var reReplyCount = 0
    var likeCount = 0
    var hateCount = 0

//    내가 좋아요 /싫어요를 찍은 댓들인지
    var isMyLike = false
    var isMyHate = false


//    보조 생성자 추가 연습 : 파라미터 X
    constructor() : this(0, "내용없음")

    companion object {
        fun getReplyDataFromJson(jsonObj : JSONObject) : ReplyData {
            val replyData = ReplyData()

//            JSON 정보 > 멤버변수 채우기
            replyData.id = jsonObj.getInt("id")
            replyData.content = jsonObj.getString("content")
            replyData.writer = UserData.getSideDataFromJson(jsonObj.getJSONObject("user"))

            replyData.selectedSide = SideData.getSideDataFromJson(jsonObj.getJSONObject("selected_side"))

            replyData.reReplyCount = jsonObj.getInt("reply_count")
            replyData.likeCount = jsonObj.getInt("like_count")
            replyData.hateCount = jsonObj.getInt("dislike_count")

            replyData.isMyLike = jsonObj.getBoolean("my_like")
            replyData.isMyHate = jsonObj.getBoolean("my_dislike")
//          Calendar로 되어 있는 작성일시의 시간을 서버가 알려주는 뎃글 작성 일시로 맟춰저야함.
//            임시1) 2022-01-12
            replyData.createdAt.set(2022, 1, 12, 10,55,35)
//            임시2) 연도만 2021년으로 변경(항목)
            replyData.createdAt.set(Calendar.YEAR, 2021)

            return replyData
        }
    }
}