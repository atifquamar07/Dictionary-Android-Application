package com.example.dictionary.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.MeaningAdapter
import com.example.dictionary.R
import org.json.JSONArray

class RecyclerViewFragment : Fragment() {

    private var jsonArray: JSONArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recycler_view, container, false)
        val partsOfSpeech = mutableListOf<String>()
        arguments?.let {
            val jsonArrayString = it.getString("jsonArray")
            jsonArray = JSONArray(jsonArrayString)
//            val jsonArray = JSONArray(jsonArrayString)
            val tempJSONObject = jsonArray!!.getJSONObject(0)
            val getMeaningsJSON = tempJSONObject.getJSONArray("meanings")

            for (i in 0 until getMeaningsJSON.length()) {
                val jsonObject = getMeaningsJSON.getJSONObject(i)
                val partOfSpeech = jsonObject.getString("partOfSpeech")
                partsOfSpeech.add(partOfSpeech)
            }

        }
        Log.i("Inside recycler view frag", "HELLO")
//        val pos = listOf("noun", "verb", "adjective", "adverb", "preposition", "atif")
        val rec = view?.findViewById<RecyclerView>(R.id.posRecyclerView)

        if (rec != null) {
            rec.adapter = jsonArray?.let { MeaningAdapter(partsOfSpeech, it) }
            rec.layoutManager = LinearLayoutManager(requireContext())
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val tempJSONObject = jsonArray?.getJSONObject(0)
//        val getMeaningsJSON = tempJSONObject?.getJSONArray("meanings")
//        val partsOfSpeech = mutableListOf<String>()
//        if (getMeaningsJSON != null) {
//            for (i in 0 until getMeaningsJSON.length()) {
//                val jsonObject = getMeaningsJSON.getJSONObject(i)
//                val partOfSpeech = jsonObject.getString("partOfSpeech")
//                partsOfSpeech.add(partOfSpeech)
//            }
//        }
//
//        if (getMeaningsJSON != null) {
//            for (i in 0 until getMeaningsJSON.length()) {
//                val jsonObject = getMeaningsJSON.getJSONObject(i)
//                val partOfSpeech = jsonObject.getString("partOfSpeech")
//                partsOfSpeech.add(partOfSpeech)
//            }
//        }
//        val rec = view.findViewById<RecyclerView>(R.id.posRecyclerView)
//
//        if (rec != null) {
//            rec.adapter = jsonArray?.let { MeaningAdapter(partsOfSpeech, view, it) }
//            rec.layoutManager = LinearLayoutManager(requireContext())
//        }
    }

    companion object {

        fun newInstance(jsonArray: JSONArray): RecyclerViewFragment {
            val fragment = RecyclerViewFragment()
            val args = Bundle()
            args.putString("jsonArray", jsonArray.toString())
            fragment.arguments = args
            return fragment
        }
    }
}