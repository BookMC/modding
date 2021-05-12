package org.bookmc.loader.vessel.json;

import com.google.gson.JsonObject;
import org.bookmc.loader.vessel.ModVessel;

public class JsonModVessel implements ModVessel {
    private final JsonObject object;

    public JsonModVessel(JsonObject object) {
        this.object = object;
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
}
