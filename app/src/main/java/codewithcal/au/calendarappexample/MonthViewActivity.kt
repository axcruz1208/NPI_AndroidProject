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

    companion object {
        @JvmField
        var selectedDay: LocalDate? = null
    }

    private var monthYearText: TextView? = null
    private var calendarRecyclerView: RecyclerView? = null
    private var gestureDetector: GestureDetector? = null

    private var haulerView: HaulerView? =  null

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

    private fun initWidgets() {
        haulerView = findViewById((R.id.haulerView))
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView)
        monthYearText = findViewById(R.id.monthYearTV)
    }


    private fun setMonthView() {
        monthYearText!!.text = CalendarUtils.selectedDate!!.month.toString() + " " + CalendarUtils.selectedDate!!.year
        val daysInMonth = daysInMonthArray(CalendarUtils.selectedDate)
        val calendarAdapter = CalendarAdapter(daysInMonth, this)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)
        calendarRecyclerView!!.layoutManager = layoutManager
        calendarRecyclerView!!.adapter = calendarAdapter
    }

    fun previousMonthAction(view: View1?) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate!!.minusMonths(1)
        setMonthView()
    }

    fun nextMonthAction(view: View1?) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate!!.plusMonths(1)
        setMonthView()
    }

    override fun onItemClick(position: Int, dayText: String?) {
        if (dayText != "") {
            selectedDay = LocalDate.of(CalendarUtils.selectedDate!!.year, CalendarUtils.selectedDate!!.month, dayText!!.toInt())
            startActivity(Intent(this, DailyViewActivity::class.java))
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector!!.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    fun weeklyAction(view: View1?) {
        startActivity(Intent(this, WeekViewActivity::class.java))
    }

    fun backHome(view: android.view.View) {
        startActivity(Intent(this, MainActivity::class.java))
    }
}