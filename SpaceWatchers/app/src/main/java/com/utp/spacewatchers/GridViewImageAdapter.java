package com.utp.spacewatchers;
import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridViewImageAdapter extends BaseAdapter{

    private Context mContext;
    private ArrayList<Integer> listPhoto = new ArrayList<Integer>();

    /** Constructor de clase */
    public GridViewImageAdapter(Context c, int btn){
        mContext = c;
        //se cargan las miniaturas
        if(btn == 1)
        {
            listPhoto.add(R.drawable.a);
            listPhoto.add(R.drawable.b);
            listPhoto.add(R.drawable.c);
            listPhoto.add(R.drawable.d);
            listPhoto.add(R.drawable.e);
            listPhoto.add(R.drawable.f);
            listPhoto.add(R.drawable.g);
            listPhoto.add(R.drawable.h);
            listPhoto.add(R.drawable.j);
            listPhoto.add(R.drawable.k);
            listPhoto.add(R.drawable.l);
            listPhoto.add(R.drawable.a);
        }
        else if(btn == 2)
        {
            listPhoto.add(R.drawable.m);
            listPhoto.add(R.drawable.n);
            listPhoto.add(R.drawable.o);
            listPhoto.add(R.drawable.p);
            listPhoto.add(R.drawable.q);
            listPhoto.add(R.drawable.r);
            listPhoto.add(R.drawable.s);
            listPhoto.add(R.drawable.t);
        }
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listPhoto.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return listPhoto.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewgroup) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource( listPhoto.get(position) );
        imageView.setScaleType( ImageView.ScaleType.CENTER_CROP );
        imageView.setLayoutParams( new GridView.LayoutParams(200, 220) );
        return imageView;
    }

}
