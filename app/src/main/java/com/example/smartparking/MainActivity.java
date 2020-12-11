package com.example.smartparking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Button boton_validar;
    EditText cedula;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_activity);
        new CountDownTimer(5000,1000){
            @Override
            public void onTick(long millisUntilFinished){}

            @Override
            public void onFinish(){
                //set the new Content of your activity
                MainActivity.this.setContentView(R.layout.login_activity);
                boton_validar=(Button) findViewById(R.id.boton_validar);
                cedula = (EditText) findViewById(R.id.entrada_cedula);

                boton_validar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String entry = cedula.getText().toString();
                        if(!getString(R.string.cedulas).contains(entry) || entry.equals("")){
                            cedula.setText("");
                            Toast.makeText(getApplicationContext(),"Cedula no coincide intente de nuevo", Toast.LENGTH_LONG).show();
                        }else {
                            Intent myIntent = new Intent(MainActivity.this, Process_activity.class);
                            MainActivity.this.startActivity(myIntent);
                        }
                    }
                });
            }
        }.start();
    }

}
