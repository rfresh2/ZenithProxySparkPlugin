package dev.zenith.spark;

import com.zenith.plugin.api.Plugin;
import com.zenith.plugin.api.PluginAPI;
import com.zenith.plugin.api.ZenithProxyPlugin;
import dev.zenith.spark.command.SparkCommand;
import me.lucko.spark.common.SparkPlatform;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

@Plugin(
    id = "spark",
    version = BuildConstants.VERSION,
    description = "ZenithProxy Spark Plugin",
    url = "https://github.com/rfresh2/ZenithProxySparkPlugin",
    authors = {"rfresh2"},
    mcVersions = {"*"}
)
public class ZenithSparkPlugin implements ZenithProxyPlugin {
    public static ComponentLogger LOG;
    public static SparkPlatform SPARK_PLATFORM;
    public static SparkPluginImpl SPARK_PLUGIN;

    @Override
    public void onLoad(PluginAPI pluginAPI) {
        LOG = pluginAPI.getLogger();
        LOG.info("Spark Plugin loading...");
        SPARK_PLUGIN = new SparkPluginImpl();
        SPARK_PLATFORM = new SparkPlatform(SPARK_PLUGIN);
        SPARK_PLATFORM.enable();
        pluginAPI.registerCommand(new SparkCommand());
        LOG.info("Spark Plugin loaded!");
    }
}
