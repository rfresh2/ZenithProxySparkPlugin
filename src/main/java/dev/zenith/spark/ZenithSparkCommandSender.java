package dev.zenith.spark;

import com.zenith.util.ComponentSerializer;
import me.lucko.spark.common.command.sender.CommandSender;
import net.kyori.adventure.text.Component;

import java.util.UUID;

import static com.zenith.Shared.DEFAULT_LOG;

public class ZenithSparkCommandSender implements CommandSender {
    public static final ZenithSparkCommandSender INSTANCE = new ZenithSparkCommandSender();
    @Override
    public String getName() {
        return "spark";
    }

    private static final UUID uuid = UUID.randomUUID();

    @Override
    public UUID getUniqueId() {
        return uuid;
    }

    @Override
    public void sendMessage(final Component component) {
        DEFAULT_LOG.info(ComponentSerializer.serializeJson(component));
    }

    @Override
    public boolean hasPermission(final String s) {
        return true;
    }
}
