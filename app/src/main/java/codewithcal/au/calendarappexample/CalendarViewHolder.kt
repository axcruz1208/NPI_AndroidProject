package codewithcal.au.calendarappexample

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import codewithcal.au.calendarappexample.CalendarAdapter.OnItemListener

class CalendarViewHolder internal constructor(itemView: View, private val onItemListener: OnItemListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    val parentView: View = itemView.findViewById(R.id.parentView)
    val dayOfMonth: TextView = itemView.findViewById(R.id.cellDayText)

    override fun onClick(view: View) {
        onItemListener.onItemClick(adapterPosition, dayOfMonth.text as String)
    }

    init {
        itemView.setOnClickListener(this)
    }
}