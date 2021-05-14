package org.bookmc.loader;

import org.bookmc.loader.exception.MissingDependencyException;
import org.bookmc.loader.vessel.ModVessel;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class BookModLoader {
    private static final List<ModVessel> loaded = new ArrayList<>();

    public static void load() {
        for (ModVessel vessel : Loader.getModVessels()) {
            if (loaded.contains(vessel)) {
                continue;
            }

            loadDependencies(vessel);

            // In the chances of someone for some stupid reason decided to add their own mod as a dependency
            if (!loaded.contains(vessel)) {
                load(vessel);
            }
        }
    }

    private static void loadDependencies(ModVessel vessel) {
        for (String dependency : vessel.getDependencies()) {
            // I believe this will become O(n^2), is there a better way to this?
            boolean isFound = false;
            for (ModVessel dependencyVessel : Loader.getModVessels()) {
                if (dependencyVessel.getId().equals(dependency)) {
                    loadDependencies(dependencyVessel);
                    load(dependencyVessel);
                    isFound = true;
                    break;
                }
            }

            if (!isFound) {
                throw new MissingDependencyException(dependency);
            }
        }
    }

    private static void load(ModVessel vessel) {
        String[] split = vessel.getEntrypoint().split("::");

        Class<?> entryClass = null;

        if (vessel.getClassLoader() != null) {
            try {
                entryClass = Class.forName(split[0], true, vessel.getClassLoader());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                entryClass = Class.forName(split[0]);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        loaded.add(vessel);
        try {
            if (entryClass != null) {
                entryClass.getDeclaredMethod(split[1]).invoke(entryClass.getConstructor().newInstance());
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
