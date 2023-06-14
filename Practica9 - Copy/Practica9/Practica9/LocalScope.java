package Practica9;

public class LocalScope extends BaseScope {

    public LocalScope(Scope currentScope) {
        super(currentScope);
    }

    @Override
    public String getScopeName() {
        return "Local";
    }

    @Override
    public String toString() {
        return "(" + this.getScopeName() + " : " + member + ")";
    }

    @Override
    public Scope getParentScope() {
        // TODO Auto-generated method stub
        return null;
    }

}
