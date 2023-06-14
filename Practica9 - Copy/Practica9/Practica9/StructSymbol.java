package Practica9;

import java.util.LinkedHashMap;
import java.util.Map;

public class StructSymbol extends ScopedSymbol implements Type, Scope {
    Map<String, Symbol> fields = new LinkedHashMap<String, Symbol>();

    public StructSymbol(String name, Scope parent) {
        super(name, parent);
    }

    public Symbol resolve(String name) {
        Symbol s = fields.get(name);

        if (s != null)
            return s;

        if (parent != null)
            return parent.resolve(name);

        return null;
    }

    @Override
    public String toString() {
        return "struct " + name + " :{" + fields.toString() + "}\n";
    }

    public Symbol getMember(String name) {
        return fields.get(name);
    }

    public Map<String, Symbol> getMembers() {
        return fields;
    }

    @Override
    public String getScopeName() {
        return parent.getScopeName();
    }

    @Override
    public Scope getEnclosingScope() {
        return parent;
    }

    @Override
    public void define(Symbol sym) {
        fields.put(sym.name, sym);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Scope getParentScope() {
        // TODO Auto-generated method stub
        return null;
    }

    // @Override
    // public Symbol resolve(String name) {
    // Symbol s = fields.get(name);
    // if (s != null) {
    // return s;
    // }
    // if (getParentScope() != null) {
    // return getParentScope().resolve(name);
    // }
    // return null;
    // }

    // @Override
    // public Scope getParentScope() {
    // return parent;
    // }

}
