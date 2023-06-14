package Practica4;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PseudoLexer {
    public enum TokenType {
        NUMERO("-?[0-9]+(\\.([0-9]+))?"),
        CADENA("\".*\""),
        OPARITMETICO("[*|/|+|-]"),
        OPRELACIONAL("<|>|==|<=|>=|!="),
        IGUAL("="),
        INICIOPROGRAMA("inicio-programa"),
        FINPROGRAMA("fin-programa"),
        SI("si"),
        ENTONCES("entonces"),
        FINSI("fin-si"),
        MIENTRAS("mientras"),
        FINMIENTRAS("fin-mientras"),
        LEER("leer"),
        ESCRIBIR("escribir"),
        COMA(","),
        PARENTISISIZQ("\\("),
        PARENTISISFER("\\)"),
        ESPACIOS("\\s+"),
        VARIABLE("[a-zA-Z][a-zA-Z0-9]*"),
        ERROR(".+");

        public final String pattern;

        private TokenType(String pattern) {
            this.pattern = pattern;
        }
    }

    public class Token {
        public TokenType type;
        public String data;

        public Token(TokenType type, String data) {
            this.type = type;
            this.data = data;
        }

        @Override
        public String toString() {
            return String.format("(%s  \"%s\")", type.name(), data);
        }

    }

    public ArrayList<Token> getTokens(String input) {
        ArrayList<Token> tokens = new ArrayList<Token>();
        StringBuffer tokenPatternsBuffer = new StringBuffer();

        for (TokenType tokenType : TokenType.values()) {
            tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
        }

        System.out.println(tokenPatternsBuffer);

        Pattern tokenPatterns = Pattern.compile(new String(tokenPatternsBuffer.substring(1)));

        Matcher matcher = tokenPatterns.matcher(input);

        while (matcher.find()) {
            for (TokenType t : TokenType.values()) {
                if (matcher.group(TokenType.ESPACIOS.name()) != null) {
                    continue;
                } else if (matcher.group(t.name()) != null) {
                    tokens.add(new Token(t, matcher.group(t.name())));
                    break;
                }
            }
        }
        return tokens;
    }
}
