package Practica9;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable implements Scope {

    public Map<String, Symbol> symbols = new HashMap<String, Symbol>();

    public SymbolTable() {
        initTypeSystem();
    }

    protected void initTypeSystem() {
        define(new BuiltInTypeSymbol("enteras"));
        define(new BuiltInTypeSymbol("flotantes"));
    }

    @Override
    public String getScopeName() {
        return "global";
    }

    @Override
    public Scope getEnclosingScope() {
        return null;
    }

    @Override
    public void define(Symbol sym) {
        symbols.put(sym.name, sym);
    }

    @Override
    public Symbol resolve(String name) {
        return symbols.get(name);
    }

    @Override
    public String toString() {
        return getScopeName() + ":" + symbols;
    }

}
