package Practica9;

import java.util.ArrayList;

public class Tuplo {

    private String type;
    private ArrayList<String> parameters;
    private Tuplo trueT;
    private Tuplo falseT;
    private int line;

    public Tuplo(int line, String type, ArrayList<String> parameters, Tuplo falseT, Tuplo trueT) {
        this.line = line;
        this.type = type;
        this.parameters = parameters;
        this.trueT = trueT;
        this.falseT = falseT;
    }

    public ArrayList<String> getParameters() {
        return this.parameters;
    }

    public void setParameters(ArrayList<String> parameters) {
        this.parameters = parameters;
    }

    public Tuplo getTrueT() {
        return this.trueT;
    }

    public void setTrueT(Tuplo trueT) {
        this.trueT = trueT;
    }

    public Tuplo getFalseT() {
        return this.falseT;
    }

    public void setFalseT(Tuplo falseT) {
        this.falseT = falseT;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLine() {
        return this.line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    @Override
    public String toString() {
        return "{" +
                " line='" + getLine() + "'" +
                ", type='" + getType() + "'" +
                ", parameters='" + getParameters() + "'" +
                // ", trueT='" + getTrueT() + "'" +
                ", falseT='" + getFalseT() + "'" +
                "}";
    }
}
