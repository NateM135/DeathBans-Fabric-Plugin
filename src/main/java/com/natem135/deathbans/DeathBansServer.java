package com.natem135.deathbans;

import com.natem135.deathbans.events.PlayerDeathCallback;
import com.natem135.deathbans.utils.DeathMessageGenerator;
import com.natem135.deathbans.config.DeathBanConfigManager;
import com.natem135.deathbans.config.DeathBanConfig;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.minecraft.server.BannedPlayerEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.Objects;
import net.minecraft.server.BannedPlayerList;
import com.natem135.deathbans.utils.DeathBanLengthCalculator;

public class DeathBansServer implements DedicatedServerModInitializer {
    public static final String MOD_ID = "deathbans";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    // private static final Gson deathBanConfig = new Gson();


    @Override
    public void onInitializeServer() {

        PlayerDeathCallback.EVENT.register((player, source) -> {
            DeathBanConfig pluginConfig = DeathBanConfigManager.getConfig();

            if(!pluginConfig.plugin_enabled) {
                return;
            }

            LOGGER.info(String.format("[+] %s has Died.", player.getName()));

            // Create appropriate time objects
            int banDurationMillis = DeathBanLengthCalculator.calculateBanMS(player);
            Date banStartTime = new Date();
            Date banExpirationTime = new Date(banStartTime.getTime() + banDurationMillis);

            // Ban the user
            BannedPlayerList bannedPlayerList = Objects.requireNonNull(player.getServer()).getPlayerManager().getUserBanList();
            BannedPlayerEntry bannedPlayerEntry =   new BannedPlayerEntry(
                    player.getGameProfile(),
                    banStartTime,
                    null,
                    banExpirationTime,
                    pluginConfig.ban_message
            );
            bannedPlayerList.add(bannedPlayerEntry);

            // Send announcement of ban in server chat.
            Text announceMessage = Text.translatable("chat.type.announcement",  "DeathBans", String.format("%s has been death-banned for %.1f minutes!", player.getGameProfile().getName(), (double)banDurationMillis/(double)(1000*60))).formatted(Formatting.DARK_GREEN).formatted(Formatting.BOLD);
            player.getServer().getPlayerManager().broadcast(announceMessage, false);

            // Adding a ban entry does not remove the user, so remove the user.
            player.networkHandler.disconnect(Text.of(DeathMessageGenerator.getDeathMessage()));
        });
    }
}

