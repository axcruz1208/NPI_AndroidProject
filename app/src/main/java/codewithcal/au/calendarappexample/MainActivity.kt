package codewithcal.au.calendarappexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CalendarUtils.selectedDate = LocalDate.now()
        CalendarUtils.actualDate = LocalDate.now()
    }

    fun calendarAccess(view: android.view.View) {
        MonthViewActivity.selectedDay = LocalDate.of(CalendarUtils.actualDate!!.year, CalendarUtils.actualDate!!.month, CalendarUtils.actualDate!!.dayOfMonth)
        startActivity(Intent(this, DailyViewActivity::class.java))
    }

}