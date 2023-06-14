package Practica5;

public class Practica5 {
    public static void main(String[] args) {
        ListLexer lexer = new ListLexer("[a,b=c,[d,e]]");
        ListParserk parser = new ListParserk(lexer, 2);
        parser.list();
    }
}