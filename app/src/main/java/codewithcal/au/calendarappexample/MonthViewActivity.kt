package codewithcal.au.calendarappexample

import codewithcal.au.calendarappexample.CalendarUtils.daysInMonthArray
import androidx.appcompat.app.AppCompatActivity
import codewithcal.au.calendarappexample.CalendarAdapter.OnItemListener
import android.view.GestureDetector
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import android.content.Intent
import android.view.MotionEvent
import app.futured.hauler.HaulerView
import app.futured.hauler.setOnDragDismissedListener
import java.time.LocalDate
import android.view.View as View1

class MonthViewActivity : AppCompatActivity(), OnItemListener{

    //El día seleccionado en el calendario
    companion object {
        @JvmField
        var selectedDay: LocalDate? = null
    }

    //Variables para declarar elementos del MonthViewActivity
    private var monthYearText: TextView? = null
    private var calendarRecyclerView: RecyclerView? = null
    private var gestureDetector: GestureDetector? = null

    //Variable para Gesto de volver al MainActivity
    private var haulerView: HaulerView? =  null

    /**
     * En esta función se inicializa su actividad. Aquí es donde normalmente se llamará a setContentView para definir
     * la interfaz de usuario y usará findViewById para asignar los widgets a esa interfaz de usuario y poder programar con ellos.
     * @param savedInstanceState: Nos guarda el estado de la apicación
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_month_view)
        initWidgets()

        haulerView!!.setOnDragDismissedListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }

        CalendarUtils.selectedDate = LocalDate.now()
        CalendarUtils.actualDate = LocalDate.now()
        setMonthView()
    }

    /**
     * Función donde asignamos a las variables creadas el ID correspondiente con los widgets de la interfaz
     */
    private fun initWidgets() {
        haulerView = findViewById((R.id.haulerView))
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView)
        monthYearText = findViewById(R.id.monthYearTV)
    }

    /**
     * Función que asigna al textView el mes actual y además actualiza el recyclerView con la disposición de días del
     * mes actual/seleccionado
     */
    private fun setMonthView() {
        monthYearText!!.text = CalendarUtils.selectedDate!!.month.toString() + " " + CalendarUtils.selectedDate!!.year
        val daysInMonth = daysInMonthArray(CalendarUtils.selectedDate)
        val calendarAdapter = CalendarAdapter(daysInMonth, this)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)
        calendarRecyclerView!!.layoutManager = layoutManager
        calendarRecyclerView!!.adapter = calendarAdapter
    }

    /**
     * Función que nos genera un array con las casillas que están junto a un muro y les aplica un wallDanger de 10
     * @param position: Observation of the current state.
     * @param dayText:
     */
    override fun onItemClick(position: Int, dayText: String?) {
        if (dayText != "") {
            selectedDay = LocalDate.of(CalendarUtils.selectedDate!!.year, CalendarUtils.selectedDate!!.month, dayText!!.toInt())
            startActivity(Intent(this, DailyViewActivity::class.java))
        }
    }

    /**
     * Función que nos genera un array con las casillas que están junto a un muro y les aplica un wallDanger de 10
     * @param event:
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector!!.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    /**
     * Función onClick que al activarla nos cambia al mes anterior al actual en el calendario
     */
    fun previousMonthAction(view: View1?) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate!!.minusMonths(1)
        setMonthView()
    }

    /**
     * Función onClick que al activarla nos cambia al mes posterior al actual en el calendario
     */
    fun nextMonthAction(view: View1?) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate!!.plusMonths(1)
        setMonthView()
    }

    /**
     * Función onClick que al activarla nos va a redirigir a la actividad WeekViewActivity
     */
    fun weeklyAction(view: View1?) {
        startActivity(Intent(this, WeekViewActivity::class.java))
    }

    /**
     * Función onClick que al activarla nos va a redirigir a la actividad MainActivity
     */
    fun backHome(view: android.view.View) {
        startActivity(Intent(this, MainActivity::class.java))
    }
}