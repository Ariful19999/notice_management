package com.arif19.vbd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arif19.vbd.R;
import com.arif19.vbd.modal.service_modal;

import java.util.ArrayList;

public class service_adapter extends ArrayAdapter<service_modal> {

    public service_adapter(@NonNull Context context, ArrayList<service_modal> courseModelArrayList) {
        super(context, 0, courseModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView. 
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_service, parent, false);
        }

        service_modal service_modal = getItem(position);
        TextView title = listitemView.findViewById(R.id.title);
        ImageView image = listitemView.findViewById(R.id.image);

        title.setText(service_modal.getTitle());
        image.setImageResource(service_modal.getImgid());
        return listitemView;
    }
}

