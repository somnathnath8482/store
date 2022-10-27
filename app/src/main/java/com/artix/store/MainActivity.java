package com.artix.store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.artix.store.MyView.PiData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<PiData>  piData = new ArrayList<>();
        piData.add(new PiData(1,"One",5));
        piData.add(new PiData(2,"two",15));
        piData.add(new PiData(3,"three",75));
        piData.add(new PiData(4,"four",55));
        piData.add(new PiData(5,"five",35));
        piData.add(new PiData(6,"six",11));
        piData.add(new PiData(7,"seven",44));
        piData.add(new PiData(8,"eight",17));

        startActivity(new Intent(this,MainActivity2.class
        ));
    }
}