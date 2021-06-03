package com.innocent.ghostleg

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView

class Data(val index: Int, val name: String)

class HrgwonAdapter(val context: Context, val dataList: ArrayList<Data>): BaseAdapter() {
    fun addItem(data: Data) {
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
        val view: View = LayoutInflater.from(context).inflate(R.layout.edittext_list, null)
        val textView = view.findViewById<TextView>(R.id.tv_number)
        val editText = view.findViewById<EditText>(R.id.ed_name)

        val data = dataList[position]
        textView.text = data.index.toString()
        editText.setText(data.name)

        return view;
    }
}