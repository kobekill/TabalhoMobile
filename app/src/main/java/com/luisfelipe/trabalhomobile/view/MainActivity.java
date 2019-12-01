package com.luisfelipe.trabalhomobile.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.luisfelipe.trabalhomobile.R;
import com.luisfelipe.trabalhomobile.control.Control;

public class MainActivity extends AppCompatActivity {

        Control control;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
control = new Control(this);
    }
public void salvar(View v){
        control.salvarAction();
}

}
