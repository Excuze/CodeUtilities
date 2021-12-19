package io.github.codeutilities.scripts.action;

public enum ScriptActionCategory {

    PLAYER("Player"),
    REPEAT("Repeat"),
    VALUE("Value"),
    FILES("Files"),
    NUMBER("Number"),
    DICTIONARY("Dictionary"),
    TEXT("Text"),
    LIST("List"),
    SCRIPT("Script"),
    RENDER("Render");

    final String name;
    ScriptActionCategory(String name) {
        this.name = name;
    }

    public static ScriptActionCategory get(String name) {
        for (ScriptActionCategory category : values()) {
            if (category.name.equals(name)) {
                return category;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
