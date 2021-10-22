package codewithcal.au.calendarappexample

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.util.ArrayList

object CalendarUtils {

    //Día actual y seleccionado
    @JvmField
    var selectedDate: LocalDate? = null
    var actualDate: LocalDate? = null

    /**
     * Función que nos genera la disposición de las vistas del recyclerView dependiendo del mes en que
     * nos encontremos.
     * @param date: La fecha actual o la fecha seleccionada (fecha que cambia cuando cambias de mes en el calendario).
     */
    @JvmStatic
    fun daysInMonthArray(date: LocalDate?): ArrayList<LocalDate?> {
        val daysInMonthArray = ArrayList<LocalDate?>()
        val yearMonth = YearMonth.from(date) //Mes de la fecha proporcionada
        val daysInMonth = yearMonth.lengthOfMonth() //Días que tiene el mes
        val firstOfMonth = selectedDate!!.withDayOfMonth(1) //Día en que comienza el mes
        val dayOfWeek = firstOfMonth.dayOfWeek.value - 1 //Día de la semana en que comienza el mes
        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) //Si son días antes o después del mes no se muestran
                daysInMonthArray.add(null)
            else
                daysInMonthArray.add(LocalDate.of(selectedDate!!.year, selectedDate!!.month, i - dayOfWeek)
            )
        }
        return daysInMonthArray
    }

    /**
     * Función que dada una fecha muestra la semana a la que pertenece esa misma fecha
     * @param selectedDate: La fecha actual o la fecha seleccionada (fecha que cambia cuando cambias de semana en el calendario).
     */
    @JvmStatic
    fun daysInWeekArray(selectedDate: LocalDate): ArrayList<LocalDate?> {
        val days = ArrayList<LocalDate?>()
        var current = mondayForDate(selectedDate) //Obtenemos el lunes de esa semana
        val endDate = current!!.plusWeeks(1)
        while (current!!.isBefore(endDate)) { //Iteramos hasta añadir todos los días de la semana al Array
            days.add(current)
            current = current.plusDays(1)
        }
        return days
    }

    /**
     * Función que nos devuelve el LUNES de la semana a la que pertenece el día pasado como parámetro
     * para así tener una referencia de cuando empieza la semana.
     * @param Current: Es el día actual / seleccionado
     */
    private fun mondayForDate(Current: LocalDate): LocalDate? {
        var current = Current
        val oneWeekAgo = current.minusWeeks(1)

        //Vamos retrocediendo un día en la semana hasta que estemos en un LUNES
        while (current.isAfter(oneWeekAgo)) {
            if (current.dayOfWeek == DayOfWeek.MONDAY)
                return current
            current = current.minusDays(1)
        }
        return null
    }
}