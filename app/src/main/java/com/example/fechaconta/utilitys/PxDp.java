package com.example.fechaconta.utilitys;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.google.rpc.context.AttributeContext;

/**
 * Classe utilitaria, para tratar algums problemas relacionado
 * a conversão entre Pixel e Dp.
 */
public class PxDp {



    public static int convertDptoPx (float dp, Context context){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp*scale + 0.5f);
    }


    /**
     * Retorna o valor em pixel segundo o dp.
     * e a resolução do celular, entretanto não está
     * funcional, por isso fiz outro.
     * @param context - Qualquer contexto.
     * @param dimenId - Id do resorce em dp  a ser comvertido.
     * @return - retorna o valor em px.
     */
    public static int getPixelValue (Context context, int dimenId){
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dimenId, Resources.getSystem().getDisplayMetrics()
        );
    }

}
