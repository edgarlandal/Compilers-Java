package Practica9;

public class AlcanceClases {
    public static void main(String[] args) {
        Scope currentScope;
        currentScope = new GlobalScope();
        currentScope.define(new BuiltInTypeSymbol("int"));
        currentScope.define(new BuiltInTypeSymbol("float"));
        currentScope.define(new BuiltInTypeSymbol("void"));
        System.out.println("global : def int");
        System.out.println("global : def float");
        System.out.println("global : def void");

        // class A{ }
        ClassSymbol cs = new ClassSymbol("A", currentScope, currentScope);
        currentScope.define(cs);
        currentScope = cs;

        System.out.println("global : def class A");
        // int i;
        BuiltInTypeSymbol t1 = (BuiltInTypeSymbol) currentScope.resolve("int");
        if (t1 == null) {
            System.out.println("Tipo de variable no declarada");
            return;
        }
        System.out.println("\tclass A : ref int");

        currentScope.define(new VariableSymbol("x", t1));
        System.out.println("\tclass A : def x");
        // foo () { }
        t1 = (BuiltInTypeSymbol) currentScope.resolve("void");
        if (t1 == null) {
            System.out.println("Tipo de variable no declarada");
            return;
        }
        System.out.println("\tclass A : ref void");

        MethodSymbol sm2 = new MethodSymbol("foo", null, currentScope);
        currentScope.define(sm2);
        currentScope = sm2;

        System.out.println("\tclass A : def method foo");
        currentScope = currentScope.getEnclosingScope();
        currentScope = currentScope.getEnclosingScope();

        System.out.println("\tclass A : close method foo()");
        System.out.println("\tclass A : close class A");
        // class B : public A
        ClassSymbol superClase = (ClassSymbol) currentScope.resolve("A");

        if (superClase == null) {
            System.out.println("Clase no definida");
            return;
        }
        System.out.println("global : ref class A");

        cs = new ClassSymbol("B", superClase, currentScope);
        currentScope.define(cs);
        currentScope = cs;

        System.out.println("global : def class B");
        // int y;
        t1 = (BuiltInTypeSymbol) currentScope.resolve("int");

        if (t1 == null) {
            System.out.println("Tipo de variable no declarado");
            return;
        }
        System.out.println("\tclass B : ref int");

        currentScope.define(new VariableSymbol("y", t1));
        System.out.println("\tclass B : def y");

        sm2 = new MethodSymbol("foo", null, currentScope);
        currentScope.define(sm2);
        currentScope = sm2;
        System.out.println("\tclass B : def method foo()");

        t1 = (BuiltInTypeSymbol) currentScope.resolve("int");

        if (t1 == null) {
            System.out.println("Tipo de variable no declarado");
            return;
        }
        System.out.println("\t\tmethod foo() : ref int");

        currentScope.define(new VariableSymbol("z", t1));
        System.out.println("\t\tmethod foo() : def z");

        VariableSymbol vs1 = (VariableSymbol) currentScope.resolve("x");

        if (vs1 == null) {
            System.out.println("Variable no declarada");
            return;
        }
        System.out.println("\t\tmethod foo() : ref x");

        vs1 = (VariableSymbol) currentScope.resolve("y");

        if (vs1 == null) {
            System.out.println("Variable no declarada");
            return;
        }
        System.out.println("\t\tmethod foo() : ref y");

        currentScope = currentScope.getEnclosingScope();
        System.out.println("\t\tmethod foo() : close method foo()");
        // close class B
        currentScope = currentScope.getEnclosingScope();
        System.out.println("\tclass B : close class");

        System.out.println("\n" + currentScope);
    }

}
