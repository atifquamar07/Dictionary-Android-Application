package com.example.dictionary

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray

class MeaningAdapter(private val posList: List<String>, private val jsonArray: JSONArray, private val listener: OnItemClickListener): RecyclerView.Adapter<MeaningAdapter.MeaningViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.pos_item_view, parent, false)
        return MeaningViewHolder(view, jsonArray)
    }

    override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) {
        holder.posText.text = posList[position]
        holder.itemView.setOnClickListener {
            listener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return posList.size
    }

    class MeaningViewHolder(itemView: View, private val jsonArray: JSONArray) : RecyclerView.ViewHolder(itemView){
        var posText = itemView.findViewById<TextView>(R.id.id_pos_text)
        init {
            itemView.setOnClickListener {
                val jsonObject = jsonArray.getJSONObject(0)
                val getUrl = jsonObject.getString("sourceUrls")
                Log.i("Message in adapter", "Word found and the sourceUrls is $getUrl")

            }
        }
    }

}

