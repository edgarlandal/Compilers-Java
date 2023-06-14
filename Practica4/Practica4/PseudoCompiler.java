package Practica4;

import java.io.FileReader;
import java.util.ArrayList;

public class PseudoCompiler {
    PseudoLexer pLexer;

    public PseudoCompiler() {
        pLexer = new PseudoLexer();

        String input = "";

        try {
            FileReader reader = new FileReader("C:/Users/mikeydrako/Desktop/Java/prom.alg");
            int character;
            while ((character = reader.read()) != -1) {
                input += (char) character;
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("*************  Programa Fuente *************\n");
        System.out.println(input);

        ArrayList<PseudoLexer.Token> tokens = pLexer.getTokens(input);
        System.out.println("\n\n*************  Tokens *************\n");

        for (PseudoLexer.Token token : tokens) {
            System.out.println(token);
        }
    }

    public static void main(String[] args) {
        new PseudoCompiler();
    }
}
