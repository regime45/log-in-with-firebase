package com.android.mylogin;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import com.android.mylogin.model;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class my_task extends AppCompatActivity {

    RecyclerView recview;
    my_adapter adapter;
    AlarmManager alarmManager;
    Button add;
    long timess;
    PendingIntent pendingIntent;
    TextView print ;

    FloatingActionButton fs;
    Switch simpleSwitch1;
    private DatabaseReference Db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task);
        setTitle("Search here..");

        recview = (RecyclerView) findViewById(R.id.recview);
        add = findViewById(R.id.add);

        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("remind_app").child("task"), model.class)
                        .build();

        adapter = new my_adapter(options);
        recview.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(my_task.this, MainActivity.class);
                startActivity(intent);



            }

        });



            }



    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();


    }



    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //calling search menu layout from resource layout @ menu
        getMenuInflater().inflate(R.menu.search_menu, menu);
        // get I.D
        MenuItem item = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) item.getActionView();
        //SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void processsearch(String s) {
        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("remind_app").child("task").orderByChild("title").startAt(s).endAt(s + "\uf8ff"), model.class)
                        .build();

        adapter = new my_adapter(options);
        adapter.startListening();
        recview.setAdapter(adapter);

    }

 */








}


