package com.example.countdowntimer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.countdowntimer.adapter.Adapter;
import com.example.countdowntimer.model.Event;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText eventName;
    Button datePicker, addCounter;
    SwitchCompat alarmSwitch;
    TimePicker timePicker;
    ArrayList<Event> eventArrayList;
    Calendar newDate;
    TextView selectedDate;
    Adapter adapter;
    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //just findviewsbyid
        findViews();
        //load eventArraylist from sharedpreferences
        loadData();
        //set clock for 24h mode and starting time at 12:00
        setTimePicker();
        //create out adapter to manage recyclerview
        adapter = new Adapter(eventArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        instance = this;
        

    }

    public void newCountdown(View view) {
        String name = eventName.getText().toString();
        if(name.isEmpty())
        {
            new AlertDialog.Builder(this)
                    .setTitle("Warning")
                    .setMessage("Please specyfiy name")
                    .setNegativeButton("ok", null)
                    .show();
            eventName.setHintTextColor(getResources().getColor(R.color.colorRed));

        }
        if(newDate == null){
            new AlertDialog.Builder(this)
                    .setTitle("Warning")
                    .setMessage("Please specyfiy date")
                    .setNegativeButton("ok", null)
                    .show();
            datePicker.setTextColor(getResources().getColor(R.color.colorRed));
        }
        if(!name.isEmpty() && newDate != null){
            newDate.getTime();
            Event event = new Event(name, newDate.getTime());
            eventArrayList.add(eventArrayList.size(), event);
            adapter.notifyItemInserted(eventArrayList.size());
            saveData();
            hideKeyboard(MainActivity.this);
            eventName.setHintTextColor(getResources().getColor(R.color.black));
            datePicker.setTextColor(getResources().getColor(R.color.colorWhite));

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void selectDate(View view) {
        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog startTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                newDate = Calendar.getInstance();
                newDate.set(y,m,d);
                selectedDate.setText(d + "-" +m + "-" + y);
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        startTime.show();
    }


    private void findViews() {
        recyclerView = findViewById(R.id.recyclerView);
        eventName = findViewById(R.id.name);
        datePicker = findViewById(R.id.datePicker);
        addCounter = findViewById(R.id.addCounter);
        alarmSwitch = findViewById(R.id.alarmSwitch);
        timePicker = findViewById(R.id.timePicker1);
        selectedDate = findViewById(R.id.selectedDate);
        eventArrayList = new ArrayList<>();
    }

    private void setTimePicker() {
        timePicker.setIs24HourView(true);
        timePicker.setHour(12);
        timePicker.setMinute(0);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("List storage", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        //put list of events in here
        String json = gson.toJson(eventArrayList);
        editor.putString("Events list", json);
        editor.apply();
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("List storage", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Events list", null);
        Type type = new TypeToken<ArrayList<Event>>() {}.getType();
        eventArrayList = gson.fromJson(json, type);
        if(eventArrayList == null)
        {
            eventArrayList = new ArrayList<Event>();
        }
    }

    public static MainActivity getInstance() {
        return instance;
    }


}