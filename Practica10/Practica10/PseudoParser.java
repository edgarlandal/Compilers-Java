package Practica9;

import java.util.ArrayList;

public class PseudoParser {
    ArrayList<PseudoLexer.Token> tokens;
    int tokenIndex = 0;
    SymbolTable table;
    VariableSymbol vs;
    BuiltInTypeSymbol bit;
    String tipoS;

    public boolean parse(ArrayList<PseudoLexer.Token> tokens) {
        this.tokens = tokens;
        table = new SymbolTable();

        System.out.println("\n\n*********************** Reglas de producción ************************\n\n");
        return programa();
    }

    private boolean match(String name) {
        if (tokens.get(tokenIndex).type.name().equals(name)) {
            System.out.println(tokens.get(tokenIndex).type.name() + " " +
                    tokens.get(tokenIndex).data);
            tokenIndex++;
            return true;
        }
        return false;
    }
    // <Programa> ::= inicio-programa <Enunciados> fin-programa

    private boolean programa() {
        System.out.println(
                "<Programa> --> inicio-programa <Enunciados> fin-programa | inicio-programa <Definicion> <Enunciados> fin-programa | inicio-programa, fin-programa");

        int tokenIndexAux = tokenIndex;
        if (match("INICIOPROGRAMA"))
            if (enunciados())
                if (match("FINPROGRAMA"))
                    if (tokenIndex == tokens.size())
                        return true;

        tokenIndex = tokenIndexAux;
        tokenIndexAux = tokenIndex;

        if (match("INICIOPROGRAMA"))
            if (declaracionEnteras())
                if (declaracionFlotantes())
                    if (enunciados())
                        if (match("FINPROGRAMA"))
                            return true;

        tokenIndex = tokenIndexAux;
        tokenIndexAux = tokenIndex;

        if (match("INICIOPROGRAMA"))
            if (declaracionEnteras())
                if (enunciados())
                    if (match("FINPROGRAMA"))
                        return true;

        tokenIndex = tokenIndexAux;
        tokenIndexAux = tokenIndex;

        if (match("INICIOPROGRAMA"))
            if (declaracionFlotantes())
                if (enunciados())
                    if (match("FINPROGRAMA"))
                        return true;

        tokenIndex = tokenIndexAux;

        if (match("INICIOPROGRAMA"))
            if (match("FINPROGRAMA"))
                return true;
        return false;
    }

    // <Enunciados> ::= <Enunciado> <Enunciados> | Vacio
    private boolean enunciados() {
        System.out.println("<Enunciados> --> <Enunciado> <Enunciados> | <Enunciado>");
        int tokenIndexAux = tokenIndex;

        if (enunciado())
            if (enunciados())
                return true;
        tokenIndex = tokenIndexAux;

        if (enunciado())
            return true;
        return false;
    }

    private boolean variables() {
        if (valorD()) {
            vs = new VariableSymbol(tokens.get(tokenIndex - 1).data, bit);
            table.define(vs);
            if (match("COMA"))
                if (variables()) {
                    return true;
                }
            return true;
        }
        return false;
    }

    private boolean declaracionEnteras() {
        System.out.println("<Declaracion> --> variables, enteras <Valor> coma <Valor>");

        if (match("VARIABLES"))
            if (match("ENTERAS")) {
                bit = (BuiltInTypeSymbol) table.resolve("enteras");
                if (bit == null) {
                    System.out.println("TIPO VARIABLE NO DEFINIDA");
                    return false;
                }
                if (match("DOSPUNTOS"))
                    if (variables())
                        return true;
            }
        return false;
    }

    private boolean declaracionFlotantes() {
        System.out.println("<Declaracion> --> variables, flotantes <Valor> coma <Valor>");

        if (match("VARIABLES"))
            if (match("FLOTANTES")) {
                bit = (BuiltInTypeSymbol) table.resolve("flotantes");
                if (bit == null) {
                    System.out.println("TIPO VARIABLE NO DEFINIDA: " + bit);
                    return false;
                }
                if (match("DOSPUNTOS"))
                    if (variables())
                        return true;
                return true;
            }
        return false;
    }

    // <Enunciado> ::= <Asignacion> | <Leer> | <Escribir> | <Si> | <Mientras>
    private boolean enunciado() {
        System.out.println("<Enunciado> --> <Asignacion> | <Leer> | <Escribir> | <Si> | <Mientras>");
        if (enunciadoAsignacion()) {
            System.out.println("<Enunciado> --> <Asignacion>");
            return true;
        }
        if (enunciadoLeer()) {
            System.out.println("<Enunciado> --> <Leer>");
            return true;
        }
        if (enunciadoEscribir()) {
            System.out.println("<Enunciado> --> <Escribir>");
            return true;
        }
        if (enunciadoSi()) {
            System.out.println("<Enunciado> --> <Si>");
            return true;
        }
        if (enunciadoMientras()) {
            System.out.println("<Enunciado> --> <Mientras>");
            return true;
        }
        if (enunciadoRepetir()) {
            System.out.println("<Enunciado> --> <Repetir>");
            return true;
        }
        return false;
    }

    // <Asignacion> ::= <Variable> = <Expresion>
    private boolean enunciadoAsignacion() {
        System.out.println("<Asignacion> --> <Variable> = <Expresion>");
        vs = (VariableSymbol) table.resolve(tokens.get(tokenIndex).data);

        if (match("VARIABLE")) {
            if (vs == null) {
                System.out.println("VARIABLE NO DECLARADA!!:" + tokens.get(tokenIndex - 1));
                return false;
            }
            if (match("IGUAL"))
                if (expresion())
                    return true;
        }
        return false;
    }

    // <Expresion > ::= <Valor> | <0peracion>
    private boolean expresion() {
        System.out.println("<Expresion > --> <Operacion> | <Valor>");
        System.out.println("<Valor> --> <Variable> | <Numero>");

        if (operacion())
            return true;
        if (valor())
            return true;

        return false;
    }

    // <Valor> <Variable> | <Numero>
    private boolean valor() {
        if (match("VARIABLE")) {
            vs = (VariableSymbol) table.resolve(tokens.get(tokenIndex - 1).data);
            if (vs == null) {
                System.out.println("VARIABLE NO DECLARADA!!:" + tokens.get(tokenIndex - 1));
                return false;
            }
            return true;
        }

        if (match("NUMERO"))
            return true;
        return false;
    }

    private boolean valorD() {
        if (match("VARIABLE"))
            return true;
        if (match("NUMERO"))
            return true;
        return false;
    }

    // <0peracion> ::= <Valor> <0perador-aritmetico> <Valor>
    private boolean operacion() {
        System.out.println("<Operacion> --> <Valor> <0perador-aritmetico> <Valor>");

        int tokenIndexAux = tokenIndex;

        if (valor()) {
            if (match("OPARITMETICO"))
                if (valor())
                    return true;

        }
        tokenIndex = tokenIndexAux;
        return false;
    }

    private boolean limites() {
        System.out.println("<Leer> --> leer <Cadena> , <Variable>");
        if (match("PARENTISISIZQ"))
            if (enunciadoAsignacion())
                if (match("COMA"))
                    if (valor())
                        if (match("PARENTESISDER"))
                            return true;
        return false;
    }

    // <Leer> ::= leer <Cadena> , <Variable>
    private boolean enunciadoLeer() {
        System.out.println("<Leer> --> leer <Cadena> , <Variable>");
        if (match("LEER"))
            if (match("CADENA"))
                if (match("COMA")) {
                    vs = (VariableSymbol) table.resolve(tokens.get(tokenIndex).data);
                    if (match("VARIABLE")) {
                        if (vs == null) {
                            System.out.println("VARIABLE NO DECLARADA!!:" + tokens.get(tokenIndex - 1));
                            return false;
                        }
                        return true;
                    }
                }
        return false;
    }

    // <Escribir> ::= escribir <Cadena> | escribir
    private boolean enunciadoEscribir() {
        System.out.println("<Escribir> --> escribir <Cadena> | escribir <Cadena> , <Variable>");

        int tokenIndexAux = tokenIndex;

        if (match("ESCRIBIR"))
            if (match("CADENA"))
                if (match("COMA")) {
                    vs = (VariableSymbol) table.resolve(tokens.get(tokenIndex).data);
                    if (match("VARIABLE")) {
                        if (vs == null) {
                            System.out.println("VARIABLE NO DECLARADA!!:" + tokens.get(tokenIndex - 1));
                            return false;
                        }
                        return true;
                    }
                }
        tokenIndex = tokenIndexAux;
        if (match("ESCRIBIR"))
            if (match("CADENA"))
                return true;

        return false;
    }

    // . <Si> ::= si <Comparacion> entonces <Enunciados> fin-si
    private boolean enunciadoSi() {
        System.out.println("<Si> -- > si <Comparacion> entonces <Enunciados> fin-si");

        if (match("SI"))
            if (comparacion())
                if (match("ENTONCES"))
                    if (enunciados())
                        if (match("FINSI"))
                            return true;
        return false;
    }

    // <Comparacion> ::= ( <Valor> <0perador-relacional> <Valor> )
    private boolean comparacion() {
        System.out.println("<Comparacion> --> ( <Valor> <Operador-relacional> <Valor> )");

        if (match("PARENTISISIZQ"))
            if (valor())
                if (match("OPRELACIONAL"))
                    if (valor())
                        if (match("PARENTESISDER"))
                            return true;
        return false;
    }

    // <Mientras> :: s <Comparacion> <Enunciados> fin
    private boolean enunciadoMientras() {
        System.out.println("<Mientras> --> mientras <Comparacion> <Enunciados> fin—mientras");

        if (match("MIENTRAS"))
            if (comparacion())
                if (enunciados())
                    if (match("FINMIENTRAS"))
                        return true;
        return false;
    }

    private boolean enunciadoRepetir() {
        System.out.println("<Repetir> --> repetir <Limites> <Enunciados> fin-repetir");
        if (match("REPITE"))
            if (limites())
                if (enunciados())
                    if (match("FINREPITE"))
                        return true;
        return false;
    }

}
