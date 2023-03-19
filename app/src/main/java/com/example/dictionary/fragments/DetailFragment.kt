package com.example.dictionary.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.dictionary.R
import org.json.JSONArray

class DetailFragment : Fragment() {

    private var jsonArray: JSONArray? = null
    private var position: Int = 0
    private lateinit var editPOS: TextView
    private lateinit var editDefinition: TextView
    private lateinit var editSynonyms: TextView
    private lateinit var editAntonyms: TextView
    private lateinit var editExamples: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val jsonArrayString = it.getString("jsonArray")
            jsonArray = JSONArray(jsonArrayString)
            position = it.getInt("position")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        editPOS = view.findViewById(R.id.pos)
        editDefinition = view.findViewById(R.id.definition)
        editSynonyms = view.findViewById(R.id.synonym)
        editAntonyms = view.findViewById(R.id.antonym)
        editExamples = view.findViewById(R.id.examples)

        return view
    }

    fun jsonArrayHasKey(jsonArray: JSONArray, key: String): Boolean {
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            if (jsonObject.has(key)) {
                return true
            }
        }
        return false
    }

    private fun JSONArray.getStringList(): List<String> {
        val list = mutableListOf<String>()
        for (i in 0 until length()) {
            list.add(getString(i))
        }
        return list
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val jsonObject = jsonArray?.getJSONObject(0)
        val getMeaningsJSONString = jsonObject?.getJSONArray("meanings")
        val requiredPOS = getMeaningsJSONString?.getJSONObject(position)

        if (requiredPOS != null) {
            editPOS.text = requiredPOS.getString("partOfSpeech")
            val definitionsArray = requiredPOS.getJSONArray("definitions")
            val firstDefinition = definitionsArray.getJSONObject(0)
            editDefinition.text = firstDefinition?.getString("definition")
            if (firstDefinition != null && firstDefinition.has("example")) {
                editExamples.text = firstDefinition.getString("example")
            }
            val commaSeparatedSynonyms = requiredPOS.getJSONArray("synonyms").getStringList().joinToString(separator = ",")
            editSynonyms.text = commaSeparatedSynonyms
            val commaSeparatedAntonyms = requiredPOS.getJSONArray("antonyms").getStringList().joinToString(separator = ",")
            editAntonyms.text = commaSeparatedAntonyms
            Log.i("in detail activity commaSeparatedSynonyms", commaSeparatedSynonyms)
            Log.i("in detail activity commaSeparatedAntonyms", commaSeparatedAntonyms)
        }

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