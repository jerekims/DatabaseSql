package com.example.jere.databasesql;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import data.DatabaseHandler;
import model.myWish;

public class MainActivity extends AppCompatActivity {
    private EditText mtitle;
    private EditText mdescription;
    private Button msubmit;
    private DatabaseHandler dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
    }

    public void setup(){
        dba = new DatabaseHandler(getApplicationContext());
        mtitle =(EditText)findViewById(R.id.wish_title);
        mdescription=(EditText)findViewById(R.id.wish_desc);
        msubmit=(Button)findViewById(R.id.btn_submit);
        msubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDb();
            }
        });
    }

    //submitting  values to the  database
    private void saveToDb(){
        myWish wish =new myWish();
        wish.setTitle(mtitle.getText().toString().trim());
        wish.setContent(mdescription.getText().toString().trim());
        dba.addwishes(wish);
        dba.close();

        //clear the text fields
        mtitle.setText("");
        mdescription.setText("");

        //displaying all the wishes fromdb
        Intent i =new Intent(getApplicationContext(),Display_Wishes.class);
        startActivity(i);
    }

}
