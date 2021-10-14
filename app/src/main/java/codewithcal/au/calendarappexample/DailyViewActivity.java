package codewithcal.au.calendarappexample;

import static codewithcal.au.calendarappexample.CalendarUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.TextStyle;

public class DailyViewActivity extends AppCompatActivity {

    private TextView dayOfWeek;
    private TextView dayAndMonth;
    private Spinner clases;
    private ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_view);
        initWidgets();
        setDayView();
    }

    private void initWidgets()
    {
       dayOfWeek = findViewById(R.id.DayOfWeek);
       dayAndMonth = findViewById(R.id.MonthAndDay);
       clases = findViewById(R.id.spinner_clases);
       adapter =ArrayAdapter.createFromResource(this, R.array.Clases, android.R.layout.simple_spinner_item);
       adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
       clases.setAdapter(adapter);
    }

    private void setDayView(){
        dayOfWeek.setText(MainActivity.selectedDay.getDayOfWeek().name());
        dayAndMonth.setText(MainActivity.selectedDay.getMonth() + "/" + MainActivity.selectedDay.getDayOfMonth());
    }
}