package com.example.dictionary

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.json.JSONArray
import java.io.*
import java.net.HttpURLConnection
import java.net.URL


private class MyTask : AsyncTask<String, Void, String>() {
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
        } finally {
            urlConnection.disconnect()
        }
        return ans
    }


    @Deprecated("Deprecated in Java")
    override fun onPostExecute(result: String) {
        super.onPostExecute(result)
        if (result.isNotEmpty()) {
            val jsonArray = JSONArray(result)
            val jsonObject = jsonArray.getJSONObject(1) // assuming there's at least one element in the array
            val definition = jsonObject.getString("partOfSpeech") // assuming "definition" is the attribute you want to extract
            Log.i("Status", "Word found and the partofSPeech is $definition")
        } else {
            Log.i("Status", "Word NOT found")
        }
    }

}
class MainActivity : AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var submit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.inputText)
        submit = findViewById(R.id.button)
        var word = ""

        submit.setOnClickListener {
            word = editText.text.toString()
            MyTask().execute(word)
            Toast.makeText(this, "Input: $word", Toast.LENGTH_SHORT).show()
        }

    }
}


