package com.example.dm2.ficheros;

import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    EditText txtContenido;
    Button btnAddInt,btnLeeInt,btnDelInt, btnAddExt, btnLeeExt,btnDelExt, btnLeeRec;
    TextView lblSalida;
    boolean sdDisponible = false;
    boolean sdAccesoEscritura= false;
    File ruta_sd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Comprobamos el estado de la memoria exterena
        String estado= Environment.getExternalStorageState();
        Log.i("Memoria", estado);
        if (estado.equals(Environment.MEDIA_MOUNTED)) {
            ruta_sd = Environment.getExternalStorageDirectory();
            sdDisponible = true;
            sdAccesoEscritura = true;
        }
        else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            sdDisponible = true;
            sdAccesoEscritura = false;
        }
        else {
            sdDisponible = false;
            sdAccesoEscritura = false;
        }


        lblSalida=(TextView)findViewById(R.id.lblSalida);

        txtContenido=(EditText)findViewById(R.id.txtContenido);

        btnAddInt=(Button)findViewById(R.id.btnAddInterno);
        btnLeeInt=(Button)findViewById(R.id.btnLeeInt);
        btnDelInt=(Button)findViewById(R.id.btnDelInt);

        btnAddExt=(Button)findViewById(R.id.btnAddExterno);
        btnLeeExt=(Button)findViewById(R.id.btnLeeExt);
        btnDelExt=(Button)findViewById(R.id.btnDelExt);

        btnLeeRec=(Button)findViewById(R.id.btnLeeRecurso);

        btnLeeRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Resources res = getResources();
                    InputStream is = res.openRawResource(R.raw.raw);
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String linea = br.readLine();
                    while(linea!=null){
                        lblSalida.append(linea+"\n");
                        linea=br.readLine();
                    }
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnAddExt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sdDisponible){
                    try {
                        String text=txtContenido.getText().toString();
                        File f = new File (ruta_sd.getAbsolutePath(),"textSD1.txt");
                        OutputStreamWriter osw =new OutputStreamWriter(new FileOutputStream(f));
                        osw.write(text+"\n");
                        osw.close();
                    }
                    catch (Exception ex) {
                        Log.e ("Ficheros", "Error al escribir fichero en tarjeta SD");
                    }

                }

            }
        });

        btnLeeExt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sdDisponible){
                    try {
                        File f = new File (ruta_sd.getAbsolutePath(),"textSD1.txt");
                        BufferedReader br =new BufferedReader(new FileReader(f));
                        String text = br.readLine();
                        br.close();
                        lblSalida.setText("El contenido del fichero es: " + text);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btnDelExt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File f = new File("storage/sdcard/textSD1.txt");
                f.delete();
            }
        });

        btnAddInt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String texto=txtContenido.getText().toString();
                    if(!texto.equalsIgnoreCase("")) {
                        OutputStreamWriter osw = new OutputStreamWriter(openFileOutput("textoEj1.txt", Context.MODE_PRIVATE));
                        osw.write(texto);
                        osw.close();
                    }
                }
                catch (Exception e) {
                    Log.e ("Ficheros", "ERROR!! al escribir fichero a memoria interna");
                }
            }
        });


        btnLeeInt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    BufferedReader br =new BufferedReader(new InputStreamReader(openFileInput("textoEj1.txt")));
                    String texto = br.readLine();
                    br.close();
                    lblSalida.setText("El contenido del fichero es: " + texto);
                }
                catch (Exception ex){
                    Log.e("Ficheros", "Error al leer fichero desde memoria interna");
                }
            }
        });

        btnDelInt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File f = new File("/data/data/com.example.dm2.ficheros/files/textoEj1.txt");
                f.delete();
            }
        });


    }
}
