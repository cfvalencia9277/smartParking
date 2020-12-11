package com.example.smartparking;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.Instant;
import java.util.Date;

import hivatec.ir.easywebservice.Callback;
import hivatec.ir.easywebservice.EasyWebservice;
import hivatec.ir.easywebservice.Method;


public class Process_activity extends AppCompatActivity {

    TextView total_libres;
    TextView estado_parqueadero1;
    TextView estado_parqueadero2;
    ImageView refrescar;
    TextView timepo_actualizado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        total_libres = (TextView) findViewById(R.id.indicador_disponibilidad);
        estado_parqueadero1 = (TextView) findViewById(R.id.indicador_parqueadero1);
        estado_parqueadero2 = (TextView) findViewById(R.id.indicador_parqueadero2);
        timepo_actualizado = (TextView) findViewById(R.id.tiempo_actualizado);
        refrescar = (ImageView) findViewById(R.id.refrescar);
        timepo_actualizado.setText("Actualizado: "+Date.from(Instant.now()).toString());
        refrescar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Actualizando Datos", Toast.LENGTH_SHORT).show();
                // realizar proceso de obtener datos y actualizar pantalla
                new EasyWebservice("https://things.ubidots.com/api/v1.6/variables/5e49836d0ff4c310d34c1bff")
                        .method(Method.GET) //default
                        .addHeader("x-Auth-Token", getString(R.string.default_token))
                        .call(new Callback.A<LastValue>("last_value") { //should map response params
                            @Override
                            public void onSuccess(LastValue person) {
                                timepo_actualizado.setText("Actualizado: "+Date.from(Instant.now()).toString());
                                if(person.value.equals("0")){
                                    estado_parqueadero1.setText(R.string.disponible);
                                    determineTotalParkingsAvailable((String) total_libres.getText(),false);
                                }else{
                                    estado_parqueadero1.setText(R.string.ocupado);
                                    determineTotalParkingsAvailable((String) total_libres.getText(),true);
                                }
                            }

                            @Override
                            public void onError(String error) {

                                //if any error encountered
                                Toast.makeText(getApplicationContext(),"Error al Conectar Parqueadero 1", Toast.LENGTH_SHORT).show();
                            }
                        });

                new EasyWebservice("https://things.ubidots.com/api/v1.6/variables/5e4987ce1d84720c7a36e18a")
                        .method(Method.GET) //default
                        .addHeader("x-Auth-Token", getString(R.string.default_token))
                        .call(new Callback.A<LastValue>("last_value") { //should map response params
                            @Override
                            public void onSuccess(LastValue person) {
                                if(person.value.equals("0")){
                                    estado_parqueadero2.setText(R.string.disponible);
                                    determineTotalParkingsAvailable((String) total_libres.getText(),false);
                                }else{
                                    estado_parqueadero2.setText(R.string.ocupado);
                                    determineTotalParkingsAvailable((String) total_libres.getText(),true);
                                }
                            }

                            @Override
                            public void onError(String error) {
                                //if any error encountered
                                Toast.makeText(getApplicationContext(),"Error al Conectar Parqueadero 2", Toast.LENGTH_SHORT).show();

                            }
                        });

            }
        });
    }

    private String determineTotalParkingsAvailable(String currentValue, boolean isAdd){
        if(currentValue.equals(getString(R.string.cerodedos))){
            if(isAdd){
                return getString(R.string.cerodedos);
            }else{
                return getString(R.string.unodedos);
            }
        }
        else if(currentValue.equals(getString(R.string.unodedos))){
            if(isAdd){
                return getString(R.string.cerodedos);
            }else{
                return getString(R.string.dosdedos);
            }
        }
        else if(currentValue.equals(getString(R.string.dosdedos))){
            if(isAdd){
                return getString(R.string.unodedos);
            }else{
                return getString(R.string.dosdedos);
            }
        }
        else {
            return getString(R.string.dosdedos);
        }

    }

}

class LastValue {
    String value;
}

