package Practica9;

public class GlobalScope extends BaseScope {

    public GlobalScope() {
        super(null);
    }

    @Override
    public String getScopeName() {
        return "Global";
    }

    @Override
    public String toString() {
        return "{" + this.getScopeName() + " : " + member + "}";
    }

    @Override
    public Scope getParentScope() {
        return null;
    }

}
