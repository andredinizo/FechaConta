package com.example.fechaconta.utilitys;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;

import com.example.fechaconta.models.Adicional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Classe para ajudar a tratar os radiosButtons
 */
public class RecursiveRadioGroup {

    private List<RadioButton> radioButtons = new ArrayList<>();
    private List<Adicional> adicionals = new ArrayList<>();

    /**
     * Adiciona um radioButton a listae um adicional a lista.
     * e seta os Listenners.
     * @param radioButton - radio button.
     * @param adicional - adicional referenciado.
     */
    public void addRadioButtonAdicionalandListener(final RadioButton radioButton, final Adicional adicional){

        this.radioButtons.add(radioButton);
        this.adicionals.add(adicional);
        radioButtons.get(radioButtons.indexOf(radioButton)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){


                    for (RadioButton rb : radioButtons) radioButtons.get(radioButtons.indexOf(rb)).setChecked(false);
                    radioButtons.get(radioButtons.indexOf(radioButton)).setChecked(true);
                    for (Adicional adicional : adicionals) adicionals.get(adicionals.indexOf(adicional)).setInclude(false);
                    adicionals.get(adicionals.indexOf(adicional)).setInclude(true);


                }else {

                    radioButtons.get(radioButtons.indexOf(radioButton)).setChecked(false);
                    for (Adicional adicional : adicionals) adicionals.get(adicionals.indexOf(adicional)).setInclude(false);

                }

            }
        });
    }


    public List<RadioButton> getRadioButtons() {
        return radioButtons;
    }

    public void setRadioButtons(List<RadioButton> radioButtons) {
        this.radioButtons = radioButtons;
    }

    public List<Adicional> getAdicionals() {
        return adicionals;
    }

    public void setAdicionals(List<Adicional> adicionals) {
        this.adicionals = adicionals;
    }
}
