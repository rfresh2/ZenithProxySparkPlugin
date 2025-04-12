package dev.zenith.spark;

import me.lucko.spark.common.sampler.source.ClassSourceLookup;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.Map;

import static com.zenith.Globals.PLUGIN_MANAGER;

public class ZenithClassSourceLookup extends ClassSourceLookup.ByClassLoader {
    private final Map<ClassLoader, String> pluginClassloaders;

    public ZenithClassSourceLookup() {
        this.pluginClassloaders = new HashMap<>();
        for (var pluginInstance : PLUGIN_MANAGER.getPluginInstances()) {
            pluginClassloaders.put(pluginInstance.getClassLoader(), pluginInstance.getId());
        }
    }

    @Override
    public @Nullable String identify(final ClassLoader classLoader) throws Exception {
        return pluginClassloaders.get(classLoader);
    }
}
