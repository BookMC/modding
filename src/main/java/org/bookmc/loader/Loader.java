package org.bookmc.loader;

import org.bookmc.loader.book.BookModDiscoverer;
import org.bookmc.loader.vessel.ModVessel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Loader {
    private static final List<MinecraftModDiscoverer> modLoaders = new ArrayList<>();
    private static final List<ModVessel> modVessels = new ArrayList<>();

    public static void registerModLoader(MinecraftModDiscoverer minecraftModDiscoverer) {
        modLoaders.add(minecraftModDiscoverer);
    }

    public static List<MinecraftModDiscoverer> getModLoaders() {
        return Collections.unmodifiableList(modLoaders);
    }

    public static void registerVessel(ModVessel vessel) {
        modVessels.add(vessel);
    }

    public static List<ModVessel> getModVessels() {
        return Collections.unmodifiableList(modVessels);
    }

    static {
        // Register default mod loader.
        Loader.registerModLoader(new BookModDiscoverer());
    }
}
