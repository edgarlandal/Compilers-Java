package Practica9;

import java.util.LinkedHashMap;
import java.util.Map;

public class ClassSymbol extends ScopedSymbol implements Type, Scope {
    Map<String, Symbol> fields = new LinkedHashMap<String, Symbol>();
    Scope enclosingScope;

    public ClassSymbol(String name, Scope parent, Scope enclosingScope) {
        super(name, parent);
        this.enclosingScope = enclosingScope;
        this.parent = parent;
    }

    @Override
    public String getScopeName() {
        return name;
    }

    @Override
    public Scope getEnclosingScope() {
        return enclosingScope;
    }

    @Override
    public void define(Symbol sym) {
        fields.put(sym.name, sym);
    }

    @Override
    public Symbol resolve(String name) {
        Symbol s = fields.get(name);

        if (s != null)
            return s;

        if (getParentScope() != null)
            return getParentScope().resolve(name);

        return null;
    }

    @Override
    public Scope getParentScope() {
        return parent;
    }

    @Override
    public String toString() {
        if (parent.getScopeName() != "Global") {
            return "class " + name + " : " + parent.toString() + "{" + fields.toString() + "}";
        }
        return "class " + name + " :" + "{" + fields.toString() + "}";
    }

}
