package com.example.socialdukan.Student.fragment.profile.aboutus;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialdukan.R;

public class clients_vh extends RecyclerView.ViewHolder {
    public ImageView cmpimg;

    public RelativeLayout layout_card;

    public clients_vh(@NonNull View itemView) {
        super( itemView );

        cmpimg = itemView.findViewById( R.id.icd_cmpimg );

        layout_card = itemView.findViewById( R.id.layout_card );



    }
}