package com.ali.earthquake.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ali.earthquake.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;


public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {

    Context context;
    View view;
    LayoutInflater layoutInflater;

    public CustomInfoWindow(Context context) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.custom_window_info, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        TextView title = view.findViewById(R.id.winTitleID);
        TextView magn = view.findViewById(R.id.winMagnID);

        title.setText(marker.getTitle());
        magn.setText(marker.getSnippet());
        return view;
    }
}
