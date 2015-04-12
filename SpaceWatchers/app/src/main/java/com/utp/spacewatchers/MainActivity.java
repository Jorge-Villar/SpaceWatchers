package com.utp.spacewatchers;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends ActionBarActivity {
    private long SPLASH_DELAY = 6000;//6 SEGUNDO
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent intent;
        if(leerIdioma("datos").equals("0")){
            intent = new Intent(getApplicationContext(), IniciarActivity.class);
        }else{
            intent = new Intent(getApplicationContext(), GaleriaActivity.class);
        }

        TimerTask task = new TimerTask(){
            public void run (){
                //Intent mainIntent = new Intent(getApplicationContext(),
                //        MainActivity.class);

                startActivity(intent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task,SPLASH_DELAY);
    }


    public String leerIdioma(String file){
        String resp="";
        try{
            FileInputStream f = openFileInput(file);
            BufferedReader reader  = new BufferedReader(new InputStreamReader(f));
            String texto;
            do{
                texto=reader.readLine();
                if(texto!=null){
                    resp = texto;
                }
            }while(texto!= null);
            //Toast.makeText(getApplicationContext(), "leer = "+resp, Toast.LENGTH_SHORT).show();
            reader.close();
            f.close();
            return resp;
        }catch (Exception ex){
            //Toast.makeText(getApplicationContext(), "Error leer = "+ex.toString(), Toast.LENGTH_SHORT).show();
            return "0";
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
