package org.bookmc.loader;

import org.bookmc.loader.classloader.BookModClassLoader;
import org.bookmc.loader.vessel.ModVessel;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class BookModLoader {
    public static void load() {
        try {
            for (ModVessel vessel : Loader.getModVessels()) {
                String[] split = vessel.getEntrypoint().split("::");

                Class<?> entryClass = Class.forName(split[0], true, new BookModClassLoader(vessel.getFile()));

                MethodHandles.publicLookup()
                    .findVirtual(entryClass, split[1], MethodType.methodType(void.class))
                    .invoke();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
