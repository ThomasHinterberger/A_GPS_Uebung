package com.example.a_gps_c_uebung.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.a_gps_c_uebung.R;

import java.util.ArrayList;

public class Gps_adapter extends ArrayAdapter<String> {
    int mResouce;
    private final Context mContext;

    public Gps_adapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        mContext = context;
        mResouce = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View converView, @NonNull ViewGroup parent) {
        String a = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        converView = inflater.inflate(mResouce, parent, false);
        TextView tx = converView.findViewById(R.id.textViewItem);
        tx.setText(a);
        return converView;

    }
}
