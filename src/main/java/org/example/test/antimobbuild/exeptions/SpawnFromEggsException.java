package org.example.test.antimobbuild.exeptions;

import java.util.List;

public class SpawnFromEggsException {
    String world;
    List<String> mobs;
    boolean isEnabled;

    public SpawnFromEggsException(String world, List<String> mobs, boolean isEnabled) {
        this.world = world;
        this.mobs = mobs;
        this.isEnabled = isEnabled;
    }

    public String getWorld() {
        return world;
    }

    public List<String> getMobs() {
        return mobs;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

}
