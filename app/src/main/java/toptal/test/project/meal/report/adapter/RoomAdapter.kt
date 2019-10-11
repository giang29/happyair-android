package toptal.test.project.meal.report.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.i_spinner.view.*
import toptal.test.project.meal.R


class RoomAdapter(context: Context, private val data: List<String>):
    ArrayAdapter<String>(context, 0, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val viewHolder = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_dropdown_item_1line, parent, false)

        (viewHolder as TextView).apply {
            text = data[position]
            setTextColor(Color.parseColor("#ffffff"))
        }
        return viewHolder
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder = convertView ?: LayoutInflater.from(context).inflate(R.layout.i_spinner, parent, false)

        viewHolder.i_spinner_text.apply {
            text = data[position]
            setTextColor(Color.parseColor("#000000"))
        }
        return viewHolder
    }

    override fun getCount(): Int {
        return data.size
    }
}
