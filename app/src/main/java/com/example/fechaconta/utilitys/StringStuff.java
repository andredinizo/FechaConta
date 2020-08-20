package com.example.fechaconta.utilitys;

/**
 * Classe para tratar Strings, Utilidade.
 * Fique avontade para criar sua Função para
 * se reaproveitada futuramente, esforce-se pra
 * manter o padrão, Obrigado! ~-Sonecão-~
 */
public class StringStuff {

    public static final int RETIRAR_ESPACOS = 0;


    public static String converterString (String mudar, int funcao) {

        switch (funcao) {
            case 0: //RETIRAR_ESPACOS
                return retirarEspacos(mudar);


        }
        return null;
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
