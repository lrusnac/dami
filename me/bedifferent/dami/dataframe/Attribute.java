package me.bedifferent.dami.dataframe;

/*
 * This is an immutable class that represents a cell in my dataset
 */
public class Attribute {
    public enum Type {
        NUMERIC, LITERAL, NOMINAL
    }

    private Type type;
    private Double numeric = Double.NaN;
    private String literal = "NaN";

    public Attribute(String value) {
        this.literal = value;
        this.type = Type.LITERAL;
    }

    public Attribute(Double value) {
        this.numeric = value;
        this.type = Type.NUMERIC;
    }

    public Attribute(Integer value) {
        this(value.doubleValue());
    }

    public Type getType() {
        return type;
    }

    public Double getNumeric() {
        return numeric;
    }

    public String getLiteral() {
        return literal;
    }

    public String toString() {
        switch (this.type) {
        case NUMERIC:
            return this.numeric.toString();
        case LITERAL:
            return this.literal;
        case NOMINAL:
            return "not implemented";
        default:
            return "undefined";
        }
    }
}
