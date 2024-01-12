package com.natem135.deathbans.commands;

import com.natem135.deathbans.config.DeathBanConfig;
import com.natem135.deathbans.config.DeathBanConfigManager;
import net.minecraft.server.command.CommandManager;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import com.natem135.deathbans.DeathBans;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;

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
                .then(CommandManager.literal("set_ban_time_mins")
                        .then(CommandManager.argument("ban_time_minutes", DoubleArgumentType.doubleArg())
                                .requires(source -> source.hasPermissionLevel(4))
                                .executes(DeathBanCommands::setDeathBanTime)
                        )
                )
                .then(CommandManager.literal("set_webhook_url")
                        .then(CommandManager.argument("webhook_url", StringArgumentType.greedyString())
                                .requires(source -> source.hasPermissionLevel(4))
                                .executes(DeathBanCommands::setWebhookURL)
                        )
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
            ctx.getSource().sendFeedback(() -> Text.literal("ERROR: The configuration failed to update."), false);
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

    private static int setDeathBanTime(CommandContext<ServerCommandSource> ctx) {
        final double ban_time_minutes = DoubleArgumentType.getDouble(ctx, "ban_time_minutes");
        DeathBanConfig config = DeathBanConfigManager.getConfig();
        config.base_ban_length_ms = (int)(ban_time_minutes * 1000 * 60);
        boolean updated_correctly = DeathBanConfigManager.saveConfig();
        if(!updated_correctly) {
            ctx.getSource().sendFeedback(() -> Text.literal("ERROR: The configuration failed to update."), false);
            return 0;
        }
        ctx.getSource().sendFeedback(() -> Text.literal(String.format("DeathBan length updated to %.2f minutes! (%dms)!", ban_time_minutes, config.base_ban_length_ms)), false);
        return 1;
    }

    private static int setWebhookURL(CommandContext<ServerCommandSource> ctx) {
        final String _webhook_url = StringArgumentType.getString(ctx, "webhook_url");
        // TODO (natem135): Verify the webhook URL is a valid URL/valid webhook URL
        DeathBanConfig config = DeathBanConfigManager.getConfig();
        config.discord_notify_webhook_url = _webhook_url;
        boolean updated_correctly = DeathBanConfigManager.saveConfig();
        if(!updated_correctly) {
            ctx.getSource().sendFeedback(() -> Text.literal("ERROR: The configuration failed to update."), false);
            return 0;
        }
        ctx.getSource().sendFeedback(() -> Text.literal("Webhook URL has been updated!"), false);
        return 1;
    }
}
