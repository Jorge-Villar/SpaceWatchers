package com.utp.spacewatchers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class TagearActivity extends ActionBarActivity {

    Button load_img;
    ImageButton btnGood,btnBad;
    ImageView img;
    Bitmap bitmap;
    ProgressDialog pDialog;
    int estado;
    String idfoto;
    String resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagear);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        img = (ImageView)findViewById(R.id.imageView);
        new LoadImage().execute("http://10.68.2.254/spaceapp/img/capturas/img_01.png");
        btnGood =(ImageButton)findViewById(R.id.imgGood  );
        btnBad =(ImageButton)findViewById(R.id.imgBad  );
        estado=0;
        idfoto="" ;
        resultado ="";
        btnGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estado =1;
            }
        });
        btnBad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estado=0;
            }
        });
    }

    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(TagearActivity.this);
            pDialog.setMessage("Loading Image ....");
            pDialog.show();
        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        protected void onPostExecute(Bitmap image) {
            if(image != null){
                img.setImageBitmap(image);
                pDialog.dismiss();
            }else{
                pDialog.dismiss();
                Toast.makeText(TagearActivity.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private boolean insertar(){
        HttpClient httpClient;
        List<NameValuePair> nameValuePairs;
        HttpPost httpPost;
        httpClient = new DefaultHttpClient();

        httpPost = new HttpPost("http://10.68.2.254/spaceapp/guardartags.php");

        nameValuePairs = new ArrayList<NameValuePair>(4);
        nameValuePairs.add(new BasicNameValuePair("idfoto","img_1.png" ));
        nameValuePairs.add(new BasicNameValuePair("iduser", leer("datos") ));
        nameValuePairs.add(new BasicNameValuePair("estado",String.valueOf(estado)));


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


    public String leer(String file){
        String resp="";
        int primero =0;
        try{
            FileInputStream f = openFileInput(file);
            BufferedReader reader  = new BufferedReader(new InputStreamReader(f));
            String texto;
            do{
                texto=reader.readLine();
                if(texto!=null && primero ==0){
                    resp = texto;
                    primero++;
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
                        //obtenerDatos();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tagear, menu);
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
