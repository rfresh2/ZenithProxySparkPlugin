package dev.zenith.spark;

import com.zenith.plugins.PluginManager;
import me.lucko.spark.common.SparkPlugin;
import me.lucko.spark.common.command.sender.CommandSender;
import me.lucko.spark.common.platform.PlatformInfo;
import me.lucko.spark.common.tick.TickHook;
import me.lucko.spark.common.tick.TickReporter;

import java.nio.file.Path;
import java.util.logging.Level;
import java.util.stream.Stream;

import static com.zenith.Shared.EXECUTOR;

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
        return Stream.of(new ZenithSparkCommandSender("Spark", null, null));
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
    public TickHook createTickHook() {
        return new ZenithTickHook();
    }

    @Override
    public TickReporter createTickReporter() {
        return new ZenithTickReporter();
    }

}
