package Practica9;

import java.util.HashMap;
import java.util.Map;

public class MethodSymbol extends Symbol implements Scope {
    Scope enclosingScope;
    public Map<String, Symbol> member = new HashMap<String, Symbol>();

    public MethodSymbol(String name, VariableSymbol[] orderedArgs, Scope enclosingScope) {
        super(name);
        this.enclosingScope = enclosingScope;

        if (orderedArgs != null) {
            for (VariableSymbol v : orderedArgs) {
                define(v);
            }
        }
    }

    @Override
    public String getScopeName() {
        return name;
    }

    @Override
    public Scope getEnclosingScope() {
        return enclosingScope;
    }

    public void setEnclosingScope(Scope enclosingScope) {
        this.enclosingScope = enclosingScope;
    }

    public Map<String, Symbol> getMember() {
        return this.member;
    }

    public void setMember(Map<String, Symbol> member) {
        this.member = member;
    }

    @Override
    public void define(Symbol sym) {
        member.put(sym.name, sym);
    }

    @Override
    public Symbol resolve(String name) {
        Symbol s = member.get(name);

        if (s != null) {
            return s;
        }

        if (enclosingScope != null) {
            return enclosingScope.resolve(name);
        }
        return null;
    }

    @Override
    public String toString() {
        return "{" + name + "(" + ") " + getMember() + "}";
    }

    @Override
    public Scope getParentScope() {
        return null;
    }

}
