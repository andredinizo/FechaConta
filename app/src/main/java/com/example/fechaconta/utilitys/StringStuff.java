package com.example.fechaconta.utilitys;

import android.text.Editable;

import java.util.InputMismatchException;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Classe para tratar Strings, Utilidade.
 * Fique a vontade para criar sua Função para
 * ser reaproveitada futuramente, esforce-se pra
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
     *
     * @param texto = Texto a ser Mudado
     */
    private static String retirarEspacos(String texto) {

        String resultado = texto.replaceAll(" ", "");

        return resultado;
    }


 /*esse método fica editando um editText, o adequando a uma chave formada
 por caracteres '#' e os caracteres especiais   .- ()    (o espaço está incluído)   */
    public static Editable editablePutMask(Editable input, String mascara){

        int position;
        for(position=0; position<input.length(); ){
            if(mascara.charAt(position)=='#'){
                if(input.charAt(position)=='.' || input.charAt(position)=='-'
                        || input.charAt(position)==' ' || input.charAt(position)=='(' || input.charAt(position)==')')
                    input.delete(position, position+1);
                else
                    position++;
            }
            else{
                if(input.charAt(position)!=mascara.charAt(position))
                    input.insert(position,mascara,position,position+1);

                position++;
            }
        }

        return input;
    }


    public static boolean isEmail(String email){
        int position;
        boolean checkArroba = false;
        int arrobaPosition = 0;
        boolean checkPonto = false;
        for(position=0;position<email.length();position++){
            if(email.charAt(position)==("@").charAt(0)){
                if(checkArroba || position==0)
                    return false;
                arrobaPosition=position;
                checkArroba=true;
            }
            if(checkArroba){
                if(email.charAt(position)==(".").charAt(0)){
                    if(position==arrobaPosition+1 || checkPonto)
                        return false;

                    if(position>arrobaPosition+1 && position<email.length()-1)
                        checkPonto=true;
                }
            }
        }
        if(checkArroba && checkPonto)
            return true;
        else
            return false;
    }


/*
isCPF retorna true se o cpf for verdadeiro de acordo com os dígitos de verificação.
coloque o argumento isMascara como true se a String CPF contiver . ou -
*/
    public static boolean isCPF(String CPF, boolean isMascara) {

        if(isMascara) {
            CPF=CPF.replace(".","").replace("-","");
        }

        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = CPF.charAt(i) - 48;
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = CPF.charAt(i) - 48;
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            return (dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10));
        } catch (InputMismatchException erro) {
            return(false);
        }
    }
}
