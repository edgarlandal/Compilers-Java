package Practica9;

public class Symbol {
    public String name;
    public Type type;
    public String value;

    public Symbol(String name, Type type, String value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public Symbol(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        if (type != null) {
            return '<' + getName() + ":" + type + ':' + value + '>';
        }
        return getName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
