package Practica9;

public class AlcanceEstructuras {

    public static void main(String[] args) {
        Scope currentScope;
        currentScope = new GlobalScope();
        currentScope.define(new BuiltInTypeSymbol("int"));
        currentScope.define(new BuiltInTypeSymbol("float"));
        currentScope.define(new BuiltInTypeSymbol("void"));
        System.out.println("global : def int");
        System.out.println("global : def float");
        System.out.println("global : def void");

        // struct A{ }
        StructSymbol sm1 = new StructSymbol("A", currentScope);
        currentScope.define(sm1);
        currentScope = sm1;
        System.out.println("global : def struct A");

        // int x;
        BuiltInTypeSymbol t1 = (BuiltInTypeSymbol) currentScope.resolve("int");

        if (t1 == null) {
            System.out.println("TIPO NO DECLARADO");
            return;
        }
        System.out.println("\tstruct A : ref int");

        currentScope.define(new VariableSymbol("x", t1));
        System.out.println("\tstruct A : def x");

        // struct B { }
        sm1 = new StructSymbol("B", currentScope);
        currentScope.define(sm1);
        currentScope = sm1;
        // int y;
        System.out.println("\tstruct A : def struct B");
        t1 = (BuiltInTypeSymbol) currentScope.resolve("int");

        if (t1 == null) {
            System.out.println("TIPO NO DECLARADO");
            return;
        }
        System.out.println("\t\tstruct B: ref int");

        currentScope.define(new VariableSymbol("y", t1));
        System.out.println("\t\tstruct B: def y");

        currentScope = currentScope.getEnclosingScope();
        System.out.println("\t\tstruct B: close");
        // B b;
        sm1 = (StructSymbol) currentScope.resolve("B");

        if (sm1 == null) {
            System.out.println("TIPO NO DECLARADO");
            return;
        }
        System.out.println("\tstruct A : ref struct B");

        currentScope.define(new VariableSymbol("b", sm1));
        System.out.println("\tstruct A : def b");

        // struct C { }
        sm1 = new StructSymbol("C", currentScope);
        currentScope.define(sm1);
        currentScope = sm1;
        // int y;
        System.out.println("\tstruct A : def struct C");
        t1 = (BuiltInTypeSymbol) currentScope.resolve("int");

        if (t1 == null) {
            System.out.println("TIPO NO DECLARADO");
            return;
        }
        System.out.println("\t\tstruct C: ref int");

        currentScope.define(new VariableSymbol("z", t1));
        System.out.println("\t\tstruct C: def z");
        // close struct C
        currentScope = currentScope.getEnclosingScope();
        System.out.println("\t\tstruct C: close");
        // B b;
        sm1 = (StructSymbol) currentScope.resolve("C");

        if (sm1 == null) {
            System.out.println("TIPO NO DECLARADO");
            return;
        }
        System.out.println("\tstruct A : ref struct C");

        currentScope.define(new VariableSymbol("c", sm1));
        System.out.println("\tstruct A : def C");

        currentScope = currentScope.getEnclosingScope();

        sm1 = (StructSymbol) currentScope.resolve("A");

        if (sm1 == null) {
            System.out.println("TIPO NO DECLARADO");
            return;
        }

        System.out.println("global : ref struct A");

        currentScope.define(new VariableSymbol("a", sm1));

        System.out.println("global : def a");

        MethodSymbol sm2 = new MethodSymbol("f", null, currentScope);
        currentScope.define(sm2);
        currentScope = sm2;

        System.out.println("global : def funtion f");

        // struct D { }
        sm1 = new StructSymbol("D", currentScope);
        currentScope.define(sm1);
        currentScope = sm1;
        // int i;
        System.out.println("\tfuntion f : def struct D");
        t1 = (BuiltInTypeSymbol) currentScope.resolve("int");

        if (t1 == null) {
            System.out.println("TIPO NO DECLARADO");
            return;
        }
        System.out.println("\t\tstruct D: ref int");

        currentScope.define(new VariableSymbol("i", t1));

        System.out.println("\t\tstruct D: def i");
        // close struct C
        currentScope = currentScope.getEnclosingScope();
        System.out.println("\t\tstruct D: close");
        // D d;
        sm1 = (StructSymbol) currentScope.resolve("D");

        if (sm1 == null) {
            System.out.println("TIPO NO DECLARADO");
            return;
        }
        System.out.println("\tfuntion f : ref struct D");
        currentScope.define(new VariableSymbol("d", sm1));
        System.out.println("\tfuntion f : def d");
        // d
        VariableSymbol v1 = (VariableSymbol) currentScope.resolve("d");
        if (v1 == null) {
            System.out.println("Variable no declarada");
            return;
        }

        System.out.println("\tfuntion f : ref d");
        // d.i;
        v1 = (VariableSymbol) ((StructSymbol) v1.type).getMember("i");
        if (v1 == null) {
            System.out.println("Variable no declarada");
            return;
        }
        System.out.println("\tfuntion f : ref d.i");
        // a
        v1 = (VariableSymbol) currentScope.resolve("a");

        if (v1 == null) {
            System.out.println("Variable no declarada");
            return;
        }
        System.out.println("\tfuntion f : ref a");
        // a.b
        v1 = (VariableSymbol) ((StructSymbol) v1.type).getMember("b");
        if (v1 == null) {
            System.out.println("Variable no declarada");
            return;
        }
        System.out.println("\tfuntion f : ref a.b");
        // a.b.y;
        v1 = (VariableSymbol) ((StructSymbol) v1.type).getMember("y");
        if (v1 == null) {
            System.out.println("Variable no declarada");
            return;
        }
        System.out.println("\tfuntion f : ref a.b.y");
        currentScope = currentScope.getEnclosingScope();
        System.out.println(currentScope);
    }
}
