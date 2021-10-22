package axcruz.horario

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import axcruz.horario.R
import java.util.ArrayList

internal class ClassroomAdapter(private val classrooms: ArrayList<Classroom>?) : RecyclerView.Adapter<ClassroomAdapter.ClassroomViewHolder>() {

    /**
     * Clase interna que nos permite declarar que contenido va a almacenar cada vista del recyclerView.
     * @param itemView: Vista del recycler view que se está declarando
     */
    class ClassroomViewHolder internal constructor (itemView: View) : RecyclerView.ViewHolder(itemView) {

        val className: TextView = itemView.findViewById(R.id.class_name)
        val classDescription: TextView = itemView.findViewById(R.id.class_description)
        val classPhoto: ImageView = itemView.findViewById(R.id.class_photo)

    }

    /**
     * Función que crea un nuevo viewHolder cuando no hay ViewHolders que recyclerView pueda utilizar
     * @param parent: ViewGroup en el que se agregará la nueva vista después de que se vincule a una posición de adaptador.
     * @param viewType:El tipo de vista
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassroomViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.classroom_cell, parent, false)
        return ClassroomViewHolder(v)
    }

    /**
     * Función que llama RecyclerView para mostrar los datos en la posición especificada.
     * Este método debería actualizar el contenido de cada vista para reflejar el elemento en la posición dada.
     * @param holder: ViewHolder que debe actualizarse para representar el contenido del elemento.
     * @param position: La posición del elemento dentro del conjunto de datos del adaptador.
     */
    override fun onBindViewHolder(holder: ClassroomViewHolder, position: Int) {
        holder.className.text = classrooms!![position].name
        holder.classDescription.text = classrooms[position].description
        holder.classPhoto.setImageResource(classrooms[position].photoID)
    }

    /**
     * Función que nos devuelve el número de vistar de nuestro ViewGroup, en este caso equivalente al
     * número de clases que tiene ese día
     */
    override fun getItemCount(): Int {
        return classrooms!!.size
    }
}