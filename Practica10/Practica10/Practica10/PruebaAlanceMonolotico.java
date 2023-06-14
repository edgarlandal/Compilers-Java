package Practica10;

public class PruebaAlanceMonolotico {
    public static void main(String[] args) {
        SymbolTable st = new SymbolTable();

        Symbol s = new BuiltInTypeSymbol("int");
        st.define(s);

        s = new BuiltInTypeSymbol("float");
        st.define(s);

        s = st.resolve("int");

        if (s != null) {
            System.out.println("ref int");
        } else {
            System.out.println("Error");
            return;
        }

        s = new VariableSymbol("i", (Type) s);
        st.define(s);
        System.out.println("def i");

        s = st.resolve("float");

        if (s != null) {
            System.out.println("ref float");
        } else {
            System.out.println("Error");
            return;
        }

        s = new VariableSymbol("j", (Type) s);
        st.define(s);
        System.out.println("def j");

        s = st.resolve("int");

        if (s != null) {
            System.out.println("ref int");
        } else {
            System.out.println("Error");
            return;
        }

        s = new VariableSymbol("k", (Type) s);
        st.define(s);

        s = st.resolve("i");

        if (s != null) {
            System.out.println("ref to " + s);
        } else {
            System.out.println("Error");
            return;
        }
        System.out.println("def k");

        System.out.println(st);
    }
}
