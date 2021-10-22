package codewithcal.au.calendarappexample

import codewithcal.au.calendarappexample.CalendarUtils.daysInWeekArray
import androidx.appcompat.app.AppCompatActivity
import codewithcal.au.calendarappexample.CalendarAdapter.OnItemListener
import android.view.GestureDetector
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import android.content.Intent
import android.view.View
import app.futured.hauler.HaulerView
import app.futured.hauler.setOnDragDismissedListener
import com.islandparadise14.mintable.MinTimeTableView
import com.islandparadise14.mintable.model.ScheduleDay
import com.islandparadise14.mintable.model.ScheduleEntity
import java.time.LocalDate

class WeekViewActivity : AppCompatActivity(), OnItemListener {

    //Variables para declarar elementos del WeekViewActivity
    private var monthYearText: TextView? = null
    private var calendarRecyclerView: RecyclerView? = null
    private var gestureDetector: GestureDetector? = null

    //Variable para Gesto de volver al MainActivity
    private var haulerView: HaulerView? =  null

    //Variables Horario
    private var day = arrayOf("Mon", "Tue", "Wen", "Thu", "Fri")
    private var table: MinTimeTableView? = null
    private var scheduleList: ArrayList<ScheduleEntity> = ArrayList()

    /**
     * En esta función se inicializa su actividad. Aquí es donde normalmente se llamará a setContentView para definir
     * la interfaz de usuario y usará findViewById para asignar los widgets a esa interfaz de usuario y poder programar con ellos.
     * @param savedInstanceState: Nos guarda el estado de la apicación
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_week_view)
        createSchedule()
        initWidgets()

        //Le asignamos al Hauler que queremos cerrar esta actividad y volver al MainActivity
        haulerView!!.setOnDragDismissedListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }

        onWindowFocusChanged(false)
        setWeekView()
    }

    /**
     * Función que asigna a una ventana si va a tener el foco de la actividad, es decir, si va a ser
     * la ventana con la que el usuario va a interactuar
     * @param hasFocus: Si la ventana de esta actividad tiene foco.
     */
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        table?.initTable(day) //Asignamos la leyendo horizontal del horario (lunes, martes...)
        table?.updateSchedules(scheduleList) //Le asignamos al horario las distintas clases
    }

    /**
     * Función para crear las distintas clases que están en el horario semanal que pertenecen a una clase
     * llamada schedule
     */
    private fun createSchedule(){

        val schedule = ScheduleEntity(
            32, //originId
            "RSC-T", //scheduleName
            "0.8", //roomInfo
            ScheduleDay.WEDNESDAY, //ScheduleDay (MONDAY ~ SUNDAY)
            "9:30", //startTime format: "HH:mm"
            "11:30", //endTime  format: "HH:mm"
            "#f9c743", //backgroundColor (optional)
            "#000000" //textcolor (optional)
        )

        val schedule2 = ScheduleEntity(33, "RSC-P", "0.8", ScheduleDay.WEDNESDAY, "11:30", "13:30", "#f9da89", "#000000")
        val schedule3 = ScheduleEntity(34, "NPI-P", "3.3", ScheduleDay.THURSDAY, "8:30", "10:30", "#cfe98a", "#000000")
        val schedule4 = ScheduleEntity(35, "NPI-T", "1.5", ScheduleDay.THURSDAY, "10:30", "12:30", "#8faf39", "#000000")
        val schedule5 = ScheduleEntity(36, "PL-T", "1.5", ScheduleDay.THURSDAY, "12:30", "14:30", "#5ad69c", "#000000")
        val schedule6 = ScheduleEntity(36, "VC-T", "1.7", ScheduleDay.FRIDAY, "8:30", "10:30", "#c95873", "#000000")
        val schedule7 = ScheduleEntity(36, "VC-P", "3.7", ScheduleDay.FRIDAY, "10:30", "12:30", "#fa95ad", "#000000")
        val schedule8 = ScheduleEntity(36, "PL-P", "2.8", ScheduleDay.FRIDAY, "12:30", "14:30", "#94f5c8", "#000000")

        scheduleList.add(schedule)
        scheduleList.add(schedule2)
        scheduleList.add(schedule3)
        scheduleList.add(schedule4)
        scheduleList.add(schedule5)
        scheduleList.add(schedule6)
        scheduleList.add(schedule7)
        scheduleList.add(schedule8)
    }

    /**
     * Función donde asignamos a las variables creadas el ID correspondiente con los widgets de la interfaz
     */
    private fun initWidgets() {
        haulerView = findViewById((R.id.haulerView))
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView)
        monthYearText = findViewById(R.id.monthYearTV)
        table = findViewById(R.id.horarioClases)
    }

    /**
     * Función que asigna al textView el mes actual y el año. Además actualiza el recyclerView con la disposición de días de la
     * semana actual/seleccionada
     */
    private fun setWeekView() {
        monthYearText!!.text = CalendarUtils.selectedDate!!.month.toString() + " " + CalendarUtils.selectedDate!!.year
        val days = daysInWeekArray(CalendarUtils.selectedDate!!)
        val calendarAdapter = CalendarAdapter(days, this)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)
        calendarRecyclerView!!.layoutManager = layoutManager
        calendarRecyclerView!!.adapter = calendarAdapter
    }

    /**
     * Función que nos inicializa la variable selectedDay con el día del calendario que pulsemos y nos
     * redirige a la actividad DailyViewActivity de el día seleccionado
     * @param position: La posición de la vista en el adaptador.
     * @param dayText: El texto que contiene la sección que hemos pulsado.
     */
    override fun onItemClick(position: Int, dayText: String?) {
        MonthViewActivity.selectedDay = LocalDate.of(CalendarUtils.selectedDate!!.year, CalendarUtils.selectedDate!!.month, dayText!!.toInt())
        startActivity(Intent(this, DailyViewActivity::class.java))
    }

    /**
     * Función onClick que al activarla nos cambia a la semana anterior a la actual en el calendario
     */
    fun previousWeekAction(view: View?) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate!!.minusWeeks(1)
        setWeekView()
    }

    /**
     * Función onClick que al activarla nos cambia a la semana posterior a la actual en el calendario
     */
    fun nextWeekAction(view: View?) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate!!.plusWeeks(1)
        setWeekView()
    }

    /**
     * Función onClick que al activarla nos va a redirigir a la actividad DailyViewActivity
     */
    fun dailyAction(view: View?) {
        MonthViewActivity.selectedDay = LocalDate.of(CalendarUtils.actualDate!!.year, CalendarUtils.actualDate!!.month, CalendarUtils.actualDate!!.dayOfMonth)
        startActivity(Intent(this, DailyViewActivity::class.java))
    }

    /**
     * Función onClick que al activarla nos va a redirigir a la actividad MonthViewActivity
     */
    fun monthlyAction(view: View?) {
        startActivity(Intent(this, MonthViewActivity::class.java))
    }

    /**
     * Función onClick que al activarla nos va a redirigir a la actividad MainActivity
     */
    fun backHome(view: View?) {
        startActivity(Intent(this, MainActivity::class.java))
    }
}