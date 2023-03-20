package com.example.dictionary

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.json.JSONArray
import java.io.*
import java.net.HttpURLConnection
import java.net.URL


interface FetchJSONCallback {
    fun onFetchCompleted()
}

class MainActivity : AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var submit: Button
    var jsonArray: JSONArray = JSONArray()

    private inner class FetchJSON(private val callback: FetchJSONCallback) : AsyncTask<String, Void, String>() {
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: String): String {
            Log.i("Size of params", "${params.size}")
            val urlStr = "https://api.dictionaryapi.dev/api/v2/entries/en/${params[0]}"
            val url = URL(urlStr)
            val urlConnection = url.openConnection() as HttpURLConnection
            var ans = ""
            try {
                val inputStream = BufferedInputStream(urlConnection.inputStream)
                val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                val stringBuilder = StringBuilder()
                bufferedReader.forEachLine { stringBuilder.append(it) }
                ans = stringBuilder.toString()
            } catch (e: FileNotFoundException) {
                Log.e("API Error", "Word not found")
                return "Word not found"
            } finally {
                urlConnection.disconnect()
            }
            return ans
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: String) {
            if (result == "Word not found") {
                Toast.makeText(applicationContext, "Word not found", Toast.LENGTH_SHORT).show()
                return
            }
            super.onPostExecute(result)
            if (result.isNotEmpty()) {
                jsonArray = JSONArray(result)
                val jsonObject = jsonArray.getJSONObject(0)
                val getUrl = jsonObject.getString("sourceUrls")
                Log.i("Status", "Word found and the sourceUrls is $getUrl")
            } else {
                Log.i("Status", "Word NOT found")
            }
            callback.onFetchCompleted()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.inputText)
        submit = findViewById(R.id.button)
        var word = ""

        submit.setOnClickListener {
            word = editText.text.toString()
            if(word == ""){
                Toast.makeText(applicationContext, "Word can't be blank", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            FetchJSON(object : FetchJSONCallback {
                override fun onFetchCompleted() {
                    val jsonObject = jsonArray.getJSONObject(0)
                    val getUrl = jsonObject.getString("sourceUrls")
                    Log.i("In main activity", "Word found and the sourceUrls is $getUrl")
                    val jsonString = jsonArray.toString()
                    val intent = Intent(applicationContext, Result::class.java)
                    intent.putExtra("jsonArray", jsonString)
                    Toast.makeText(applicationContext, "Input: $word", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                }
            }).execute(word)



        }

    }
}


