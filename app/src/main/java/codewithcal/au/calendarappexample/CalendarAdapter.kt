package codewithcal.au.calendarappexample

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import java.time.LocalDate
import java.util.ArrayList

internal class CalendarAdapter( private val days: ArrayList<LocalDate?>,  private val onItemListener: OnItemListener) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    /**
     * Función que nos genera un array con las casillas que están junto a un muro y les aplica un wallDanger de 10
     * @param itemView: Observation of the current state.
     * @param onItemListener:
     */
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

    /**
     * Función que nos genera un array con las casillas que están junto a un muro y les aplica un wallDanger de 10
     * @param parent: Observation of the current state.
     * @param viewType:
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.calendar_cell, parent, false)
        val layoutParams = view.layoutParams
        if (days.size > 15) layoutParams.height =
            (parent.height * 0.166666666).toInt() else layoutParams.height = parent.height
        return CalendarViewHolder(view, onItemListener)
    }

    /**
     * Función que nos genera un array con las casillas que están junto a un muro y les aplica un wallDanger de 10
     * @param holder: Observation of the current state.
     * @param position:
     */
    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val date = days[position]
        if (date == null) holder.dayOfMonth.text = "" else {
            holder.dayOfMonth.text = date.dayOfMonth.toString()
            if (date == CalendarUtils.actualDate)
                holder.parentView.setBackgroundColor(Color.parseColor("#FFA961BD"))
            else
                holder.parentView.setBackgroundColor(Color.WHITE)
        }
    }

    /**
     * Función que nos genera un array con las casillas que están junto a un muro y les aplica un wallDanger de 10
     */
    override fun getItemCount(): Int {
        return days.size
    }

    /**
     * Función que nos genera un array con las casillas que están junto a un muro y les aplica un wallDanger de 10
     */
    interface OnItemListener {
        fun onItemClick(position: Int, dayText: String?)
    }
}