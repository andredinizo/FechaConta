package com.example.fechaconta.utilitys;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Classe para tratar Strings, Utilidade.
 * Fique avontade para criar sua Função para
 * se reaproveitada futuramente, esforce-se pra
 * manter o padrão, Obrigado! ~-Sonecão-~
 */
public class StringStuff {

    public static final int RETIRAR_ESPACOS = 0;
    public static final int FORMATAR_VALOR = 1;

    /**
     * Interpolador
     * @param mudar - Objeto a ser operado.
     * @param funcao - Função a ser Executada.
     * @return - Retorna a String Transformada.
     */
    public static String converterString (Object mudar, int funcao) {

        switch (funcao) {
            case 0: //RETIRAR_ESPACOS
                return retirarEspacos((String) mudar);
            case 1: //FORMATAR_VALOR
                return formatarValor((float) mudar);
        }
        return null;
    }

    /**
     * Formata um float pra valor,
     * isto é "R$ 0.00".
     * @param mudar - Numero de entrada.
     * @return - String Formatada.
     */
    private static String formatarValor(float mudar) {
        NumberFormat format = new DecimalFormat("#0.00");
        return "R$ " + String.valueOf(format.format(mudar));
    }

    /**
     * Retira os espaços da String.
     * @param texto = Texto a ser Mudado
     */
    private static String retirarEspacos (String texto) {

        String resultado = texto.replaceAll(" ", "");

        return  resultado;
    }


}
