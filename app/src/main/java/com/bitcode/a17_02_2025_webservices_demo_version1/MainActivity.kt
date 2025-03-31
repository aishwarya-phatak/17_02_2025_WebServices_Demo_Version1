package com.bitcode.a17_02_2025_webservices_demo_version1

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CoroutineScope(Dispatchers.IO).launch {
            var url = URL("https://reqres.in/api/users?page=2")
            var httpsURLConnection = url.openConnection() as HttpsURLConnection

            httpsURLConnection.connect()

            var inStream = httpsURLConnection.inputStream

            var responseBuffer = StringBuffer()
            var byteArray = ByteArray(1024 * 2)
            var count = 0

            count = inStream.read(byteArray)
            while (count != -1) {
                responseBuffer.append(String(byteArray, 0, count))
                count = inStream.read(byteArray)
            }

            inStream.close()

            Log.e("tag", responseBuffer.toString())

            var jsonResponse = JSONObject(responseBuffer.toString())

            var page = jsonResponse.getInt("page")
            var perPage = jsonResponse.get("per_page")
            var total = jsonResponse.getInt("total")
            var totalPages = jsonResponse.getInt("total_pages")

            Log.e("tag", "page -- $page  perPage -- $perPage  total -- $total")

            var usersArray = ArrayList<User>()

            var jsonArrayOfUsers = jsonResponse.getJSONArray("data")

            for (i in 0..jsonArrayOfUsers.length() - 1){
                var userObject = jsonArrayOfUsers.getJSONObject(i)
                var userId = userObject.getInt("id")
                var userFirstName = userObject.getString("first_name")
                var userLastName = userObject.getString("last_name")
                var userEmail = userObject.getString("email")
                var userAvatar = userObject.getString("avatar")

                usersArray.add(
                    User(userId,
                                    userEmail,
                                    userFirstName,
                                    userLastName,
                                    userAvatar)
                )
            }

            for(eachUSer in usersArray) {
                Log.e("tag", "${eachUSer.id} -- ${eachUSer.first_name} -- ${eachUSer.email}")
            }
        }
    }
}