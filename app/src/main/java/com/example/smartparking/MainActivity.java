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
                setUtilsFromView();

                boton_validar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String entry = cedula.getText().toString();
                        if(getString(R.string.cedulas).contains(entry)){
                            Intent myIntent = new Intent(MainActivity.this, Process_activity.class);
                            MainActivity.this.startActivity(myIntent);
                        }else {
                            cedula.clearComposingText();

                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getApplicationContext());
                            builder1.setMessage("Cedula no coincide intente de nuevo");
                            builder1.setCancelable(false);
                            builder1.setPositiveButton(
                                    "Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                        }
                    }
                });
            }
        }.start();
    }

    public void setUtilsFromView(){
        boton_validar=(Button) findViewById(R.id.boton_validar);
        cedula = (EditText) findViewById(R.id.entrada_cedula);
    }
}
