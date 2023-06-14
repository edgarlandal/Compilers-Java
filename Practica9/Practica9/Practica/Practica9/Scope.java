package Practica9;

public interface Scope {
    public String getScopeName();

    public Scope getEnclosingScope();

    public Scope getParentScope();

    public void define(Symbol sym);

    public Symbol resolve(String name);
}
