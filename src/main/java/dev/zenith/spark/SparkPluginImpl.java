package dev.zenith.spark;

import com.zenith.plugin.PluginManager;
import me.lucko.spark.common.SparkPlugin;
import me.lucko.spark.common.command.sender.CommandSender;
import me.lucko.spark.common.platform.PlatformInfo;
import me.lucko.spark.common.sampler.source.ClassSourceLookup;
import me.lucko.spark.common.sampler.source.SourceMetadata;
import me.lucko.spark.common.tick.TickHook;
import me.lucko.spark.common.tick.TickReporter;

import java.nio.file.Path;
import java.util.Collection;
import java.util.logging.Level;
import java.util.stream.Stream;

import static com.zenith.Globals.EXECUTOR;
import static com.zenith.Globals.PLUGIN_MANAGER;

public class SparkPluginImpl implements SparkPlugin {
    @Override
    public String getVersion() {
        return BuildConstants.VERSION;
    }

    @Override
    public Path getPluginDirectory() {
        return PluginManager.PLUGINS_PATH;
    }

    @Override
    public String getCommandName() {
        return "spark";
    }

    @Override
    public Stream<? extends CommandSender> getCommandSenders() {
        return Stream.empty();
    }

    @Override
    public void executeAsync(final Runnable runnable) {
        EXECUTOR.execute(runnable);
    }

    @Override
    public PlatformInfo getPlatformInfo() {
        return ZenithPlatformInfo.INSTANCE;
    }

    @Override
    public void log(final Level level, final String s) {
        if (level == Level.INFO) {
            ZenithSparkPlugin.LOG.info(s);
        } else if (level == Level.WARNING) {
            ZenithSparkPlugin.LOG.warn(s);
        } else if (level == Level.SEVERE) {
            ZenithSparkPlugin.LOG.error(s);
        } else {
            ZenithSparkPlugin.LOG.info(s);
        }
    }

    @Override
    public void log(final Level level, final String s, final Throwable throwable) {
        if (level == Level.INFO) {
            ZenithSparkPlugin.LOG.info(s, throwable);
        } else if (level == Level.WARNING) {
            ZenithSparkPlugin.LOG.warn(s, throwable);
        } else if (level == Level.SEVERE) {
            ZenithSparkPlugin.LOG.error(s, throwable);
        } else {
            ZenithSparkPlugin.LOG.info(s, throwable);
        }
    }

    @Override
    public ClassSourceLookup createClassSourceLookup() {
        return new ZenithClassSourceLookup();
    }

    @Override
    public Collection<SourceMetadata> getKnownSources() {
        return SourceMetadata.gather(
            PLUGIN_MANAGER.getPluginInstances(),
            plugin -> plugin.getPluginInfo().id(),
            plugin -> plugin.getPluginInfo().version(),
            plugin -> String.join(", ", plugin.getPluginInfo().authors()),
            plugin -> plugin.getPluginInfo().description()
        );
    }

    @Override
    public TickHook createTickHook() {
        return new ZenithTickHook();
    }

    @Override
    public TickReporter createTickReporter() {
        return new ZenithTickReporter();
    }

}
