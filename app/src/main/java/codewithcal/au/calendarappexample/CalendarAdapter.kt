package codewithcal.au.calendarappexample

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import java.time.LocalDate
import java.util.ArrayList

internal class CalendarAdapter( private val days: ArrayList<LocalDate?>,  private val onItemListener: OnItemListener) : RecyclerView.Adapter<CalendarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.calendar_cell, parent, false)
        val layoutParams = view.layoutParams
        if (days.size > 15) layoutParams.height =
            (parent.height * 0.166666666).toInt() else layoutParams.height = parent.height
        return CalendarViewHolder(view, onItemListener)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val date = days[position]
        if (date == null) holder.dayOfMonth.text = "" else {
            holder.dayOfMonth.text = date.dayOfMonth.toString()
            if (date == CalendarUtils.actualDate) holder.parentView.setBackgroundColor(
                Color.parseColor(
                    "#FFA961BD"
                )
            ) else holder.parentView.setBackgroundColor(
                Color.WHITE
            )
        }
    }

    override fun getItemCount(): Int {
        return days.size
    }

    interface OnItemListener {
        fun onItemClick(position: Int, dayText: String?)
    }
}