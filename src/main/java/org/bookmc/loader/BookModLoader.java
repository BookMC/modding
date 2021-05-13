package org.bookmc.loader;

import org.bookmc.loader.vessel.ModVessel;

import java.io.File;

public class BookModLoader {
    public static void load() {
        try {
            for (ModVessel vessel : Loader.getModVessels()) {
                String[] split = vessel.getEntrypoint().split("::");

                Class<?> entryClass;

                if (vessel.getClassLoader() != null) {
                    entryClass = Class.forName(split[0], true, vessel.getClassLoader());
                } else {
                    entryClass = Class.forName(split[0]);
                }

                entryClass.getDeclaredMethod(split[1]).invoke(entryClass.getConstructor().newInstance());
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
