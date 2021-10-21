package codewithcal.au.calendarappexample

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
     * Función que nos genera un array con las casillas que están junto a un muro y les aplica un wallDanger de 10
     * @param savedInstanceState: Observation of the current state.
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
     * Función que nos genera un array con las casillas que están junto a un muro y les aplica un wallDanger de 10
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
     * Función que nos genera un array con las casillas que están junto a un muro y les aplica un wallDanger de 10
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
                nextClass!!.visibility = View.GONE
                noHayClase!!.visibility = View.VISIBLE
                noHayClase2!!.visibility = View.VISIBLE
            }
        }

        //A mas cercano le asignamos la hora actual
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
     * Función que nos genera un array con las casillas que están junto a un muro y les aplica un wallDanger de 10
     */
    private fun setRVView(){
        rv!!.setHasFixedSize(true)
        rv!!.layoutManager = LinearLayoutManager(this)
        val adapter = ClassroomAdapter(classrooms)
        rv!!.adapter = adapter
    }

    /**
     * Función que nos genera un array con las casillas que están junto a un muro y les aplica un wallDanger de 10
     */
    private fun setDayView() {
        dayOfWeek!!.text = MonthViewActivity.selectedDay?.dayOfWeek!!.name
        dayAndMonth!!.text = MonthViewActivity.selectedDay!!.month.toString() + "/" + MonthViewActivity.selectedDay!!.dayOfMonth
    }

    /**
     * Función que nos genera un array con las casillas que están junto a un muro y les aplica un wallDanger de 10
     */
    fun DailyToWeekly(view: View?) {
        startActivity(Intent(this, WeekViewActivity::class.java))
    }

    /**
     * Función que nos genera un array con las casillas que están junto a un muro y les aplica un wallDanger de 10
     */
    fun backHome(view: View?) {
        startActivity(Intent(this, MainActivity::class.java))
    }
}