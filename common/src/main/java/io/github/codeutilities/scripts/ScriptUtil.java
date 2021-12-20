package io.github.codeutilities.scripts;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.github.codeutilities.CodeUtilities;
import io.github.codeutilities.scripts.types.ScriptDictionary;
import io.github.codeutilities.scripts.types.ScriptList;
import io.github.codeutilities.scripts.types.ScriptNumber;
import io.github.codeutilities.scripts.types.ScriptText;
import io.github.codeutilities.scripts.types.ScriptUnknownValue;
import io.github.codeutilities.scripts.types.ScriptValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;

public class ScriptUtil {

    public static ScriptValue tagToValue(Tag tag) {
        if (tag instanceof CompoundTag ct) {
            HashMap<String, ScriptValue> dict = new HashMap<>();
            for (String key : ct.getAllKeys()) {
                dict.put(key, tagToValue(ct.get(key)));
            }
            return new ScriptDictionary(dict);
        } else if (tag instanceof DoubleTag dt) {
            return new ScriptNumber(dt.getAsDouble());
        } else if (tag instanceof FloatTag ft) {
            return new ScriptNumber(ft.getAsDouble());
        } else if (tag instanceof IntArrayTag it) {
            ArrayList<ScriptValue> list = new ArrayList<>();
            for (IntTag i : it) {
                list.add(new ScriptNumber(i.getAsDouble()));
            }
            return new ScriptList(list);
        } else if (tag instanceof IntTag it) {
            return new ScriptNumber(it.getAsDouble());
        } else if (tag instanceof ListTag lt) {
            ArrayList<ScriptValue> list = new ArrayList<>();
            for (Tag t : lt) {
                list.add(tagToValue(t));
            }
            return new ScriptList(list);
        } else if (tag instanceof LongArrayTag lt) {
            ArrayList<ScriptValue> list = new ArrayList<>();
            for (LongTag i : lt) {
                list.add(new ScriptNumber(i.getAsDouble()));
            }
            return new ScriptList(list);
        } else if (tag instanceof ShortTag st) {
            return new ScriptNumber(st.getAsDouble());
        } else if (tag instanceof StringTag st) {
            return new ScriptText(st.getAsString());
        } else if (tag instanceof ByteTag bt) {
            return new ScriptNumber(bt.getAsDouble());
        } else if (tag instanceof ByteArrayTag bt) {
            ArrayList<ScriptValue> list = new ArrayList<>();
            for (ByteTag i : bt) {
                list.add(new ScriptNumber(i.getAsDouble()));
            }
            return new ScriptList(list);
        }

        CodeUtilities.LOGGER.warn("Unknown tag type: " + tag.getClass().getName());

        return new ScriptUnknownValue();
    }

    public static Tag valueToTag(ScriptValue value) {
        if (value instanceof ScriptDictionary dict) {
            CompoundTag ct = new CompoundTag();
            for (String key : dict.dictionary().keySet()) {
                ct.put(key, valueToTag(dict.dictionary().get(key)));
            }
            return ct;
        } else if (value instanceof ScriptList list) {
            ListTag lt = new ListTag();
            for (ScriptValue v : list.list()) {
                lt.add(valueToTag(v));
            }
            return lt;
        } else if (value instanceof ScriptNumber number) {
            return DoubleTag.valueOf(number.number());
        } else if (value instanceof ScriptText text) {
            return StringTag.valueOf(text.text());
        }

        return IntTag.valueOf(0);
    }

    public static JsonElement valueToJson(ScriptValue value) {
        if (value instanceof ScriptDictionary dict) {
            JsonObject json = new JsonObject();
            for (String key : dict.dictionary().keySet()) {
                json.add(key, valueToJson(dict.dictionary().get(key)));
            }
            return json;
        } else if (value instanceof ScriptList list) {
            JsonArray json = new JsonArray();
            for (ScriptValue v : list.list()) {
                json.add(valueToJson(v));
            }
            return json;
        } else if (value instanceof ScriptNumber number) {
            return new JsonPrimitive(number.number());
        } else if (value instanceof ScriptText text) {
            return new JsonPrimitive(text.text());
        }

        return JsonNull.INSTANCE;
    }

    public static ScriptValue jsonToValue(JsonElement json) {
        if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            HashMap<String, ScriptValue> dict = new HashMap<>();
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                dict.put(entry.getKey(), jsonToValue(entry.getValue()));
            }
            return new ScriptDictionary(dict);
        } else if (json.isJsonArray()) {
            JsonArray jsonArray = json.getAsJsonArray();
            ArrayList<ScriptValue> list = new ArrayList<>();
            for (JsonElement element : jsonArray) {
                list.add(jsonToValue(element));
            }
            return new ScriptList(list);
        } else if (json.isJsonPrimitive()) {
            JsonPrimitive jsonPrimitive = json.getAsJsonPrimitive();
            if (jsonPrimitive.isNumber()) {
                return new ScriptNumber(jsonPrimitive.getAsDouble());
            } else if (jsonPrimitive.isString()) {
                return new ScriptText(jsonPrimitive.getAsString());
            }
        }

        return new ScriptUnknownValue();
    }
}
