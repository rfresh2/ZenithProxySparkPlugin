package dev.zenith.spark.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.zenith.command.Command;
import com.zenith.command.CommandUsage;
import com.zenith.command.brigadier.CommandCategory;
import com.zenith.command.brigadier.CommandContext;
import dev.zenith.spark.ZenithSparkCommandSender;
import dev.zenith.spark.ZenithSparkPlugin;
import org.geysermc.mcprotocollib.auth.GameProfile;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;

public class SparkCommand extends Command {
    @Override
    public CommandUsage commandUsage() {
        return CommandUsage.builder()
            .name("spark")
            .category(CommandCategory.MODULE)
            .description("""
              Manages the Spark memory and CPU profiler.
              """)
            .usageLines(
                "<command>"
            )
            .build();
    }

    @Override
    public LiteralArgumentBuilder<CommandContext> register() {
        return command("spark")
            .then(argument("args", greedyString()).executes(c -> {
                ZenithSparkCommandSender sender = switch (c.getSource().getSource()) {
                    case TERMINAL -> new ZenithSparkCommandSender("Terminal", null, null);
                    case DISCORD -> new ZenithSparkCommandSender("Discord", null, null);
                    case SPECTATOR, IN_GAME_PLAYER -> {
                        var session = c.getSource().getInGamePlayerInfo().session();
                        GameProfile profile = session.getProfileCache().getProfile();
                        yield new ZenithSparkCommandSender(profile == null ? session.getUsername() : profile.getName(), profile == null ? session.getLoginProfileUUID() : profile.getId(), session);
                    }
                };
                ZenithSparkPlugin.SPARK_PLATFORM.executeCommand(sender, getString(c, "args").split(" "));
                c.getSource().setNoOutput(true); // handoff output to spark
                return OK;
            }));
    }
}
