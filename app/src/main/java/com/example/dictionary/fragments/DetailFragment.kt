package com.example.dictionary.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dictionary.R
import org.json.JSONArray

class DetailFragment : Fragment() {

    private var jsonArray: JSONArray? = null
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            val jsonArrayString = it.getString("jsonArray")
            jsonArray = JSONArray(jsonArrayString)
            position = it.getInt("position")
            val jsonObject = jsonArray!!.getJSONObject(0)
            val getName = jsonObject.getString("word")
            Log.i("Message in detail frag", "Word found and the word is $getName")
            Log.i("Message in detail frag", "Position is $position")
        }

        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance(jsonArray: JSONArray, position: Int): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putString("jsonArray", jsonArray.toString())
            args.putInt("position", position)
            fragment.arguments = args
            return fragment
        }
    }
}