package me.bedifferent.dami.dataframe;

/*
 * This is an immutable class that represents a cell in my dataset
 */
public class Attribute {
    public enum Type {
        NUMERIC, LITERAL, NOMINAL, SET
    }

    private Type type;
    private Double numeric = Double.NaN;
    private String literal = "NaN";

    public Attribute(String value) {
        this.literal = value.toLowerCase();
        this.type = Type.LITERAL;
    }

    public Attribute(Double value) {
        this.numeric = value;
        this.type = Type.NUMERIC;
    }

    public Attribute(Integer value) {
        this(value.doubleValue());
    }

    public Attribute(Object obj, Type type) {
        switch (type) {
        case NUMERIC:
            try {
                this.numeric = Double.parseDouble(obj.toString());
            } catch (NumberFormatException e) {
                this.numeric = Double.NaN;
            }
            break;
        case LITERAL:
            this.literal = obj.toString().toLowerCase();
            break;
        default:
            System.err.println("this type is not implemented yet");
        }
        this.type = type;
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
        case SET:
            return "not implemented";
        default:
            return "undefined";
        }
    }
}
