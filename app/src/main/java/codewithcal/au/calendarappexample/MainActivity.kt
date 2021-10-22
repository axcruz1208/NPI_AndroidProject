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
     * En esta función se inicializa su actividad. Aquí es donde normalmente se llamará a setContentView para definir
     * la interfaz de usuario y usará findViewById para asignar los widgets a esa interfaz de usuario y poder programar con ellos.
     * @param savedInstanceState: Nos guarda el estado de la apicación
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
     * Función onClick del botón Horario que nos va a redirigir a la actividad DailyViewActivity
     */
    fun calendarAccess(view: View?) {
        MonthViewActivity.selectedDay = LocalDate.of(CalendarUtils.actualDate!!.year, CalendarUtils.actualDate!!.month, CalendarUtils.actualDate!!.dayOfMonth)
        startActivity(Intent(this, DailyViewActivity::class.java))
    }

    /**
     * Función que nos redirige a la actividad DailyViewActivity cuando agitamos el movil
     */
    override fun hearShake() {
        MonthViewActivity.selectedDay = LocalDate.of(CalendarUtils.actualDate!!.year, CalendarUtils.actualDate!!.month, CalendarUtils.actualDate!!.dayOfMonth)
        startActivity(Intent(this, DailyViewActivity::class.java))
    }

}