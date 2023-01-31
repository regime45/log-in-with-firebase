package com.android.mylogin;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;




public class my_adapter extends FirebaseRecyclerAdapter<model, my_adapter.myviewholder> {

    private Context context;
    private Intent intent;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;


    public my_adapter( FirebaseRecyclerOptions<model> options)
    {
        super(options);


    }


    @Override
    public myviewholder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler, parent, false);
        return new myviewholder(view);
    }


    public static class myviewholder extends RecyclerView.ViewHolder {
        ImageView img;
        ImageView edit, delete;
        TextView name,date ,time, title;
        Button call;

        public myviewholder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.task_message);
            date= (TextView) itemView.findViewById(R.id.date);
            time = (TextView) itemView.findViewById(R.id.time);
            title = (TextView) itemView.findViewById(R.id.title);

            edit=(ImageView)itemView.findViewById(R.id.editicon);
            delete=(ImageView)itemView.findViewById(R.id.deleteicon);

        }
    }


    @Override
    protected void onBindViewHolder( final myviewholder holder, final int position,  final model model) {
        context = holder.itemView.getContext();
        holder.name.setText(model.gettask());
        holder.date.setText(model.getdate());
        holder.time.setText(model.gettime());
        holder.title.setText(model.gettitle());
        String ss= String.valueOf(model.getalarm_time());
        long times = Long.parseLong(ss);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);


        //Toast.makeText(context,ss, Toast.LENGTH_SHORT).show();

        // using intent i have class AlarmReceiver class which inherits
        // BroadcastReceiver
         intent = new Intent(context, AlarmReceiver.class);

        // we call broadcast using pendingIntent
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        if (System.currentTimeMillis() > times) {
            // setting time as AM and PM
            if (Calendar.AM_PM == 0)
                times =times + (1000 * 60 * 60 * 12);
            else
                times = times + (1000 * 60 * 60 * 24);
        }
        // Alarm rings continuously until toggle button is turned off
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, times, 100, pendingIntent);
        // alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (time * 1000), pendingIntent);



        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.delete.getContext());
                builder.setTitle("Delete Panel");
                builder.setMessage("Delete...?");
                // Intent intent = new Intent(view.getContext(), Fire_Activity.class);


                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // String time    =  model.getlatitude();
                        //String  lastime = model.getlongitude();

                        // Long times = Long.parseLong( time);
                        // Long lastimes = Long.parseLong( lastime);

                        //if (times > lastimes){
                        FirebaseDatabase.getInstance().getReference("remind_app").child("task")
                                .child(getRef(position).getKey()).removeValue();

                        // String ss = getRef(position).getKey();
                        //Toast.makeText(context, model.getlatitude()  , Toast.LENGTH_LONG).show();
                    }//}
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });





    }


}
