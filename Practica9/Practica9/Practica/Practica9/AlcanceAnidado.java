package Practica9;

public class AlcanceAnidado {
    public static void main(String[] args) {
        Scope currentScope;

        currentScope = new GlobalScope();
        currentScope.define(new BuiltInTypeSymbol("int"));
        currentScope.define(new BuiltInTypeSymbol("float"));
        currentScope.define(new BuiltInTypeSymbol("void"));
        System.out.println("line 01 - global : def int");
        System.out.println("line 02 - global : def float");
        System.out.println("line 10 - global : def void");

        // int i = 9
        BuiltInTypeSymbol t1 = (BuiltInTypeSymbol) currentScope.resolve("int");

        if (t1 == null) {
            System.out.println("TIPO NO DECLARADO");
            return;
        }

        System.out.println("line 01 - global : ref int");
        currentScope.define(new VariableSymbol("i", t1));
        System.out.println("line 01 - global : def i");

        // float f (int x, float y)
        BuiltInTypeSymbol returnType = (BuiltInTypeSymbol) currentScope.resolve("float");
        if (returnType == null) {
            System.out.println("TIPO NO DECLARADO");
            return;
        }

        System.out.println("line 02 - global : ref float");
        BuiltInTypeSymbol t2 = (BuiltInTypeSymbol) currentScope.resolve("int");
        if (t2 == null) {
            System.out.println("TIPO NO DECLARADO");
            return;
        }

        BuiltInTypeSymbol t3 = (BuiltInTypeSymbol) currentScope.resolve("int");
        if (t3 == null) {
            System.out.println("TIPO NO DECLARADO");
            return;
        }

        System.out.println("line 02 - global : ref int");
        System.out.println("line 02 - global : ref float");

        System.out.println("function f : \n\t{");

        VariableSymbol parameters[] = { new VariableSymbol("x", t2), new VariableSymbol("y", t3) };
        currentScope = new MethodSymbol("f", parameters, currentScope);
        System.out.println("\t  line 02 - function f : def x");
        System.out.println("\t  line 02 - function f : def y");

        // float i;
        t1 = (BuiltInTypeSymbol) currentScope.resolve("float");
        if (t1 == null) {
            System.out.println("TIPO NO DECLARADO");
            return;
        }

        System.out.println("\t  line 04 - function f : def float");

        currentScope.define(new VariableSymbol("i", t1));
        System.out.println("\t  line 04 - function f : def i");

        currentScope = new LocalScope(currentScope);
        System.out.println("\t  Block 1: \n\t\t{");
        // {float z = x+y; i = z}
        t1 = (BuiltInTypeSymbol) currentScope.resolve("float");
        if (t1 == null) {
            System.out.println("TIPO NO DECLARADO");
            return;
        }
        System.out.println("\t\t  line 05 - block 1 : ref float");

        currentScope.define(new VariableSymbol("z", t1));

        System.out.println("\t\t  line 05 - block 1 : def z");

        VariableSymbol v1 = (VariableSymbol) currentScope.resolve("x");
        if (v1 == null) {
            System.out.println("VARIABLE NO DECLARADA");
            return;
        }
        System.out.println("\t\t  line 05 - block 1 : ref x");

        VariableSymbol v2 = (VariableSymbol) currentScope.resolve("y");
        if (v2 == null) {
            System.out.println("VARIABLE NO DECLARADA");
            return;
        }
        System.out.println("\t\t  line 05 - block 1 : ref y");

        v1 = (VariableSymbol) currentScope.resolve("i");
        if (v1 == null) {
            System.out.println("VARIABLE NO DECLARADA");
            return;
        }
        System.out.println("\t\t  line 05 - block 1 : ref i");

        v2 = (VariableSymbol) currentScope.resolve("z");
        if (v2 == null) {
            System.out.println("VARIABLE NO DECLARADA");
            return;
        }
        System.out.println("\t\t  line 05 - block 1 : ref z");

        currentScope = currentScope.getEnclosingScope();
        System.out.println("\t\t}");

        currentScope = new LocalScope(currentScope);

        System.out.println("\t  Block 2: \n\t\t{");
        // {float z = i+1; i = z;}
        t1 = (BuiltInTypeSymbol) currentScope.resolve("float");
        if (t1 == null) {
            System.out.println("TIPO NO DECLARADO");
            return;
        }
        System.out.println("\t\t  line 06 - block 2 : ref float");

        currentScope.define(new VariableSymbol("z", t1));

        System.out.println("\t\t  line 06 - block 2 : def z");

        v1 = (VariableSymbol) currentScope.resolve("i");
        if (v1 == null) {
            System.out.println("VARIABLE NO DECLARADA");
            return;
        }
        System.out.println("\t\t  line 06 - block 2 : ref i");

        v1 = (VariableSymbol) currentScope.resolve("i");
        if (v1 == null) {
            System.out.println("VARIABLE NO DECLARADA");
            return;
        }
        System.out.println("\t\t  line 06 - block 2 : ref i");

        v2 = (VariableSymbol) currentScope.resolve("z");
        if (v2 == null) {
            System.out.println("VARIABLE NO DECLARADA");
            return;
        }
        System.out.println("\t\t  line 06 - block 2 : ref z");

        currentScope = currentScope.getEnclosingScope();

        System.out.println("\t\t}");
        // return i;
        v1 = (VariableSymbol) currentScope.resolve("i");
        if (v1 == null) {
            System.out.println("VARIABLE NO DECLARADA");
            return;
        }
        System.out.println("\t  line 07 - function f : ref i");
        System.out.println("\t}");
        currentScope = currentScope.getEnclosingScope();
        // g()

        returnType = (BuiltInTypeSymbol) currentScope.resolve("void");
        if (returnType == null) {
            System.out.println("TIPO NO DECLARADO");
            return;
        }

        currentScope = new MethodSymbol("g", null, currentScope);

        System.out.println("line 09 - global : ref void");
        System.out.println("function g : \n\t{");

        System.out.println("\t  line 11 - function g : ref f");

        v1 = (VariableSymbol) currentScope.resolve("i");
        if (v1 == null) {
            System.out.println("VARIABLE NO DECLARADA");
            return;
        }
        System.out.println("\t  line 11 - function g : ref i");

        currentScope = currentScope.getEnclosingScope();
        System.out.println("\t}");

    }
}
