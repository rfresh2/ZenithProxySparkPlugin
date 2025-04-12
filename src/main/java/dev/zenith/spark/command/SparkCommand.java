package dev.zenith.spark.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.zenith.command.api.Command;
import com.zenith.command.api.CommandCategory;
import com.zenith.command.api.CommandContext;
import com.zenith.command.api.CommandUsage;
import dev.zenith.spark.ZenithSparkCommandSender;
import dev.zenith.spark.ZenithSparkPlugin;

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
              
              Spark command documentation: https://spark.lucko.me/docs/Command-Usage
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
                        yield new ZenithSparkCommandSender(session.getName(), session.getUUID(), session);
                    }
                };
                var args = getString(c, "args").split(" ");
                ZenithSparkPlugin.SPARK_PLATFORM.executeCommand(sender, args);
                c.getSource().setNoOutput(true); // handoff output to spark
            }));
    }
}
