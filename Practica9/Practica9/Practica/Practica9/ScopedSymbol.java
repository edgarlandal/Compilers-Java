package Practica9;

public abstract class ScopedSymbol extends Symbol {
    String name;
    Scope parent;

    public ScopedSymbol(String name, Type type, Scope parent) {
        super(name, type);
        this.name = name;
        this.parent = parent;
    }

    public ScopedSymbol(String name, Scope parent) {
        super(name);
        this.name = name;
        this.parent = parent;
    }
}
