package org.bookmc.loader;

import org.bookmc.loader.classloader.BookModClassLoader;
import org.bookmc.loader.vessel.ModVessel;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class BookModLoader {
    public static void load() {
        try {
            for (ModVessel vessel : Loader.getModVessels()) {
                String[] split = vessel.getEntrypoint().split("::");

                File file = vessel.getFile();



                Class<?> entryClass;

                if (!file.getAbsolutePath().equals("there_is_no_mod_to_see")) {
                    entryClass = Class.forName(split[0], true, new BookModClassLoader(file));
                } else {
                    entryClass = Class.forName(split[0]);
                }

                MethodHandles.publicLookup()
                    .findVirtual(entryClass, split[1], MethodType.methodType(void.class))
                    .invoke();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
