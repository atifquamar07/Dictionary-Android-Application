package com.example.dictionary.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.dictionary.R
import org.json.JSONArray

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/*
 *
 * A simple [Fragment] subclass.
 * Use the [Audio.newInstance] factory method to
 * create an instance of this fragment.
 */
class AudioFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var jsonArray: JSONArray? = null
    private lateinit var btAudio: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("IN AUDIO FRAGMENT", "HELLO")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_audio, container, false)
        arguments?.let {
            val jsonArrayString = it.getString("jsonArray")
            jsonArray = JSONArray(jsonArrayString)
            val jsonArray = JSONArray(jsonArrayString)
            val jsonObject = jsonArray.getJSONObject(0)
            val getUrl = jsonObject.getString("sourceUrls")
            Log.i("Message in audio", "Word found and the sourceUrls is $getUrl")
            val audioUrl = jsonArray.getJSONObject(0).getJSONArray("phonetics").getJSONObject(0).getString("audio")

            btAudio = view.findViewById(R.id.btn_audio)
            btAudio.setOnClickListener {
                if (audioUrl.isBlank()) {
                    Toast.makeText(requireContext(), "Audio not available", Toast.LENGTH_SHORT).show()
                } else {
                    val mediaPlayer = MediaPlayer()
                    mediaPlayer.setDataSource(audioUrl)
                    mediaPlayer.prepareAsync()
                    mediaPlayer.setOnPreparedListener { player ->
                        player.start()
                    }
                    mediaPlayer.setOnCompletionListener {
                        mediaPlayer.release()
                    }
                }
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        fun newInstance(jsonArray: JSONArray): AudioFragment {
            val fragment = AudioFragment()
            val args = Bundle()
            args.putString("jsonArray", jsonArray.toString())
            fragment.arguments = args
            return fragment
        }
    }

}