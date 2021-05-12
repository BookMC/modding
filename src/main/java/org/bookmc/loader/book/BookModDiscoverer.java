package org.bookmc.loader.book;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bookmc.loader.Loader;
import org.bookmc.loader.MinecraftModDiscoverer;
import org.bookmc.loader.vessel.json.JsonModVessel;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class BookModDiscoverer implements MinecraftModDiscoverer {
    private static final String LOADER_JSON_FILE = "book.mod.json";

    @Override
    public void discover(File[] files) {
        for (File file : files) {
            String name = file.getName();

            // Let's not waste our time, shall we?
            if (!(name.endsWith(".zip") || name.endsWith(".jar"))) continue;

            try {
                ZipFile zipFile = new ZipFile(file);

                Enumeration<? extends ZipEntry> entries = zipFile.entries();

                while (entries.hasMoreElements()) {
                    ZipEntry entry = entries.nextElement();

                    if (entry.getName().equals(LOADER_JSON_FILE)) {
                        try (InputStream inputStream = zipFile.getInputStream(entry)) {
                            try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {
                                JsonArray mods = JsonParser.parseReader(inputStreamReader).getAsJsonArray();

                                for (int i = 0; i < mods.size(); i++) {
                                    JsonObject mod = mods.get(i).getAsJsonObject();
                                    Loader.registerVessel(new JsonModVessel(mod, file));
                                }
                            }
                        }
                        break;
                    }
                }
            } catch (Throwable exception) {
                exception.printStackTrace();
            }
        }
    }
}
