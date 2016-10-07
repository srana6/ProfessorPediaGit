package headfirst.com.projectapplication;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import android.text.format.DateFormat;
import java.util.Calendar;

import info.hoang8f.widget.FButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmFragment extends Fragment {

   // Button btnAlarm;

    public FButton btnAlarm;

    public Context context;
    public Context context1;
    public Calendar calendar;

    public EditText editTextDate;
    public EditText editTextTime;
    public EditText editTextNote;

    public String notes;
    public String datee;
    public String time;
    public String character;

    public AlarmFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity().getApplicationContext();
        context1=getActivity().getBaseContext();
        //---get current date and time---


        View view= inflater.inflate(R.layout.fragment_alarm, container, false);
        btnAlarm=(FButton) view.findViewById(R.id.btnSetAlarm);
        editTextDate=(EditText) view.findViewById(R.id.editTextDateID);
        editTextTime=(EditText) view.findViewById(R.id.editTextTimeID);
        editTextNote=(EditText) view.findViewById(R.id.editTextNotesID);



        btnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                character="-";

                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(context.ALARM_SERVICE);

                //---get current date and time---
                calendar = Calendar.getInstance();

                datee = editTextDate.getText().toString();
                time = editTextTime.getText().toString();
                if(datee.matches("")||time.matches("")) {
                    Toast.makeText(getContext(), "Please enter date and time in correct format", Toast.LENGTH_LONG).show();
                }else if(datee.contains(character)&&time.contains(character)){
                    Toast.makeText(context, time + "is a time", Toast.LENGTH_LONG).show();

                    String output[] = datee.split("-");
                    String year1 = output[0];
                    String month1 = output[1];
                    String day1 = output[2];

                    String output1[] = time.split("-");
                    String hour1 = output1[0];
                    String min1 = output1[1];

                    calendar.set(Calendar.YEAR, Integer.valueOf(year1));
                    calendar.set(Calendar.MONTH, Integer.valueOf(month1) - 1);
                    calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(day1));
                    calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour1));
                    calendar.set(Calendar.MINUTE, Integer.valueOf(min1));
                    calendar.set(Calendar.SECOND, 0);


                    notes = editTextNote.getText().toString();

                    editTextDate.setText(" ");
                    editTextTime.setText(" ");
                    editTextNote.setText(" ");

                    Intent i = new Intent("headfirst.com.projectapplication.DisplayNotification");

                    i.putExtra("NotifID", notes);

                    PendingIntent displayIntent = PendingIntent.getActivity(
                            context1, 0, i, 0);

                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), displayIntent);
                }
                else{
                    Toast.makeText(getContext(), "Please enter date and time in correct format", Toast.LENGTH_LONG).show();
                }

            }
        });
        return view;
    }
}
