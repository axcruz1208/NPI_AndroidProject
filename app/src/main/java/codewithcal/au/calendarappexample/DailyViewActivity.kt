package codewithcal.au.calendarappexample

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DailyViewActivity : AppCompatActivity() {
    private var dayOfWeek: TextView? = null
    private var dayAndMonth: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_view)
        initWidgets()
        setDayView()
    }

    private fun initWidgets() {
        dayOfWeek = findViewById(R.id.DayOfWeek)
        dayAndMonth = findViewById(R.id.MonthAndDay)
    }

    private fun setDayView() {
        dayOfWeek!!.text = MainActivity.selectedDay?.dayOfWeek!!.name
        dayAndMonth!!.text = MainActivity.selectedDay!!.month.toString() + "/" + MainActivity.selectedDay!!.dayOfMonth
    }
}