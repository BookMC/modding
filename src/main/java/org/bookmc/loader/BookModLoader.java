package org.bookmc.loader;

import org.bookmc.loader.vessel.ModVessel;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class BookModLoader {
    private static final List<ModVessel> loaded = new ArrayList<>();

    public static void load() {
        try {
            for (ModVessel vessel : Loader.getModVessels()) {
                if (loaded.contains(vessel)) {
                    continue;
                }

                for (String dependency : vessel.getDependencies()) {
                    // I believe this will become O(n^2), is there a better way to this?
                    for (ModVessel dependencyVessel : Loader.getModVessels()) {
                        if (dependencyVessel.getId().equals(dependency)) {
                            load(dependencyVessel);
                            return;
                        }
                    }
                }

                // In the chances of someone for some stupid reason decided to add their own mod as a dependency
                if (!loaded.contains(vessel)) {
                    load(vessel);
                }

            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static void load(ModVessel vessel) throws Throwable {
        String[] split = vessel.getEntrypoint().split("::");

        Class<?> entryClass;

        if (vessel.getClassLoader() != null) {
            entryClass = Class.forName(split[0], true, vessel.getClassLoader());
        } else {
            entryClass = Class.forName(split[0]);
        }

        loaded.add(vessel);
        entryClass.getDeclaredMethod(split[1]).invoke(entryClass.getConstructor().newInstance());
    }
}
