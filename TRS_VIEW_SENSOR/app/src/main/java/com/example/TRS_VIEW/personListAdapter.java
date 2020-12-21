package com.example.TRS_VIEW;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class personListAdapter extends ArrayAdapter<person> {
    public static final String TAG = "personListAdapter";
     Context mContext;
    int mResource;

    public personListAdapter(@NonNull Context context, int resource, @NonNull List<person> objects) {
        super(context, resource, objects);
        mContext = context;
         mResource  = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String nom = getItem(position).getNom();
        String prenom = getItem(position).getPrenom();
        String badge = getItem(position).getBadge();
        person person = new person(nom, prenom, badge);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent,false);
        TextView tvnom = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvpernom = (TextView) convertView.findViewById(R.id.textView2);
        TextView tvbadge = (TextView) convertView.findViewById(R.id.textView3);


        tvnom.setText(nom);
        tvpernom.setText(prenom);
        tvbadge.setText(badge);
        return convertView;
    }
}


