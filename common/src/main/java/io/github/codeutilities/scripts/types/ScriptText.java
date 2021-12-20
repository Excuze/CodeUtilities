package io.github.codeutilities.scripts.types;

public record ScriptText(String value) implements ScriptValue {

    @Override
    public String text() {
        return value;
    }

    @Override
    public double number() {
        try {
            return Double.parseDouble(value);
        } catch (Exception ignored) {
            return 0;
        }
    }

    @Override
    public ScriptValue copy() {
        return new ScriptText(value);
    }

    @Override
    public String toString() {
        return text();
    }
}