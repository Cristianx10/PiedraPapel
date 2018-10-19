package com.example.ecosistemas.piedrapapel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public Button btn_piedra;
    public Button btn_papel;
    public Button btn_tijera;
    public int ID;
    public String identificame;

    ClienteUnicast server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        identificame = "identificame:";

        btn_papel = findViewById(R.id.btn_papel);
        btn_piedra = findViewById(R.id.btn_piedra);
        btn_tijera = findViewById(R.id.btn_tijera);


        btn_papel.setOnClickListener(this);
        btn_piedra.setOnClickListener(this);
        btn_tijera.setOnClickListener(this);


        server = new ClienteUnicast();
        server.start();

    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_papel:
                server.enviar(identificame + "accion:" + ID+ ":1");
                break;
            case R.id.btn_piedra:
                server.enviar(identificame + "accion:" + ID+ ":2");
                break;
            case R.id.btn_tijera:
                server.enviar(identificame + "accion:" + ID+":3");
                break;


        }
        identificame = "";
    }
}
