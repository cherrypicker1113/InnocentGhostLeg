package com.innocent.ghostleg

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class GhostLegResult(val index: Int, val name: String)

class GhostLegAdapter(val context: Context, val dataList: ArrayList<GhostLegResult>): BaseAdapter() {
    fun addItem(data: GhostLegResult) {
        dataList.add(data)
        notifyDataSetChanged()
    }

    fun removeItem() {
        dataList.remove(dataList.last())
        notifyDataSetChanged()
    }

    // Data 갯수
    override fun getCount() = dataList.size

    // position (index와 같은 개념) 에 해당하는 Data 반환
    override fun getItem(position: Int) = dataList[position]

    // L : Long 형식
    override fun getItemId(position: Int) = 0L

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.ghostleg_result_list, null)
        val nameView = view.findViewById<TextView>(R.id.tv_name)
        val idxView = view.findViewById<TextView>(R.id.tv_idx)

        val data = dataList[position]
        nameView.text = data.name
        idxView.text = data.index.toString()

        return view;
    }
}