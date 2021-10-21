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
import android.view.MotionEvent
import android.view.View
import app.futured.hauler.HaulerView
import app.futured.hauler.setOnDragDismissedListener
import com.islandparadise14.mintable.MinTimeTableView
import com.islandparadise14.mintable.model.ScheduleDay
import com.islandparadise14.mintable.model.ScheduleEntity
import java.time.LocalDate

class WeekViewActivity : AppCompatActivity(), OnItemListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private var monthYearText: TextView? = null
    private var calendarRecyclerView: RecyclerView? = null
    private var gestureDetector: GestureDetector? = null

    private var haulerView: HaulerView? =  null

    //Variables Horario
    private var day = arrayOf("Mon", "Tue", "Wen", "Thu", "Fri")
    private var table: MinTimeTableView? = null
    private var scheduleList: ArrayList<ScheduleEntity> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_week_view)
        createSchedule()
        gestureDetector = GestureDetector(this, this)
        gestureDetector!!.setOnDoubleTapListener(this)
        initWidgets()

        haulerView!!.setOnDragDismissedListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }

        onWindowFocusChanged(false)
        setWeekView()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        table?.initTable(day)
        table?.updateSchedules(scheduleList)
    }

    private fun createSchedule(){

        val schedule = ScheduleEntity(
            32, //originId
            "RSC-T", //scheduleName
            "0.8", //roomInfo
            ScheduleDay.WEDNESDAY, //ScheduleDay object (MONDAY ~ SUNDAY)
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

    private fun initWidgets() {
        haulerView = findViewById((R.id.haulerView))
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView)
        monthYearText = findViewById(R.id.monthYearTV)
        table = findViewById(R.id.horarioClases)
    }

    private fun setWeekView() {
        monthYearText!!.text = CalendarUtils.selectedDate!!.month.toString() + " " + CalendarUtils.selectedDate!!.year
        val days = daysInWeekArray(CalendarUtils.selectedDate!!)
        val calendarAdapter = CalendarAdapter(days, this)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)
        calendarRecyclerView!!.layoutManager = layoutManager
        calendarRecyclerView!!.adapter = calendarAdapter
    }

    override fun onItemClick(position: Int, dayText: String?) {
        MonthViewActivity.selectedDay = LocalDate.of(CalendarUtils.selectedDate!!.year, CalendarUtils.selectedDate!!.month, dayText!!.toInt())
        startActivity(Intent(this, DailyViewActivity::class.java))
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector!!.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun onSingleTapConfirmed(motionEvent: MotionEvent): Boolean {
        return false
    }

    override fun onDoubleTap(motionEvent: MotionEvent): Boolean {
        return false
    }

    override fun onDoubleTapEvent(motionEvent: MotionEvent): Boolean {
        return false
    }

    override fun onDown(motionEvent: MotionEvent): Boolean {
        return false
    }

    override fun onShowPress(motionEvent: MotionEvent) {}

    override fun onSingleTapUp(motionEvent: MotionEvent): Boolean {
        return false
    }

    override fun onScroll(motionEvent: MotionEvent, motionEvent1: MotionEvent, v: Float, v1: Float): Boolean {
        return false
    }

    override fun onLongPress(motionEvent: MotionEvent) {}

    override fun onFling( motionEvent: MotionEvent, motionEvent1: MotionEvent, v: Float, v1: Float): Boolean {
        return false
    }

    fun dailyAction(view: View?) {
        MonthViewActivity.selectedDay = LocalDate.of(CalendarUtils.actualDate!!.year, CalendarUtils.actualDate!!.month, CalendarUtils.actualDate!!.dayOfMonth)
        startActivity(Intent(this, DailyViewActivity::class.java))
    }

    fun previousWeekAction(view: View?) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate!!.minusWeeks(1)
        setWeekView()
    }

    fun nextWeekAction(view: View?) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate!!.plusWeeks(1)
        setWeekView()
    }

    fun monthlyAction(view: android.view.View) {
        startActivity(Intent(this, MonthViewActivity::class.java))
    }

    fun backHome(view: android.view.View) {
        startActivity(Intent(this, MainActivity::class.java))
    }
}