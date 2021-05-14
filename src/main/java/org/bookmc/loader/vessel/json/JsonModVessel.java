package org.bookmc.loader.vessel.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bookmc.loader.vessel.ModVessel;

import java.io.File;

public class JsonModVessel implements ModVessel {
    private final ClassLoader classLoader;
    private final JsonObject object;
    private final File file;

    public JsonModVessel(JsonObject object, File file, ClassLoader classLoader) {
        this.object = object;
        this.classLoader = classLoader;

        if (!object.has("entrypoint")) {
            throw new IllegalStateException("Nope! You cannot not load a mod without an entrypoint!");
        }

        this.file = file;
    }


    @Override
    public String getName() {
        return object.get("name").getAsString();
    }

    @Override
    public String getId() {
        return object.get("id").getAsString();
    }

    @Deprecated
    @Override
    public String getAuthor() {
        return object.has("author") ? object.get("author").getAsString() : "MysteriousDev";
    }

    @Override
    public String[] getAuthors() {
        return toString(object.get("authors").getAsJsonArray());
    }

    @Override
    public String getVersion() {
        return object.get("version").getAsString();
    }

    @Override
    public String getEntrypoint() {
        return object.get("entrypoint").getAsString();
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public String getMixinEntrypoint() {
        return object.has("mixin_entrypoint") ? object.get("mixin_entrypoint").getAsString() : null;
    }

    @Override
    public ClassLoader getClassLoader() {
        return classLoader;
    }

    @Override
    public String[] getDependencies() {
        return object.has("dependencies") ? toString(object.get("dependencies").getAsJsonArray()) : new String[0];
    }

    private String[] toString(JsonArray array) {
        int size = array.size();
        String[] strings = new String[size];

        for (int i = 0; i < size; i++) {
            strings[i] = array.get(i).getAsString();
        }

        return strings;
    }
}
