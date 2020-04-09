package com.example.smartparking;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import hivatec.ir.easywebservice.Callback;
import hivatec.ir.easywebservice.EasyWebservice;
import hivatec.ir.easywebservice.Method;


public class Process_activity extends Activity {

    TextView total_libres;
    TextView estado_parqueadero1;
    TextView estado_parqueadero2;
    ImageView refrescar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        total_libres = (TextView) findViewById(R.id.indicador_disponibilidad);
        estado_parqueadero1 = (TextView) findViewById(R.id.indicador_parqueadero1);
        estado_parqueadero2 = (TextView) findViewById(R.id.indicador_parqueadero2);
        refrescar = (ImageView) findViewById(R.id.refrescar);
        refrescar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // realizar proceso de obtener datos y actualizar pantalla
                new EasyWebservice("https://things.ubidots.com/api/v1.6/variables/5e49836d0ff4c310d34c1bff")
                        .method(Method.GET) //default
                        .addHeader("x-Auth-Token", getString(R.string.default_token))
                        .call(new Callback.A<LastValue>("last_value") { //should map response params
                            @Override
                            public void onSuccess(LastValue person) {
                                if(person.value.equals("0")){
                                    estado_parqueadero1.setText(R.string.disponible);
                                }else{
                                    estado_parqueadero1.setText(R.string.ocupado);
                                }
                            }

                            @Override
                            public void onError(String error) {

                                //if any error encountered
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
                                }else{
                                    estado_parqueadero2.setText(R.string.ocupado);
                                }
                            }

                            @Override
                            public void onError(String error) {

                                //if any error encountered
                            }
                        });

            }
        });
    }
}

class LastValue {
    String value;
}

