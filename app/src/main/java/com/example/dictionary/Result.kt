package com.example.dictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.dictionary.fragments.Audio
import com.example.dictionary.fragments.ListOfMeanings
import org.json.JSONArray

class Result : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("Message in result", "Just before setContentView")
        setContentView(R.layout.activity_result)
        Log.i("Message in result", "Just after setContentView")
        val jsonString = intent.getStringExtra("jsonArray")
        if (jsonString != null) {
            Log.i("Length of string in result.kt", "${jsonString.length}")
        }
        val jsonArray = JSONArray(jsonString)
        val jsonObject = jsonArray.getJSONObject(0)
        val getUrl = jsonObject.getString("sourceUrls")
        Log.i("Message in result", "Word found and the sourceUrls is $getUrl")

//        val listOfMeaningsInstance = ListOfMeanings()
//        val args = Bundle()
//        args.putString("jsonArray", jsonString)
//        listOfMeaningsInstance.arguments = args
//        supportFragmentManager.beginTransaction().replace(R.id.activity_result, listOfMeaningsInstance).commit()

//        val audioInstance = Audio()
//        val args = Bundle()
//        args.putString("jsonString", jsonString)
//        audioInstance.arguments = args
//        supportFragmentManager.beginTransaction().add(R.id.fragmentContainerView, audioInstance).commit()

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val myFragment = Audio.newInstance(jsonArray)
        fragmentTransaction.add(R.id.fragmentContainerView, myFragment)
        fragmentTransaction.commit()



    }
}