package com.android.mylogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    Button b1,b2;
    EditText ed1,ed2;

    TextView tx1, failed;
    int counter = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        b1 = (Button)findViewById(R.id.button);
        ed1 = (EditText)findViewById(R.id.editText);
        ed2 = (EditText)findViewById(R.id.editText2);

        b2 = (Button)findViewById(R.id.button2);
        tx1 = (TextView)findViewById(R.id.textView3);
        failed = (TextView)findViewById(R.id.login_failed);
        tx1.setVisibility(View.GONE);

        //write credentials to firebase  database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("log_in");
        DatabaseReference newChildRef = myRef.push();
        String key = newChildRef.getKey();
        if (key != null) {

            myRef.child("username").setValue("SuperAdmin");
            myRef.child("password").setValue("SuperAdmin");
        }


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //offline mode
                          /*
                if(ed1.getText().toString().equals("carl") &&
                        ed2.getText().toString().equals("carl_pass")) {

                    Toast.makeText(getApplicationContext(),
                            "Redirecting...",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,home.class);
                    startActivity(intent);}

                else{
                    //  Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
                    failed.setText("Invalid Username or Password");

                    tx1.setVisibility(View.VISIBLE);
                    tx1.setBackgroundColor(Color.RED);
                    counter--;
                    tx1.setText(Integer.toString(counter));

                    if (counter == 0) {
                        b1.setEnabled(false);
                    }}

                           */


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                //creating reference
                DatabaseReference dbRef = database.getReference("log_in");

                dbRef .addListenerForSingleValueEvent(new ValueEventListener()  {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {

                        //retreiving credentials from firebase database
                        String username = dataSnapshot.child("username").getValue().toString();
                        String password = dataSnapshot.child("password").getValue().toString();

                        // matching if inputted data is existed
                        if(ed1.getText().toString().equals(username) &&
                                ed2.getText().toString().equals(password)) {

                            Toast.makeText(getApplicationContext(),
                                    "Redirecting...",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, my_task.class);
                            startActivity(intent);

                        }
                        else{
                            //  Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
                            failed.setText("Invalid Username or Password");

                            tx1.setVisibility(View.VISIBLE);
                            tx1.setBackgroundColor(Color.RED);
                            counter--;
                            tx1.setText(Integer.toString(counter));

                            if (counter == 0) {
                                b1.setEnabled(false);
                            }}




                            //String radius = childSnapshot.child("Radius").getValue().toString();


                        //Toast.makeText(getApplicationContext(), username,Toast.LENGTH_SHORT).show();



                    }

                    @Override
                    public void onCancelled( DatabaseError databaseError) {

                    }

                });







            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}