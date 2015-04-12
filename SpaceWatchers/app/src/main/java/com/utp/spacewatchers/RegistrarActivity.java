package com.utp.spacewatchers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class RegistrarActivity extends ActionBarActivity {

    EditText txtuser, txtcorreo,txtpw, txtname;
    Button btnVolver,btnGuardar;
    String resultado;
    int resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        txtuser=(EditText) findViewById(R.id.txtid);
        txtcorreo=(EditText)findViewById(R.id.txtcorreo);
        txtpw=(EditText)findViewById(R.id.txtpassword);
        txtname=(EditText) findViewById(R.id.txtname);
        btnVolver=(Button) findViewById(R.id.btnVolver);
        btnGuardar=(Button) findViewById(R.id.btnGuardar);
        resultado = "";
        resp = 0;

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!txtpw.getText().toString().trim().equalsIgnoreCase("") ||
                        !txtcorreo.getText().toString().trim().equalsIgnoreCase("")) {
                    new Insertar(RegistrarActivity.this).execute();
                } else {
                    Toast.makeText(getApplicationContext(), "Uno de los campos esta vacio", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registrar, menu);
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


    private boolean obtenerDatos(){
        String data=resultado;
        if(!data.equalsIgnoreCase("")){
            JSONObject json;
            try{
                json = new JSONObject(data);
                resp = json.getInt("personas");
                seleccionarMetodo(resp);
                Toast.makeText(getApplicationContext(), String.valueOf(resp), Toast.LENGTH_LONG).show();
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }
    private void seleccionarMetodo(int valor){
        if (valor > 0){
            String parametros = txtcorreo.getText().toString()+ "\n"+txtpw.getText().toString();
            escribir("datos", parametros);

            //aqui se va a galeria layout
        }else{
            Toast.makeText(getApplicationContext(), "error: El usuario existe", Toast.LENGTH_LONG).show();
        }
    }

    public boolean escribir (String file, String datos){
        FileOutputStream fos;
        try{
            fos = openFileOutput(file, Context.MODE_PRIVATE);
            fos.write(datos.getBytes());
            fos.close();
            Intent claseGaleria= new Intent(getApplicationContext(), GaleriaActivity.class);
            startActivity(claseGaleria);
            return true;
        }catch (FileNotFoundException ex){
            return false;
        }
        catch (IOException ex){
            return false;
        }
    }
    //clase obligtatoria para api superior a 3.0
    private boolean insertar(){
        HttpClient httpClient;
        List<NameValuePair> nameValuePairs;
        HttpPost httpPost;
        httpClient = new DefaultHttpClient();
        httpPost = new HttpPost("http://10.68.2.254/spaceapp/registrarusuario.php");

        nameValuePairs = new ArrayList<NameValuePair>(4);
        nameValuePairs.add(new BasicNameValuePair("correo", txtcorreo.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("pw", txtpw.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("user", txtuser.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("nombre", txtname.getText().toString().trim()));

        HttpResponse response;

        try{
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            resultado = EntityUtils.toString(entity, "UTF-8");
            return true;
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    };

    //clase obligtatoria para api superior a 3.0
    class Insertar extends AsyncTask<String, String, String> {
        private Activity context;
        Insertar(Activity context){
            this.context=context;
        }

        @Override
        protected String doInBackground(String... strings) {
            if(insertar())
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Sentencia exitosa", Toast.LENGTH_LONG).show();
                        obtenerDatos();
                    }
                });
            else
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Error: sentencia fallida", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }
}