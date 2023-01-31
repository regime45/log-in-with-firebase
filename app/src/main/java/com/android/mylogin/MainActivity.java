package com.android.mylogin;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends Activity {
    private TimePicker timePicker1;
    private TextView time;
    EditText date, task, oras, title;
    private Calendar calendar;
    private String format = "";

    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    Button submit, my_task;
    TimePickerDialog picker;
    DatePickerDialog datePickerDialog;
    String ss ;
    TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
        //time = (TextView) findViewById(R.id.textView1);
        calendar = Calendar.getInstance();
        submit = findViewById(R.id.set_button);
        my_task = findViewById(R.id.reminder);
        date= findViewById(R.id.date);
        task= findViewById(R.id.task);
        oras= findViewById(R.id.times);
        title= findViewById(R.id.title);

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });


        //int hour = calendar.get(Calendar.HOUR_OF_DAY);
        //int min = calendar.get(Calendar.MINUTE);

        //showTime(hour, min);


        // checking time if
        /*
         String timer = "1674617040000";
         Long timers =Long.parseLong(timer);

        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(timers);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
               "hh:mm: a");
        String  dateforrow = dateFormat.format(cal1.getTime());
          Toast.makeText(MainActivity.this,dateforrow, Toast.LENGTH_SHORT).show();

         */

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String toSpeak = "Title"+title.getText().toString()+"Task Description"+task.getText().toString()+"Date on"+date.getText().toString()+
                        "time"+oras.getText().toString()+"succesfully save";
                //Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();

                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                //long time;
               //String tasks = task.getText().toString();
                //String dates = date.getText().toString();

                //int hour = timePicker1.getCurrentHour();
                //int min = timePicker1.getCurrentMinute();

                //calendar.set(Calendar.HOUR_OF_DAY, timePicker1.getCurrentHour());
                //calendar.set(Calendar.MINUTE, timePicker1.getCurrentMinute());

                // using intent i have class AlarmReceiver class which inherits
                // BroadcastReceiver


                //time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));

                //Calendar cal1 = Calendar.getInstance();
                //cal1.setTimeInMillis(time);
                //SimpleDateFormat dateFormat = new SimpleDateFormat(
                //        "hh:mm: a");
                //String  dateforrow = dateFormat.format(cal1.getTime());
              //  Toast.makeText(MainActivity.this,String.valueOf(time), Toast.LENGTH_SHORT).show();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("remind_app").child("task");
                DatabaseReference newChildRef = myRef.push();
                String key = newChildRef.getKey();
                if (key != null) {
                    myRef.child(key).child("task").setValue( task.getText().toString());
                    myRef.child(key).child("date").setValue( date.getText().toString());
                    myRef.child(key).child("title").setValue( title.getText().toString());
                   // myRef.child(key).child("alarm_time").setValue( String.valueOf(time));
                    myRef.child(key).child("alarm_time").setValue( ss);
                    myRef.child(key).child("time").setValue(oras.getText().toString());
                    Toast.makeText(MainActivity.this, "Successfully save", Toast.LENGTH_SHORT).show();
                }



                //Toast.makeText(MainActivity.this, dateforrow, Toast.LENGTH_SHORT).show();

                //showTime(hour, min);

            }

        });

        my_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, my_task.class);
                startActivity(intent);

            }

        });



        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        oras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog






                picker = new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                             oras.setText(sHour + ":" + sMinute);
                                dialogtimer(sHour, sMinute);

                                cldr.set(Calendar.HOUR_OF_DAY,  sHour );
                                cldr.set(Calendar.MINUTE,  sMinute);
                                long timeskip;
                                timeskip = (cldr.getTimeInMillis() - (cldr.getTimeInMillis() % 60000));

                                ss = String.valueOf(timeskip);



                            }
                        }, hour, minutes, false);



                picker.show();


            }
        });






    }

    private void dialogtimer(int sHour, int sMinutes) {
        if (sHour == 0) {
            sHour += 12;
            format = "AM";
        } else if (sHour == 12) {
            format = "PM";
        } else if (sHour> 12) {
            sHour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        oras.setText(new StringBuilder().append(sHour).append(" : ").append(sMinutes)
                .append(" ").append(format));
    }

/*
    public void showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        time.setText(new StringBuilder().append(hour).append(" : ").append(min)
                .append(" ").append(format));
    }

 */
public void onPause(){
    if(t1 !=null){
        t1.stop();
        t1.shutdown();
    }
    super.onPause();
}




}