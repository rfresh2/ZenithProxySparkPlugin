package dev.zenith.spark;

import me.lucko.spark.common.platform.PlatformInfo;

import static com.zenith.Globals.LAUNCH_CONFIG;
import static com.zenith.Globals.MC_VERSION;

public class ZenithPlatformInfo implements PlatformInfo {
    public static final ZenithPlatformInfo INSTANCE = new ZenithPlatformInfo();
    @Override
    public Type getType() {
        return Type.PROXY;
    }

    @Override
    public String getName() {
        return "ZenithProxy";
    }

    @Override
    public String getBrand() {
        return "ZenithProxy";
    }

    @Override
    public String getVersion() {
        return LAUNCH_CONFIG.version;
    }

    @Override
    public String getMinecraftVersion() {
        return MC_VERSION;
    }
}
