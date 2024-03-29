package com.natem135.deathbans.commands;

import com.natem135.deathbans.config.DeathBanConfig;
import com.natem135.deathbans.config.DeathBanConfigManager;
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
                .then(CommandManager.literal("on")
                        .requires(source -> source.hasPermissionLevel(4))
                        .executes(DeathBanCommands::enablePlugin)
                )
                .then(CommandManager.literal("off")
                        .requires(source -> source.hasPermissionLevel(4))
                        .executes(DeathBanCommands::disablePlugin)
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

    private static int enablePlugin(CommandContext<ServerCommandSource> ctx) {
        DeathBanConfig config = DeathBanConfigManager.getConfig();
        boolean updated_correctly = true;
        if(!config.plugin_enabled) {
            config.plugin_enabled = true;
            updated_correctly = DeathBanConfigManager.saveConfig();
        }
        if(!updated_correctly) {
            ctx.getSource().sendFeedback(() -> Text.literal("The configuration failed to update."), false);
            return 0;
        }
        ctx.getSource().sendFeedback(() -> Text.literal("The plugin has been enabled!"), false);
        return 1;
    }

    private static int disablePlugin(CommandContext<ServerCommandSource> ctx) {
        DeathBanConfig config = DeathBanConfigManager.getConfig();
        boolean updated_correctly = true;
        if(config.plugin_enabled) {
            config.plugin_enabled = false;
            updated_correctly = DeathBanConfigManager.saveConfig();
        }
        if(!updated_correctly) {
            ctx.getSource().sendFeedback(() -> Text.literal("ERROR: The configuration failed to update."), false);
            return 0;
        }
        ctx.getSource().sendFeedback(() -> Text.literal("The plugin has been successfully disabled."), false);
        return 1;
    }
}
