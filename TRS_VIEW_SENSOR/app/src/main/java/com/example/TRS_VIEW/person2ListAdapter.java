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

public class person2ListAdapter extends ArrayAdapter<person2> {
    private static final String TAG = "person2ListAdapter";
    private Context mContext;
    int mResource;

    public person2ListAdapter(@NonNull Context context, int resource, @NonNull List<person2> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String produit = getItem(position).getProduit();
        String of = getItem(position).getOf();
        String matiere = getItem(position).getMatiere();
        String pres = getItem(position).getPres();
        String qte_fab = getItem(position).getQte_fab();
        String cadence = getItem(position).getCadence();
        person2 person2 = new person2(produit, of, matiere, pres ,qte_fab,cadence);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent,false);
        TextView tvproduit = (TextView) convertView.findViewById(R.id.textView101);
        TextView tvof = (TextView) convertView.findViewById(R.id.textView202);
        TextView tvmatiere = (TextView) convertView.findViewById(R.id.textView303);
        TextView tvqte_fab = (TextView) convertView.findViewById(R.id.textView404) ;
        TextView tvpres = (TextView) convertView.findViewById(R.id.textView606) ;
        TextView tvcandence = (TextView) convertView.findViewById(R.id.textView505) ;
        tvproduit.setText(produit);
        tvof.setText(of);
        tvmatiere.setText(matiere);
        tvpres.setText(pres);
        tvqte_fab.setText(qte_fab);
        tvcandence.setText(cadence);
        return convertView;
    }
}
