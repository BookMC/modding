package org.bookmc.loader.vessel;

import java.io.File;

public interface ModVessel {
    String getName();

    String getId();

    String getAuthor();

    String getVersion();

    String getEntrypoint();

    File getFile();

    String getMixinEntrypoint();

    ClassLoader getClassLoader();

    String[] getDependencies();
}
