package Practica9;

import java.util.ArrayList;
import java.util.Scanner;

public class PseudoParser {
    private static final String ESCRIBIR = "Escribir";
    private static final String LEER = "Leer";
    private static final String MIENTRAS = "Mientras";
    private static final String REPETIR = "Repetir";
    private static final String SI = "Si-Entonces";
    private static final String FIN = "Fin";
    private static final String ASIGNACION = "Asignacion";
    private static final String FIN_MIENTRAS = "Fin-Mientras";
    private static final String FIN_REPETIR = "Fin-Repetir";
    private static final String FIN_SI = "Fin_Si";

    Tuplo tuploInicial;
    private Tuplo tuploAux;
    private SymbolTable table = new SymbolTable();
    private VariableSymbol vs;
    private BuiltInTypeSymbol bit;
    private int line = 1;
    private Pila pilaSi;
    private Pila pilaMientras;
    private Pila pilaRepetir;

    ArrayList<PseudoLexer.Token> tokens;
    ArrayList<String> parameters = new ArrayList<>();

    private String tipoS;
    private int tokenIndex = 0;

    public boolean parse(ArrayList<PseudoLexer.Token> tokens) {
        this.tokens = tokens;
        table.initTypeSystem();
        initalPilas();
        System.out.println("\n\n*********************** Reglas de producción ************************\n\n");
        return programa();
    }

    private void initalPilas() {
        pilaMientras = new Pila();
        pilaRepetir = new Pila();
        pilaSi = new Pila();
    }

    private void ejecuccion(Tuplo a) {

        Scanner sc = new Scanner(System.in);
        while (a.getTrueT() != null) {
            System.out.print("\n" + a.getType());
            if (a.getType() == LEER) {
                System.out.println(a.getParameters().get(0));
                String variable = sc.nextLine();
                table.resolve(a.getParameters().get(1)).setValue(variable);
                a = a.getTrueT();
            } else if (a.getType() == ESCRIBIR) {
                if (a.getParameters().size() > 1) {
                    System.out.println(a.getParameters().get(0));
                    System.out.println(table.resolve(a.getParameters().get(1)).getValue());
                } else {
                    System.out.println(a.getParameters().get(0));
                }
                a = a.getTrueT();
            } else if (a.getType() == ASIGNACION) {
                String type = table.resolve(a.getParameters().get(0)).getType().getName();
                if (type == "enteras") {
                    int num = Integer.parseInt(table.resolve(a.getParameters().get(0)).getValue());
                    String value = Integer.toString(calcularE(num, a.getParameters(), 2));
                    table.resolve(a.getParameters().get(0)).setValue(value);
                } else {
                    float num = Float.parseFloat(table.resolve(a.getParameters().get(0)).getValue());
                    String value = Float.toString(calcularF(num, a.getParameters(), 2));
                    table.resolve(a.getParameters().get(0)).setValue(value);
                }
                a = a.getTrueT();
            } else if (a.getType() == MIENTRAS) {
                int n = Integer.parseInt(table.resolve(a.getParameters().get(2)).getValue());
                int num = Integer.parseInt(table.resolve(a.getParameters().get(0)).getValue());
                boolean bandera = false;
                char car = a.getParameters().get(1).charAt(0);
                if (car == '<')
                    bandera = (num < n);
                else if (car == '>')
                    bandera = (num > n);
                else if (a.getParameters().get(1) == "==")
                    bandera = (num == n);
                else if (a.getParameters().get(1) == "<=")
                    bandera = (num <= n);
                else if (a.getParameters().get(1) == ">=")
                    bandera = (num >= n);

                if (!bandera)
                    a = a.getFalseT();
                else
                    a = a.getTrueT();

            } else if (a.getType() == REPETIR) {
                int i = Integer.parseInt(table.resolve(a.getParameters().get(0)).getValue());
                int n = Integer.parseInt(table.resolve(a.getParameters().get(3)).getValue());
                Tuplo aux = null;
                if (i < n)
                    aux = a.getTrueT();
                else
                    aux = a.getFalseT();
                i++;
                table.resolve(a.getParameters().get(0)).setValue(Integer.toString(i));
                a = aux;
            } else if (a.getType() == SI) {
                String name = table.resolve(a.getParameters().get(0)).getType().getName();
                int n;
                float n2;
                int i = 0;
                float i2 = 0;
                if (name == "enteras") {
                    if (table.resolve(a.getParameters().get(2)) != null) {
                        n = Integer.parseInt(table.resolve(a.getParameters().get(2)).getValue());
                    } else {
                        n = Integer.parseInt(a.getParameters().get(2));
                    }
                    if (table.resolve(a.getParameters().get(0)) != null)
                        i = Integer.parseInt(table.resolve(a.getParameters().get(0)).getValue());
                    boolean bandera = false;
                    char car = a.getParameters().get(1).charAt(0);
                    if (car == '<')
                        bandera = (i < n);
                    else if (car == '>')
                        bandera = (i > n);
                    else if (a.getParameters().get(1) == "==")
                        bandera = (i == n);
                    else if (a.getParameters().get(1) == "<=")
                        bandera = (i <= n);
                    else if (a.getParameters().get(1) == ">=")
                        bandera = (i >= n);

                    if (!bandera)
                        a = a.getFalseT();
                    else
                        a = a.getTrueT();
                } else {
                    if (table.resolve(a.getParameters().get(0)) != null)
                        i2 = Float.parseFloat(table.resolve(a.getParameters().get(0)).getValue());
                    if (table.resolve(a.getParameters().get(2)) != null) {
                        n2 = Float.parseFloat(table.resolve(a.getParameters().get(2)).getValue());
                    } else {
                        n2 = Float.parseFloat(a.getParameters().get(2));
                    }
                    if (table.resolve(a.getParameters().get(0)) != null)
                        i2 = Float.parseFloat(table.resolve(a.getParameters().get(0)).getValue());
                    boolean bandera = false;
                    char car = a.getParameters().get(1).charAt(0);
                    if (car == '<')
                        bandera = (i < n2);
                    else if (car == '>')
                        bandera = (i2 > n2);
                    else if (a.getParameters().get(1) == "==")
                        bandera = (i2 == n2);
                    else if (a.getParameters().get(1) == "<=")
                        bandera = (i2 <= n2);
                    else if (a.getParameters().get(1) == ">=")
                        bandera = (i2 >= n2);

                    if (!bandera)
                        a = a.getFalseT();
                    else
                        a = a.getTrueT();
                }

            } else if (a.getType() == FIN_MIENTRAS) {
                a = a.getFalseT();
            } else if (a.getType() == FIN_REPETIR) {
                a = a.getFalseT();
            } else if (a.getType() == FIN_SI) {
                a = a.getTrueT();
            }
        }
    }

    private int calcularE(int num, ArrayList<String> arrayList, int index) {
        if (arrayList.size() > 3) {
            int a, b, c;
            String name = arrayList.get(index);
            String numero = null;
            String numero2 = null;

            if (table.resolve(name) != null)
                numero = table.resolve(name).getValue();

            if (numero == null)
                a = Integer.parseInt(name);
            else
                a = Integer.parseInt(numero);

            String name2 = arrayList.get(index + 2);
            if (table.resolve(name2) != null)
                numero2 = table.resolve(name2).getValue();

            if (numero2 == null)
                b = Integer.parseInt(name2);
            else
                b = Integer.parseInt(numero2);

            c = 0;
            char car = arrayList.get(index + 1).charAt(0);

            if (car == '+')
                c = a + b;
            else if (car == '-')
                c = a - b;
            else if (car == '/')
                c = a / b;
            else if (car == '*')
                c = a * b;

            return c;
        }
        return Integer.parseInt(arrayList.get(index));
    }

    private float calcularF(float num, ArrayList<String> arrayList, int index) {
        if (arrayList.size() > 3) {
            float a, b, c;
            String name = arrayList.get(index);
            String numero = null;
            String numero2 = null;

            if (table.resolve(name) != null)
                numero = table.resolve(name).getValue();

            if (numero == null)
                a = Float.parseFloat(name);
            else
                a = Float.parseFloat(numero);

            String name2 = arrayList.get(index + 2);
            if (table.resolve(name2) != null)
                numero2 = table.resolve(name2).getValue();

            if (numero == null)
                b = Float.parseFloat(name2);
            else
                b = Float.parseFloat(numero2);
            c = 0;

            char car = arrayList.get(index + 1).charAt(0);

            if (car == '+')
                c = a + b;
            else if (car == '-')
                c = a - b;
            else if (car == '/')
                c = a / b;
            else if (car == '*')
                c = a * b;
            return c;
        }

        return Float.parseFloat(arrayList.get(index));
    }

    private boolean match(String name) {
        if (tokens.get(tokenIndex).type.name().equals(name)) {
            // System.out.println(tokens.get(tokenIndex).type.name() + " " +
            // tokens.get(tokenIndex).data);
            tokenIndex++;
            return true;
        }
        return false;
    }

    private boolean programa() {
        // System.out.println(
        // "<Programa> --> inicio-programa <Enunciados> fin-programa | inicio-programa
        // <Definicion> <Enunciados> fin-programa | inicio-programa, fin-programa");
        if (match("INICIOPROGRAMA"))
            if (declaracionVariables())
                if (enunciados())
                    if (match("FINPROGRAMA")) {
                        setTuplo(FIN);
                        terminal(tuploInicial);
                        ejecuccion(tuploInicial);
                        System.out.println(tuploInicial);
                        return true;
                    }
        return false;
    }

    public void terminal(Tuplo a) {
        while (a.getTrueT() != null) {
            if (a.getType() == MIENTRAS) {
                Tuplo mientras = pilaMientras.Pop(); // Tomo el elemento de la pila
                mientras.setFalseT(a); // El fin mientras regresa al mientras
                a.setFalseT(mientras.getTrueT()); /// Condicion falsa del mientras
            }
            if (a.getType() == SI) {
                Tuplo si = pilaSi.Pop();
                si.setFalseT(a);
                a.setFalseT(si.getTrueT());
            }
            if (a.getType() == REPETIR) {
                Tuplo repetir = pilaRepetir.Pop();
                repetir.setFalseT(a);
                a.setFalseT(repetir.getTrueT());

            }
            a = a.getTrueT();
        }
    }

    private boolean enunciados() {
        // System.out.println("<Enunciados> --> <Enunciado> <Enunciados> |
        // <Enunciado>");
        if (enunciado())
            if (enunciados())
                return true;
            else
                return true;
        return false;
    }

    private boolean declaracionVariables() {
        if (match("VARIABLES")) {
            if (tipo())
                if (match("DOSPUNTOS"))
                    if (variables())
                        if (declaracionVariables())
                            return true;
        } else
            return true;
        return false;
    }

    private boolean tipo() {
        tipoS = tokens.get(tokenIndex).data;
        bit = (BuiltInTypeSymbol) table.resolve(tipoS);
        if (bit == null)
            return false;
        if (match("ENTERAS"))
            return true;
        if (match("FLOTANTES"))
            return true;
        return false;
    }

    private boolean defineVariables() {
        vs = new VariableSymbol(tokens.get(tokenIndex - 1).data, bit, "0");
        VariableSymbol vsAux = (VariableSymbol) table.resolve(vs.name);
        if (tipoS != null) {
            if (vsAux != null)
                return false;
            table.define(vs);
            return true;
        } else if (tipoS == null) {
            if (vsAux == null)
                return false;
            else
                return true;
        }
        return false;
    }

    private boolean variables() {
        if (valor())
            if (match("COMA")) {
                if (variables())
                    return true;
            } else {
                tipoS = null;
                return true;
            }
        return false;
    }

    private boolean enunciado() {
        // System.out.println("<Enunciado> --> <Asignacion> | <Leer> | <Escribir> | <Si>
        // | <Mientras>");
        parameters = new ArrayList<>();
        if (enunciadoAsignacion()) {
            // System.out.println("<Enunciado> --> <Asignacion>");
            setTuplo(ASIGNACION);
            return true;
        }
        parameters = new ArrayList<>();

        if (enunciadoLeer()) {
            setTuplo(LEER);
            // System.out.println("<Enunciado> --> <Leer>");
            return true;
        }
        parameters = new ArrayList<>();

        if (enunciadoEscribir()) {
            // System.out.println("<Enunciado> --> <Escribir>"
            return true;
        }
        parameters = new ArrayList<>();

        if (enunciadoSi()) {
            // System.out.println("<Enunciado> --> <Si>");
            return true;
        }
        parameters = new ArrayList<>();

        if (enunciadoMientras()) {
            // System.out.println("<Enunciado> --> <Mientras>");
            return true;
        }
        parameters = new ArrayList<>();

        if (enunciadoRepetir()) {
            // System.out.println("<Enunciado> --> <Repetir>");
            return true;
        }
        return false;
    }

    private void setTuplo(String type) {
        if (tuploInicial == null) {
            tuploInicial = new Tuplo(line++, type, parameters, null, null);
            tuploAux = tuploInicial;
        } else {
            Tuplo tuplo = new Tuplo(line++, type, parameters, null, null);
            if (tuploAux.getType() == ESCRIBIR
                    || tuploAux.getType() == LEER
                    || tuploAux.getType() == ASIGNACION) {
                tuploAux.setTrueT(tuplo);
                tuploAux.setFalseT(tuplo);
            } else {
                if (tuploAux.getType() == FIN_MIENTRAS) {
                    tuploAux.setFalseT(tuplo);
                    pilaMientras.Push(tuploAux);
                } else if (tuploAux.getType() == FIN_REPETIR) {
                    tuploAux.setFalseT(tuplo);
                    pilaRepetir.Push(tuploAux);
                } else if (tuploAux.getType() == FIN_SI) {
                    tuploAux.setFalseT(tuplo);
                    pilaSi.Push(tuploAux);
                }
                tuploAux.setTrueT(tuplo);
            }
            tuploAux = tuploAux.getTrueT();
        }
    }

    private boolean enunciadoAsignacion() {
        if (valor())
            if (match("IGUAL")) {
                parameters.add(tokens.get(tokenIndex - 1).data);
                if (expresion())
                    return true;
            }

        return false;
    }

    // <Expresion > ::= <Valor> | <0peracion>
    private boolean expresion() {
        // System.out.println("<Expresion > --> <Operacion> | <Valor>");
        // System.out.println("<Valor> --> <Variable> | <Numero>");
        if (operacion())
            return true;
        if (valor())
            return true;
        return false;
    }

    // <Valor> <Variable> | <Numero>
    private boolean valor() {
        if (match("VARIABLE")) {
            if (tipoS == null)
                parameters.add(tokens.get(tokenIndex - 1).data);
            if (defineVariables())
                return true;
        }
        if (match("NUMERO")) {
            parameters.add(tokens.get(tokenIndex - 1).data);
            return true;
        }
        return false;
    }

    // <0peracion> ::= <Valor> <0perador-aritmetico> <Valor>
    private boolean operacion() {
        // System.out.println("<Operacion> --> <Valor> <0perador-aritmetico> <Valor>");
        if (valor())
            if (match("OPARITMETICO")) {
                parameters.add(tokens.get(tokenIndex - 1).data);
                if (valor())
                    return true;
            } else
                return true;
        return false;
    }

    private boolean limites() {
        // System.out.println("<Leer> --> leer <Cadena> , <Variable>");
        if (match("PARENTISISIZQ"))
            if (enunciadoAsignacion())
                if (match("COMA"))
                    if (valor())
                        if (match("PARENTESISDER")) {
                            setTuplo(REPETIR);
                            return true;
                        }
        return false;
    }

    // <Leer> ::= leer <Cadena> , <Variable>
    private boolean enunciadoLeer() {
        // System.out.println("<Leer> --> leer <Cadena> , <Variable>");
        if (match("LEER"))
            if (match("CADENA")) {
                parameters.add(tokens.get(tokenIndex - 1).data);
                if (match("COMA"))
                    if (match("VARIABLE")) {
                        parameters.add(tokens.get(tokenIndex - 1).data);
                        if (defineVariables())
                            return true;
                    }
            }
        return false;
    }

    private boolean enunciadoEscribir() {
        // System.out.println("<Escribir> --> escribir <Cadena> | escribir <Cadena> ,
        // <Variable>");
        int tokenIndexAux = tokenIndex;
        if (match("ESCRIBIR"))
            if (match("CADENA")) {
                parameters.add(tokens.get(tokenIndex - 1).data);
                if (match("COMA")) {
                    if (valor()) {
                        setTuplo(ESCRIBIR);
                        return true;
                    }
                }
            }
        tokenIndex = tokenIndexAux;
        if (match("ESCRIBIR"))
            if (match("CADENA")) {
                setTuplo(ESCRIBIR);
                return true;
            }
        return false;
    }

    private boolean enunciadoSi() {
        // System.out.println("<Si> -- > si <Comparacion> entonces <Enunciados>
        // fin-si");
        if (match("SI"))
            if (comparacion())
                if (match("ENTONCES")) {
                    setTuplo(SI);
                    if (enunciados())
                        if (match("FINSI")) {
                            setTuplo(FIN_SI);
                            return true;
                        }
                }
        return false;
    }

    private boolean comparacion() {
        // System.out.println("<Comparacion> --> ( <Valor> <Operador-relacional>
        // <Valor>)");
        if (match("PARENTISISIZQ"))
            if (valor())
                if (match("OPRELACIONAL")) {
                    parameters.add(tokens.get(tokenIndex - 1).data);
                    if (valor())
                        if (match("PARENTESISDER"))
                            return true;
                }
        return false;
    }

    private boolean enunciadoMientras() {
        // System.out.println("<Mientras> --> mientras <Comparacion> <Enunciados>
        // fin—mientras");
        if (match("MIENTRAS"))
            if (comparacion()) {
                setTuplo(MIENTRAS);
                if (enunciados())
                    if (match("FINMIENTRAS")) {
                        setTuplo(FIN_MIENTRAS);
                        return true;
                    }
            }
        return false;
    }

    private boolean enunciadoRepetir() {
        // System.out.println("<Repetir> --> repetir <Limites> <Enunciados>
        // fin-repetir");
        if (match("REPITE"))
            if (limites())
                if (enunciados())
                    if (match("FINREPITE")) {
                        setTuplo(FIN_REPETIR);
                        return true;
                    }
        return false;
    }
}