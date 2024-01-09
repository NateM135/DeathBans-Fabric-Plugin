package com.natem135.deathbans.commands;

import net.minecraft.server.command.CommandManager;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import com.natem135.deathbans.DeathBans;
import com.mojang.brigadier.context.CommandContext;

public class DeathBanCommands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal(DeathBans.MOD_ID)
                .then(CommandManager.literal("status")
                    .executes(DeathBanCommands::getPluginStatus)
                )
                .then(CommandManager.literal("author")
                    .executes(DeathBanCommands::getPluginInfo)
                )
        ));
    }

    private static int getPluginStatus(CommandContext<ServerCommandSource> ctx) {
        ctx.getSource().sendFeedback(() -> Text.literal("The plugin is currently enabled!"), false);
        return 1;
    }

    private static int getPluginInfo(CommandContext<ServerCommandSource> ctx) {
        ctx.getSource().sendFeedback(() -> Text.literal("The plugin was developed by NateM135."), false);
        return 1;
    }
}
