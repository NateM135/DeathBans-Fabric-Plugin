package com.natem135.deathbans.commands;

import net.minecraft.server.command.CommandManager;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.text.Text;
import com.natem135.deathbans.DeathBans;

public class DeathBanCommands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal(DeathBans.MOD_ID)
                .then(CommandManager.literal("status")
                    .executes(context -> {
                        context.getSource().sendFeedback(() -> Text.literal("The plugin is currently enabled!"), false);
                        return 1;
                }))
                .then(CommandManager.literal("info")
                .executes(context -> {
                    context.getSource().sendFeedback(() -> Text.literal("This plugin was developed by NateM135."), false);
                    return 1;
                }))
        ));
    }
}
