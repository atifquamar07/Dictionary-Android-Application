package com.example.dictionary.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.DetailActivity
import com.example.dictionary.MeaningAdapter
import com.example.dictionary.R
import org.json.JSONArray

class RecyclerViewFragment : Fragment(), MeaningAdapter.OnItemClickListener {

    private var jsonArray: JSONArray? = null

    override fun onItemClick(position: Int) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra("jsonString", jsonArray.toString())
        intent.putExtra("position", position)
        startActivity(intent)
    }

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
            val tempJSONObject = jsonArray!!.getJSONObject(0)
            val getMeaningsJSON = tempJSONObject.getJSONArray("meanings")

            for (i in 0 until getMeaningsJSON.length()) {
                val jsonObject = getMeaningsJSON.getJSONObject(i)
                val partOfSpeech = jsonObject.getString("partOfSpeech")
                partsOfSpeech.add(partOfSpeech)
            }

        }
        Log.i("Inside recycler view frag", "HELLO")
        val rec = view?.findViewById<RecyclerView>(R.id.posRecyclerView)

        if (rec != null) {
            rec.adapter = jsonArray?.let { MeaningAdapter(partsOfSpeech, it, this) }
            rec.layoutManager = LinearLayoutManager(requireContext())
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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