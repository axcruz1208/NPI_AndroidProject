package codewithcal.au.calendarappexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.seismic.ShakeDetector
import java.time.LocalDate
import android.hardware.SensorManager
import android.view.View


class MainActivity : AppCompatActivity(), ShakeDetector.Listener {

    /**
     * Función que nos genera un array con las casillas que están junto a un muro y les aplica un wallDanger de 10
     * @param savedInstanceState: Observation of the current state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val sd = ShakeDetector(this)
        sd.setSensitivity(11)
        sd.start(sensorManager)

        CalendarUtils.selectedDate = LocalDate.now()
        CalendarUtils.actualDate = LocalDate.now()
    }

    /**
     * Función que nos genera un array con las casillas que están junto a un muro y les aplica un wallDanger de 10
     */
    fun calendarAccess(view: View?) {
        MonthViewActivity.selectedDay = LocalDate.of(CalendarUtils.actualDate!!.year, CalendarUtils.actualDate!!.month, CalendarUtils.actualDate!!.dayOfMonth)
        startActivity(Intent(this, DailyViewActivity::class.java))
    }

    /**
     * Función que nos genera un array con las casillas que están junto a un muro y les aplica un wallDanger de 10
     */
    override fun hearShake() {
        MonthViewActivity.selectedDay = LocalDate.of(CalendarUtils.actualDate!!.year, CalendarUtils.actualDate!!.month, CalendarUtils.actualDate!!.dayOfMonth)
        startActivity(Intent(this, DailyViewActivity::class.java))
    }

}