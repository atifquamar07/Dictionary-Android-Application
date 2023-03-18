package com.example.dictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.dictionary.fragments.Audio
import com.example.dictionary.fragments.ListOfMeanings
import org.json.JSONArray
import java.util.*

class Result : AppCompatActivity() {

    private lateinit var wordTxt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val jsonString = intent.getStringExtra("jsonArray")
        val jsonArray = JSONArray(jsonString)
        val jsonObject = jsonArray.getJSONObject(0)
        val getName = jsonObject.getString("word")
        Log.i("Message in result", "Word found and the word is $getName")

        wordTxt = findViewById(R.id.idWord)
        wordTxt.text = getName.capitalize(Locale.ROOT)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val myFragment = Audio.newInstance(jsonArray)
        fragmentTransaction.add(R.id.fragmentContainerView, myFragment)
        fragmentTransaction.commit()



    }
}