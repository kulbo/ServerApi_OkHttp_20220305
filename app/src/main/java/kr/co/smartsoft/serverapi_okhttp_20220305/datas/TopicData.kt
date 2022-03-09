package kr.co.smartsoft.serverapi_okhttp_20220305.datas

import org.json.JSONObject
import java.io.Serializable

class TopicData: Serializable {
    var id = 0          //id는 Int라고 명시
    var title = ""      //title은 String이라고 명시
    var imageURL = ""   //서버 : img_url, 앱 : imageURL 변수명 다른경우
    var replyCount = 0

//      주제 정보르 담고 있는 JSONObject 가 들어오면 > TopicData
    companion object {
//        주제 정보를 담고 있는 JSONObject가 들어오면 > TopicData
        fun getTopicDataFromJson(jsonObj : JSONObject) : TopicData {
//            기본 내용의 TopicData 생성
            val topicData = TopicData()
            topicData.id = jsonObj.getInt("id")
            topicData.title = jsonObj.getString("img_url")
            topicData.replyCount = jsonObj.getInt("reply_count")

//            jsonObj 에서 데이터 추출
            return topicData
        }
    }
}