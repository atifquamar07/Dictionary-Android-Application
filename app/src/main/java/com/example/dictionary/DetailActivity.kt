package com.example.dictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.dictionary.fragments.DetailFragment
import org.json.JSONArray

class DetailActivity : AppCompatActivity() {

    private var jsonArray: JSONArray? = null
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val jsonArrayString = intent.getStringExtra("jsonString")
        jsonArray = JSONArray(jsonArrayString)
        position = intent.getIntExtra("position", 0)

        val jsonObject = jsonArray!!.getJSONObject(0)
        val getName = jsonObject.getString("word")
        Log.i("Message in detail activity", "Word found and the word is $getName")
        Log.i("Message in detail activity", "Position is $position")

//        val fragmentManager = supportFragmentManager
//        val fragmentTransaction = fragmentManager.beginTransaction()
//        val myFragment = DetailFragment.newInstance(jsonArray!!, position)
//        fragmentTransaction.add(R.id.detailFragmentContainerView, myFragment)
//        fragmentTransaction.commit()

        val detailFragment = DetailFragment()
        val bundle = Bundle()
        bundle.putString("jsonArray", jsonArray.toString())
        bundle.putInt("position", position)
        detailFragment.arguments = bundle
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.detailFragmentContainerView, detailFragment)
        fragmentTransaction.commit()


    }
}