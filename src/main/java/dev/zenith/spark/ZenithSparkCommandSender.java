package dev.zenith.spark;

import com.zenith.discord.Embed;
import com.zenith.network.server.ServerSession;
import com.zenith.util.ComponentSerializer;
import me.lucko.spark.common.command.sender.CommandSender;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

import static com.zenith.Globals.DISCORD;

public class ZenithSparkCommandSender implements CommandSender {
    @NotNull private final String name;
    @Nullable private final UUID uuid;
    @Nullable private final ServerSession session;

    public ZenithSparkCommandSender(final @NotNull String name, final @Nullable UUID uuid, final @Nullable ServerSession session) {
        this.name = name;
        this.uuid = uuid;
        this.session = session;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UUID getUniqueId() {
        return uuid;
    }

    @Override
    public void sendMessage(final Component component) {
        String text = ComponentSerializer.serializePlain(component).trim();
        if (!text.isBlank()) {
            // todo: queue and accumulate these messages
            //  spark likes to send a lot of single-line components
            //  like see `spark help` output
            //  would reduce discord spam if we could send these in a single embed
            DISCORD.sendEmbedMessage(
                Embed.builder().description(text));
        }
        // embed not logged because it has no title so we have to do it manually
        ZenithSparkPlugin.LOG.info(component);
        if (session != null) {
            session.sendAsyncMessage(component);
        }
    }

    @Override
    public boolean hasPermission(final String s) {
        return true;
    }
}
