package Practica9;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseScope implements Scope {
    Scope enclosingScope;
    Map<String, Symbol> member = new HashMap<String, Symbol>();

    public BaseScope(Scope currentScope) {
        this.enclosingScope = currentScope;
    }

    public Scope getEnclosingScope() {
        return enclosingScope;
    }

    public void define(Symbol sym) {
        member.put(sym.name, sym);
    }

    public Symbol resolve(String name) {
        Symbol s = member.get(name);

        if (s != null)
            return s;

        if (enclosingScope != null)
            return enclosingScope.resolve(name);
        return null;
    }

}
