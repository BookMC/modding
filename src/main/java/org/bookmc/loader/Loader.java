package org.bookmc.loader;

import org.bookmc.loader.book.BookModDiscoverer;
import org.bookmc.loader.book.DevelopmentModDiscoverer;
import org.bookmc.loader.vessel.ModVessel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Loader {
    private static final List<MinecraftModDiscoverer> discoverers = new ArrayList<>();
    private static final List<ModVessel> modVessels = new ArrayList<>();

    public static void registerModDiscoverer(MinecraftModDiscoverer minecraftModDiscoverer) {
        discoverers.add(minecraftModDiscoverer);
    }

    public static List<MinecraftModDiscoverer> getModDiscoverers() {
        return Collections.unmodifiableList(discoverers);
    }

    public static void registerVessel(ModVessel vessel) {
        modVessels.add(vessel);
    }

    public static List<ModVessel> getModVessels() {
        return Collections.unmodifiableList(modVessels);
    }

    static {
        // Register default mod loader.
        Loader.registerModDiscoverer(new BookModDiscoverer());
        Loader.registerModDiscoverer(new DevelopmentModDiscoverer());
    }
}
