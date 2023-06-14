package Practica1;

import java.util.Scanner;

public class Practica1 {
    public static String palabra;
    public static int i = 0;
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Ingrese una palabra");
            palabra = scanner.nextLine();
        }

        char x = palabra.charAt(i);
        int tam = palabra.length();

        if ( x == 'i') { // Pasar del estado A al B
            i++;
            x = palabra.charAt(i);
            if(x == 'f'){ // Pasar del estado B al C
                if (i < tam-1) {
                    i++;
                    x = palabra.charAt(i);
                    estadoD(tam, x);// Pasar del estado C al D
                } else { //Si coicide es un if
                    System.out.println("If");
                }
            } else if (x >= 97 && x <= 104 || x >= 106 && x <= 122 ||x >= 65 && x <= 90 || x >= 48 && x<= 57) {
                estadoD(tam, x); //Pasar del estado B al D
            }
        } else if (x >= 97 && x <= 101 || x >= 103 && x <= 122 ||x >= 65 && x <= 90) {
            estadoD(tam, x);   //Pasar del estado A al D
        } else { // Estado A al E
            if (x == '.') {
                estadoH(tam, x); //Estado E al H  
            } else if (x == '+' || x == '-') { //Estado A al F
                estadoG(tam, x);
            } else if (x >= 48 || x <= 57) {// Estadp A a G
                estadoG(tam, x);
            }
        }
    }

    public static void estadoD(int tam, char x) {
        while(i < tam){ 
            if (x >= 97 && x <= 122 || x >= 65 && x <= 90 || x >= 48 && x<= 57){
                i++;
                if(i != tam){
                    x = palabra.charAt(i);
                } else {
                    System.out.println("Identificador");
                }
            } else {
                break;
            }
        }
    }

    public static void estadoH(int tam, char x) {
        while (i < tam) {
            i++;
            if(i != tam){
                x = palabra.charAt(i);
                if (x == 'e' || x == 'E') {
                    estadoI(x, tam);
                    break;
                } else  if (!(x >= 48 && x <= 57)) {
                    System.out.println("Invalido");
                    break;
                }  
            } else { 
                System.out.println("Float");
                break;
            }
        }
    }

    public static void estadoG(int tam, char x) {
        while (i < tam) {
            i++;
            if(i != tam){
                x = palabra.charAt(i);
                if (x == '.') {
                    estadoH(tam, x);
                } else {
                    if (x == 'e' || x == 'E') {
                        estadoK(tam, x);
                        break;
                    } else if (!(x >= 48 && x<= 57)) {
                        System.out.println("Invalido");
                        break;
                    }
                }
            } else {
                System.out.println("Numero");
                break;
            }        
        }
    }

    public static void estadoI(char x, int tam) {
        i++;
        x = palabra.charAt(i);
        if (x == '+' || x == '-' || (x >= 48 && x<= 57)) 
            estadoK(tam, x);
    }

    public static void estadoK(int tam, char x) {
        while (i < tam) {  
            i++;
            if(i != tam){
                x = palabra.charAt(i);
                if (!((x >= 48 && x <= 57) ||x == '+' || x == '-')) {
                    System.out.println("Invalido");
                    break;
                }
            } else { 
                System.out.println("Float");
                break;
            }
        }
    }
}