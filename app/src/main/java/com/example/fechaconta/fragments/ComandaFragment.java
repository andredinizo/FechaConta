package com.example.fechaconta.fragments;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fechaconta.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ComandaFragment extends BottomSheetDialogFragment {

    BottomSheetBehavior bottomSheetBehavior;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheet = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        //Infla Layout
        View view = View.inflate(getContext(), R.layout.sheet_layout_comanda, null);

        bottomSheet.setContentView(view);

        bottomSheetBehavior = BottomSheetBehavior.from((View) (view.getParent()));

        bottomSheetBehavior.setPeekHeight(80);
        bottomSheetBehavior.setHideable(false);

        LinearLayout teste = view.findViewById(R.id.testebottom);

        teste.setMinimumHeight((Resources.getSystem().getDisplayMetrics().heightPixels));

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                if(BottomSheetBehavior.STATE_EXPANDED == newState){

                    Toast.makeText(getContext(), "Expanded",Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        return bottomSheet;

    }
}
