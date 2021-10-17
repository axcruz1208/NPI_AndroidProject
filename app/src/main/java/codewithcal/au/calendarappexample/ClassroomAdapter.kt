package codewithcal.au.calendarappexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

internal class ClassroomAdapter(private val classrooms: ArrayList<Classroom>?) : RecyclerView.Adapter<ClassroomAdapter.ClassroomViewHolder>() {

    class ClassroomViewHolder internal constructor (itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cv: CardView = itemView.findViewById(R.id.cv)
        val className: TextView = itemView.findViewById(R.id.class_name)
        val classDescription: TextView = itemView.findViewById(R.id.class_description)
        val classPhoto: ImageView = itemView.findViewById(R.id.class_photo)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassroomViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.classroom_cell, parent, false)
        val cvh = ClassroomViewHolder(v)
        return cvh
    }

    override fun onBindViewHolder(holder: ClassroomViewHolder, position: Int) {
        holder.className.text = classrooms!![position].name
        holder.classDescription.text = classrooms!![position].description
        holder.classPhoto.setImageResource(classrooms!![position].photoID!!)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun getItemCount(): Int {
        return classrooms!!.size
    }
}