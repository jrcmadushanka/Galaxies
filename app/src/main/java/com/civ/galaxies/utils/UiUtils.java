package com.civ.galaxies.utils;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Window;

import com.civ.galaxies.R;
import com.civ.galaxies.databinding.DialogPlanetDetailsBinding;
import com.civ.galaxies.model.Planet;
import com.squareup.picasso.Picasso;

public class UiUtils {

    public static Dialog showPlanetDetailsDialog(Context context, Planet planet) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        DialogPlanetDetailsBinding binding = DialogPlanetDetailsBinding.inflate(inflater);
        binding.setPlanetModel(planet);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        final Dialog dialog = new Dialog(context, R.style.Style_Dialog_Rounded_Corner);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(binding.getRoot());

        Picasso.get().load("https://picsum.photos/id/" + planet.getImageId() + "/700").fit().placeholder(R.drawable.galaxy_logo).into(binding.ivMainImage);
        binding.ibClose.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog.show();
        return dialog;
    }
}
