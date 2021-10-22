package axcruz.horario

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.futured.hauler.HaulerView
import app.futured.hauler.setOnDragDismissedListener
import axcruz.horario.R
import java.util.*

class DailyViewActivity : AppCompatActivity() {

    //Variables para declarar elementos del DailyViewActivity
    private var dayOfWeek: TextView? = null
    private var dayAndMonth: TextView? = null
    private var rv: RecyclerView? = null
    private var classrooms = ArrayList<Classroom>()
    private var noHayClase: TextView? = null
    private var noHayClase2: TextView? = null

    //Variable para Gesto de volver al MainActivity
    private var haulerView: HaulerView? =  null

    //CardView variables
    private var nextClass: CardView? = null
    private var nextClass_name: TextView? = null
    private var nextClass_description: TextView? = null
    private var nextClass_photo: ImageView? = null

    /**
     * En esta función se inicializa su actividad. Aquí es donde normalmente se llamará a setContentView para definir
     * la interfaz de usuario y usará findViewById para asignar los widgets a esa interfaz de usuario y poder programar con ellos.
     * @param savedInstanceState: Nos guarda el estado de la apicación
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_view)

        initWidgets()

        haulerView!!.setOnDragDismissedListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }

        setDayView()
        setClassrooms()
        setRVView()
    }

    /**
     * Función donde asignamos a las variables creadas el ID correspondiente con los widgets de la interfaz
     */
    private fun initWidgets() {
        dayOfWeek = findViewById(R.id.DayOfWeek)
        dayAndMonth = findViewById(R.id.MonthAndDay)
        rv = findViewById(R.id.rv)
        noHayClase = findViewById(R.id.NoHayClase)
        noHayClase2 = findViewById(R.id.NoHayClase2)

        haulerView = findViewById((R.id.haulerView))

        nextClass = findViewById(R.id.nextClass)
        nextClass_name = findViewById(R.id.nextClass_name)
        nextClass_description = findViewById(R.id.nextclass_description)
        nextClass_photo = findViewById(R.id.nextClass_photo)
    }

    /**
     * Función donde se crean las asignaturas que podemos tener, son una variable de la clase Classroom.
     * Además las mostramos en esta actividad de forma dinámica dependiendo de las clases que tengamos ese día.
     */
    private fun setClassrooms(){

        noHayClase!!.visibility = View.GONE
        noHayClase2!!.visibility = View.GONE

        val NPIT = Classroom("NPI (Teoría)", getString(R.string.NPIT), R.drawable.npi, 1030)
        val NPIP = Classroom("NPI (Prácticas)", getString(R.string.NPIP), R.drawable.npi, 830)
        val RSCT = Classroom("RSC (Teoría)", getString(R.string.RSCT), R.drawable.rsc, 930)
        val RSCP = Classroom("RSC (Prácticas)", getString(R.string.RSCP), R.drawable.rsc, 1130)
        val VCT = Classroom("VC (Teoría)", getString(R.string.VCT), R.drawable.vc, 830)
        val VCP = Classroom("VC (Prácticas)", getString(R.string.VCP), R.drawable.vc, 1030)
        val PLT = Classroom("PL (Teoría)", getString(R.string.PLT), R.drawable.pl, 1230)
        val PLP = Classroom("PL (Prácticas)", getString(R.string.PLP), R.drawable.pl, 1230)

        //Como yo solo tengo clases los lunes, martes y miercoles. Son los días en los que asignamos clases
        when(MonthViewActivity.selectedDay?.dayOfWeek!!.name){
            "WEDNESDAY"-> {
                classrooms.add(RSCT)
                classrooms.add(RSCP)
            }
            "THURSDAY"-> {
                classrooms.add(NPIT)
                classrooms.add(NPIP)
                classrooms.add(PLT)
            }
            "FRIDAY"-> {
                classrooms.add(VCT)
                classrooms.add(VCP)
                classrooms.add(PLP)
            }
            else -> {

                //En caso de que no tenga clase se muestra un mensaje de que hoy no hay clase

                rv!!.visibility = View.GONE
                nextClass!!.visibility = View.GONE
                noHayClase!!.visibility = View.VISIBLE
                noHayClase2!!.visibility = View.VISIBLE
            }
        }

        //Si tenemos clase, la siguiente clase será la más próxima a nuestra hora actual.
        //Por lo que siempre que la hora de la clase sea mayor que la actual, asignamos como siguiente clase
        //la que tenga una diferencia con nuestra hora actual menor.
        if(nextClass!!.visibility == View.VISIBLE) {
            val calendar = Calendar.getInstance()
            var horaActual = (calendar.get(Calendar.HOUR_OF_DAY).toString() + calendar.get(Calendar.MINUTE).toString()).toInt()
            var masCercano = 2400
            var position = 0
            for (i in classrooms.indices) {
                if (classrooms[i].time - horaActual < masCercano && horaActual < classrooms[i].time) {
                    masCercano = classrooms[i].time - horaActual
                    position = i
                }
            }

            nextClass_name!!.text = classrooms[position].name
            nextClass_description!!.text = classrooms[position].description
            nextClass_photo!!.setImageResource(classrooms[position].photoID)
        }
    }

    /**
     * Función que inicializa el recyclerView que contiene todas las asignaturas del día actual/seleccionado
     */
    private fun setRVView(){
        rv!!.setHasFixedSize(true)
        rv!!.layoutManager = LinearLayoutManager(this)
        val adapter = ClassroomAdapter(classrooms)
        rv!!.adapter = adapter
    }

    /**
     * Función que asigna a los dos TexView de la interfaz el día y el mes actual, o boen del día que hayamos elegido
     */
    private fun setDayView() {
        dayOfWeek!!.text = MonthViewActivity.selectedDay?.dayOfWeek!!.name
        dayAndMonth!!.text = MonthViewActivity.selectedDay!!.month.toString() + "/" + MonthViewActivity.selectedDay!!.dayOfMonth
    }

    /**
     * Función onClick que al activarla nos va a redirigir a la actividad WeekViewActivity
     */
    fun DailyToWeekly(view: View?) {
        startActivity(Intent(this, WeekViewActivity::class.java))
    }

    /**
     * Función onClick que al activarla nos va a redirigir a la actividad MainActivity
     */
    fun backHome(view: View?) {
        startActivity(Intent(this, MainActivity::class.java))
    }
}