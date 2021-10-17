package codewithcal.au.calendarappexample

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class DailyViewActivity : AppCompatActivity() {
    private var dayOfWeek: TextView? = null
    private var dayAndMonth: TextView? = null
    private var rv: RecyclerView? = null
    private var classrooms = ArrayList<Classroom>()
    private var noHayClase: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_view)
        initWidgets()
        setDayView()
        setClassrooms()
        setRVView()
    }

    private fun initWidgets() {
        dayOfWeek = findViewById(R.id.DayOfWeek)
        dayAndMonth = findViewById(R.id.MonthAndDay)
        rv = findViewById(R.id.rv)
        noHayClase = findViewById(R.id.NoHayClase)
    }

    private fun setClassrooms(){

        noHayClase!!.visibility = View.GONE

        val NPIT = Classroom("NPI (Teoría)", getString(R.string.NPIT), R.drawable.npi)
        val NPIP = Classroom("NPI (Prácticas)", getString(R.string.NPIP), R.drawable.npi)
        val RSCT = Classroom("RSC (Teoría)", getString(R.string.RSCT), R.drawable.rsc)
        val RSCP = Classroom("RSC (Prácticas)", getString(R.string.RSCP), R.drawable.rsc)
        val VCT = Classroom("VC (Teoría)", getString(R.string.VCT), R.drawable.vc)
        val VCP = Classroom("VC (Prácticas)", getString(R.string.VCP), R.drawable.vc)
        val PLT = Classroom("PL (Teoría)", getString(R.string.PLT), R.drawable.pl)
        val PLP = Classroom("PL (Prácticas)", getString(R.string.PLP), R.drawable.pl)

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
                rv!!.visibility = View.GONE
                noHayClase!!.visibility = View.VISIBLE
            }
        }
    }

    private fun setRVView(){
        rv!!.setHasFixedSize(true)
        rv!!.layoutManager = LinearLayoutManager(this)
        val adapter = ClassroomAdapter(classrooms)
        rv!!.adapter = adapter
    }

    private fun setDayView() {
        dayOfWeek!!.text = MonthViewActivity.selectedDay?.dayOfWeek!!.name
        dayAndMonth!!.text = MonthViewActivity.selectedDay!!.month.toString() + "/" + MonthViewActivity.selectedDay!!.dayOfMonth
    }

    fun DailyToWeekly(view: android.view.View) {
        startActivity(Intent(this, WeekViewActivity::class.java))
    }
}