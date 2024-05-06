package org.example.test.antimobbuild.exeptions;

import java.util.List;

public class SpawnFromEggsException {
    List<String> worlds;
    List<String> mobs;
    boolean isEnabled;

    public SpawnFromEggsException(List<String> worlds, List<String> mobs, boolean isEnabled) {
        this.worlds = worlds;
        this.mobs = mobs;
        this.isEnabled = isEnabled;
    }

    public List<String> getWorlds() {
        return worlds;
    }

    public List<String> getMobs() {
        return mobs;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

}
