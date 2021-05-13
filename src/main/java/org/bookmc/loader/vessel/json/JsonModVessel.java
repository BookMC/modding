package org.bookmc.loader.vessel.json;

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
    public String getAuthor() {
        return object.get("author").getAsString();
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
}
