package com.example.dictionary.fragments

import android.annotation.SuppressLint
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.AsyncTask
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
import java.io.IOException

class AudioFragment : Fragment() {

    private var jsonArray: JSONArray? = null
    private lateinit var btAudio: Button
    @SuppressLint("StaticFieldLeak")
    private inner class PlayAudioTask(private val audioUrl: String) : AsyncTask<Void, Void, Boolean>() {

        private var mediaPlayer: MediaPlayer = MediaPlayer()
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: Void?): Boolean {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mediaPlayer.setDataSource(audioUrl)
            mediaPlayer.prepareAsync()
            return true
        }
        @Deprecated("Deprecated in Java")
        override fun onCancelled(result: Boolean?) {
            super.onCancelled(result)
            mediaPlayer.release()
        }
        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            mediaPlayer.setOnPreparedListener { player ->
                player.start()
            }
        }
    }

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
            var isAudioPresent = false
            var audioUrl = ""
            if(jsonArray.getJSONObject(0).getJSONArray("phonetics").length() > 0){
                audioUrl = jsonArray.getJSONObject(0).getJSONArray("phonetics").getJSONObject(0).getString("audio")
                isAudioPresent = true
            }

            btAudio = view.findViewById(R.id.btn_audio)
            btAudio.setOnClickListener {
                Log.i("Audio URL on button click in Audio frag", audioUrl)
                if (audioUrl.isBlank() or !isAudioPresent) {
                    Toast.makeText(requireContext(), "Audio not available", Toast.LENGTH_SHORT).show()
                } else {
                    val playAudioTask = PlayAudioTask(audioUrl)
                    playAudioTask.execute()
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