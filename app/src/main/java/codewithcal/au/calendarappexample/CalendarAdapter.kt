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
     * Clase interna que nos permite declarar que contenido va a almacenar cada vista del recyclerView.
     * @param itemView: Vista del recycler view que se está declarando
     * @param onItemListener: Elemento que va a estar "escuchando", esperando a que lo pulsen.
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
     * Función que crea un nuevo viewHolder cuando no hay ViewHolders que recyclerView pueda utilizar
     * @param parent: ViewGroup en el que se agregará la nueva vista después de que se vincule a una posición de adaptador.
     * @param viewType:El tipo de vista
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
     * Función que llama RecyclerView para mostrar los datos en la posición especificada.
     * Este método debería actualizar el contenido de cada vista para reflejar el elemento en la posición dada.
     * @param holder: ViewHolder que debe actualizarse para representar el contenido del elemento.
     * @param position: La posición del elemento dentro del conjunto de datos del adaptador.
     */
    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val date = days[position]
        if (date == null)
            holder.dayOfMonth.text = ""
        else {
            holder.dayOfMonth.text = date.dayOfMonth.toString()
            //En caso de que sea el día actual el que tiene que actualizar, lo pinta de color morado para diferenciarlo
            if (date == CalendarUtils.actualDate)
                holder.parentView.setBackgroundColor(Color.parseColor("#FFA961BD"))
            else
                holder.parentView.setBackgroundColor(Color.WHITE)
        }
    }

    /**
     * Función que nos devuelve el número de vistar de nuestro ViewGroup, en este caso equivalente al
     * número de días del mes o de la semana.
     */
    override fun getItemCount(): Int {
        return days.size
    }

    interface OnItemListener {
        fun onItemClick(position: Int, dayText: String?)
    }
}