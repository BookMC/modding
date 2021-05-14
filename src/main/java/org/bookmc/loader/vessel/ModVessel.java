package org.bookmc.loader.vessel;

import java.io.File;

public interface ModVessel {
    String getName();

    String getId();

    @Deprecated
    String getAuthor();

    String[] getAuthors();

    String getVersion();

    String getEntrypoint();

    File getFile();

    String getMixinEntrypoint();

    ClassLoader getClassLoader();

    String[] getDependencies();
}
