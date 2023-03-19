package com.example.dictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.fragments.AudioFragment
import com.example.dictionary.fragments.RecyclerViewFragment
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
        val myFragment = AudioFragment.newInstance(jsonArray)
        fragmentTransaction.add(R.id.fragmentContainerView, myFragment)
        fragmentTransaction.commit()


        val recyclerFragment = supportFragmentManager
        val recyclerFragmentTransaction = recyclerFragment.beginTransaction()
        val recyclerFrag = RecyclerViewFragment.newInstance(jsonArray)
        recyclerFragmentTransaction.add(R.id.id_fragment_recycler_view, recyclerFrag)
        recyclerFragmentTransaction.commit()





    }
}