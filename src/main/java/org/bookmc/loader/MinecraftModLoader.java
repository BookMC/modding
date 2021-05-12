package org.bookmc.loader;

import java.io.File;

public interface MinecraftModLoader {
    /**
     * A method to implement simple modloading
     * @param files Array of files to be loaded via the implementation
     */
    void load(File[] files);
}
