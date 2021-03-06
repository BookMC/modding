package org.bookmc.loader.book;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.bookmc.loader.Loader;
import org.bookmc.loader.MinecraftModDiscoverer;
import org.bookmc.loader.vessel.json.JsonModVessel;
import org.bookmc.loader.vessel.json.library.LibraryModVessel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DevelopmentModDiscoverer implements MinecraftModDiscoverer {
    @Override
    public void discover(File[] files) {
        // (Files are useless to us)


        try (InputStream inputStream = this.getClass().getResourceAsStream("/book.mod.json")) {
            if (inputStream != null) {
                try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {
                    JsonArray mods = parser.parse(inputStreamReader).getAsJsonArray();

                    for (int i = 0; i < mods.size(); i++) {
                        JsonObject mod = mods.get(i).getAsJsonObject();

                        if (mod.has("library") && mod.get("library").getAsBoolean()) {
                            Loader.registerVessel(new LibraryModVessel(mod, new File("there_is_no_mod_to_see")));
                        } else {
                            Loader.registerVessel(new JsonModVessel(mod, new File("there_is_no_mod_to_see")));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
