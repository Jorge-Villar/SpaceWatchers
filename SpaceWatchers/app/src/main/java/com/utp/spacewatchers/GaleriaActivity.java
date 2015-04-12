package com.utp.spacewatchers;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;


public class GaleriaActivity extends ActionBarActivity {
    GridView gridView;

    int direccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);
        gridView = (GridView) findViewById( R.id.gridView1 );
        gridView.setAdapter( new GridViewImageAdapter(this, 1) );

        direccion=0;

    }
    public void votados (View v)
    {
        direccion=1;
        gridView.setAdapter( new GridViewImageAdapter(this, 1) );
    }
    public void novotados(View n)
    {
        direccion=0;
        gridView.setAdapter( new GridViewImageAdapter(this, 2) );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_galeria, menu);
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
