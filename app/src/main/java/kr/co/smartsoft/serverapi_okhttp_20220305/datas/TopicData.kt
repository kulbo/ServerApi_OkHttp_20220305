package kr.co.smartsoft.serverapi_okhttp_20220305.datas

import org.json.JSONObject
import java.io.Serializable

class TopicData: Serializable {
    var id = 0          //id는 Int라고 명시
    var title = ""      //title은 String이라고 명시
    var imageURL = ""   //서버 : img_url, 앱 : imageURL 변수명 다른경우
    var replyCount = 0

//    하나의 토론주제 : 여러개의 (목록) 선택진영
    val sideList = ArrayList<SideData>()
//      주제 정보르 담고 있는 JSONObject 가 들어오면 > TopicData
    var mySelectedSide : SideData? = null

    companion object {
//        주제 정보를 담고 있는 JSONObject가 들어오면 > TopicData
        fun getTopicDataFromJson(jsonObj : JSONObject) : TopicData {
//            기본 내용의 TopicData 생성
            val topicData = TopicData()
            topicData.id = jsonObj.getInt("id")
            topicData.title = jsonObj.getString("title")
            topicData.imageURL = jsonObj.getString("img_url")
            topicData.replyCount = jsonObj.getInt("reply_count")

//          sides라는 JSONArray가 들어있음.
//          => topicData의 하위 정보로 선택진영 목록으로 저장
            val sidesArr = jsonObj.getJSONArray("sides")
            for (i in 0 until sidesArr.length()) {
//                선택 진영 정보를 들고 있는 JSONObject 추출
                val sideObj = sidesArr.getJSONObject(i)
//                sideObj
                val sideData = SideData.getSideDataFromJson(sideObj)
//                topicData변수의 하위 목록으로 등록
                topicData.sideList.add(sideData)
            }

//          투표해준 진영이 있다면 선택진영데이터도 파싱
//            진영이 업다면
            if ( !jsonObj.isNull("my_side") ) {
//              null이 아닐때면 파싱.
                val mySideObj = jsonObj.getJSONObject("my_side")
//                선택 진영 JSON => mySelectedSide 변수 (SideData)
                topicData.mySelectedSide = SideData.getSideDataFromJson(mySideObj)
            }
//            jsonObj 에서 데이터 추출
            return topicData
        }
    }
}