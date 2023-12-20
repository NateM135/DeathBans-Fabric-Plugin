package com.natem135.deathbans;

import com.natem135.deathbans.events.PlayerDeathCallback;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.minecraft.server.BannedPlayerEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.Objects;

import net.minecraft.server.BannedPlayerList;

public class DeathBansServer implements DedicatedServerModInitializer {
    public static final String MOD_ID = "deathbans";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    @Override
    public void onInitializeServer() {
        LOGGER.info("[+] Initializing Plugin");

        PlayerDeathCallback.EVENT.register((player, source) -> {
            LOGGER.info(String.format("[+] %s has Died.", player.getName()));

            // Ten Minutes
            int banDurationMillis = 60*1000*10;

            Date banStartTime = new Date();
            Date banExpirationTime = new Date(banStartTime.getTime() + banDurationMillis);

            BannedPlayerList bannedPlayerList = Objects.requireNonNull(player.getServer()).getPlayerManager().getUserBanList();

            // Ensure player is not already banned before banning.
            if (!bannedPlayerList.contains(player.getGameProfile())) {
                BannedPlayerEntry bannedPlayerEntry = new BannedPlayerEntry(
                        player.getGameProfile(),
                        banStartTime,
                        null,
                        banExpirationTime,
                        "You are currently death-banned."
                );
                System.out.println("===DEBUG===");
                System.out.println(player.getGameProfile().toString());
                System.out.println(bannedPlayerEntry == null);
                bannedPlayerList.add(bannedPlayerEntry);
                ServerPlayerEntity serverPlayerEntity = player.getServer().getPlayerManager().getPlayer(player.getGameProfile().getId());
            }

            player.networkHandler.disconnect(Text.of("Thou hast fallen and lost thy noble life. The hero hath crumbled over the weight of his own justice. His story of revolution reacheth not the ears of the people. The torch of courage that hath begun to kindle hath now been extinguished by tainted winds."));
        });
    }
}

