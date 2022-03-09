package kr.co.smartsoft.serverapi_okhttp_20220305.datas

import org.json.JSONObject

class UserData {
    var id = 0
    var email = ""
    var nickname = ""

    companion object {
        fun getSideDataFromJson(jsonObj: JSONObject) : UserData {
            val userData = UserData()

            userData.id = jsonObj.getInt("id")
            userData.email = jsonObj.getString("email")
            userData.nickname = jsonObj.getString("nickname")

            return userData
        }
    }

}