package kr.co.smartsoft.serverapi_okhttp_20220305.utils

import android.content.Context
import android.util.Log
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException

class ServerUtil {

//    서버유틸로 돌아온 응답을 => 액티비티에서 처리하도록 일처리 넘긱기
    interface JsonResponseHandler {
        fun onResponse( jsonObject: JSONObject )
    }
//    서버에 Request를 날리는 역할
//    코틀린에서 static 에 해당하는 개념 conpanion object{  } 에 만들자
    companion object {
//    서버 컴퓨터 주소만 변수로 저장(관리 일원화)
        private val BASE_URL = "http://54.180.52.26"
        private val EMAIL = "email"
        private val PASSWORD = "password"
        private val NICKNAME = "nick_name"
//      로그인 기는 호출 함수
//      handler : 이 함수를 쓰는 화면에서 JSON 분석을 어떻게 /UI에서 어떻게
//          - 처리 방안을 대입하려면 널 데입을 허용

        fun postRequestLogin(id:String, pw:String, handler: JsonResponseHandler?) {
//            Request 제작 -> 실제 호출 -> 서버의 응답을 화면에 전달
//            제작 1) 어느 주소로(url)로 접근할 지? 서버 주소 + 기능 주소
            val urlString = "${BASE_URL}/user"
//            제작 2 파라미터 담아주기 => 어떤 이름표/어느 공간에
            val formData = FormBody.Builder()
                .add(EMAIL, id)
                .add(PASSWORD, pw)
                .build()
//            제작 3) 모든 Request 정보를 종합한 객체 생성.(어느 주소로 + 어느 메쏘드로 + 어떤 파라미터를)
            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .build()
//          종합한 Request로 실제 호출을 해줘야
//          실제 호출 : 앱이 클라이언트로써 동작 > OKHttpClient 클래스
            val client = OkHttpClient()
//            OkHttpClient 객체를 이용 > 서버에 로그인 기능 실제 호출
//            => 서버에 다녀와서 할 일을 등록 : enqueue(Callback)
            client.newCall(request).enqueue( object : Callback{

                override fun onFailure(call: Call, e: IOException) {
//                실패 : 서버 연결 자체를 실패. 응답이 오지 않았다.
//                ex. 인터넷 끊김 등

                }

                override fun onResponse(call: Call, response: Response) {
//                    어떤 내용이던, 응답 자체는 잘 들어온 경우(그 내용은 성공/실패 일 수 있다)
//                    응답 : reponse 변수 > 응답의 본문(body) 만 가져온다.
                    val bodyString = response.body!!.string()   // toString() 아님!
//                    응답의 본문을 String으로 변환하면 JSON Encoding 적용된 상태.
//                    JSONObject 객체로 응답본문 String을 변환해주면 한글이 복구됨.
//                    => UI에서도 JSONObject를 이용해서 데이터 추출/실제 활용.
                    val jsonObj = JSONObject(bodyString)
                    Log.d("서버응답",jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }

            })

        }

        fun putRequestSignUp(email:String, pw: String, nickname:String, handler: JsonResponseHandler?) {
            val urlString = "${BASE_URL}/user"
            val formData = FormBody.Builder()
                .add(EMAIL, email)
                .add(PASSWORD, pw)
                .add(NICKNAME, nickname)
                .build()
            val request = Request.Builder()
                .url(urlString)
                .put(formData)
                .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    Log.d("서버응답", jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }

            })
        }

//        이메일 or 닉네임 중복 검사 함수
        fun getRequestDuplicatedCheck(type :String, inputValue: String, handler:JsonResponseHandler?) {
//            어느 주소로 가야하는가? + 어떤 파라미터를 첨부하는가? 도 주소에 같이 포함.
//           => 라이브러리의 도움을 받자. HttpUrl 클래스(OkHttp 소속)
            val urlBuilder = "${BASE_URL}/user_check".toHttpUrlOrNull()!!.newBuilder()
                .addEncodedQueryParameter("type", type)
                .addEncodedQueryParameter("value", inputValue)
                .build()

            val urlString = urlBuilder.toString()

//            2) 요청한 정고 정리 > Request 생성
            val request = Request.Builder()
                .url(urlString)
                .get()
                .build()
//             3) request 완성
            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString  = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    Log.d("서버응답", jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }

            }
            )
        }

//      연습 : 내 정보 불러오기 (/user_info - GET)
//      토큰은 ContextUtil 클래스에서 getToken 함수로 꺼내올 수 있다.
//      토큰값
//      메모장에 접근할 수 있게, Context 변수 하나를 미리 받아두자.

        fun getRequestMyInfo(context:Context,  handler: JsonResponseHandler? ) {
            val urlBuilder = "${BASE_URL}/user_info".toHttpUrlOrNull()!!.newBuilder()
                .build() // 쿼리파아미터를 담을게 없다.

            val urlString = urlBuilder.toString()

            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getToken(context))
                .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object :Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val jsonObj = JSONObject(response.body!!.string() )
                    Log.d("서버응답", jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }

            })
        }

        fun getRequestMainInfo(context:Context,  handler: JsonResponseHandler? ) {
            val urlBuilder = "${BASE_URL}/v2/main_info".toHttpUrlOrNull()!!.newBuilder()
                .build() // 쿼리파라미터를 담을게 없다.

            val urlString = urlBuilder.toString()

            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getToken(context))
                .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object :Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val jsonObj = JSONObject(response.body!!.string() )
                    Log.d("서버응답", jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }

            })
        }

        fun getRequestTopicDetail(context:Context,topicId : Int,  handler: JsonResponseHandler? ) {
            val urlBuilder = "${BASE_URL}/topic/${topicId}".toHttpUrlOrNull()!!.newBuilder()
                .addEncodedQueryParameter("order_type", "NEW")
                .build() // 쿼리파라미터를 담을게 없다.

            val urlString = urlBuilder.toString()

            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getToken(context))
                .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object :Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val jsonObj = JSONObject(response.body!!.string() )
                    Log.d("서버응답", jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }

            })
        }


        fun postRequestVote(context:Context, sideId:Int, handler: JsonResponseHandler?) {
    //            Request 제작 -> 실제 호출 -> 서버의 응답을 화면에 전달
    //            제작 1) 어느 주소로(url)로 접근할 지? 서버 주소 + 기능 주소
            val urlString = "${BASE_URL}/topic_vote"
    //            제작 2 파라미터 담아주기 => 어떤 이름표/어느 공간에
            val formData = FormBody.Builder()
                .add("side_id", sideId.toString())
                .build()
    //            제작 3) 모든 Request 정보를 종합한 객체 생성.(어느 주소로 + 어느 메쏘드로 + 어떤 파라미터를)
            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http-Token", ContextUtil.getToken(context))
                .build()
            val client = OkHttpClient()
            client.newCall(request).enqueue( object : Callback{

                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()   // toString() 아님!
                    val jsonObj = JSONObject(bodyString)
                    Log.d("서버응답",jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }

            })

        }

        fun postRequestTopicReply(context:Context, topicId:Int, content:String, handler: JsonResponseHandler?) {
            //            Request 제작 -> 실제 호출 -> 서버의 응답을 화면에 전달
            //            제작 1) 어느 주소로(url)로 접근할 지? 서버 주소 + 기능 주소
            val urlString = "${BASE_URL}/topic_reply"
            //            제작 2 파라미터 담아주기 => 어떤 이름표/어느 공간에
            val formData = FormBody.Builder()
                .add("topic_id", topicId.toString())
                .add("content", content)
                .build()
            //            제작 3) 모든 Request 정보를 종합한 객체 생성.(어느 주소로 + 어느 메쏘드로 + 어떤 파라미터를)
            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http-Token", ContextUtil.getToken(context))
                .build()
            val client = OkHttpClient()
            client.newCall(request).enqueue( object : Callback{

                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()   // toString() 아님!
                    val jsonObj = JSONObject(bodyString)
                    Log.d("서버응답",jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }

            })

        }


        fun postRequestReplyLike(context: Context, replyId:Int, isLike:Boolean, handler: JsonResponseHandler?) {
            val urlString = "${BASE_URL}/topic_reply_like"
            val formData = FormBody.Builder()
                .add("side_id", replyId.toString())
                .add("is_like", isLike.toString())
                .build()
            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http-Token", ContextUtil.getToken(context))
                .build()
            val client = OkHttpClient()
            client.newCall(request).enqueue( object : Callback{

                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()   // toString() 아님!
                    val jsonObj = JSONObject(bodyString)
                    Log.d("서버응답",jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }

            })

        }

    }
}