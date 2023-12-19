package com.natem135.deathbans;

import com.natem135.deathbans.events.PlayerDeathCallback;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeathBansServer implements DedicatedServerModInitializer {
    public static final String MOD_ID = "deathbans";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    @Override
    public void onInitializeServer() {
        LOGGER.info("[+] Initializing Plugin");

        PlayerDeathCallback.EVENT.register((player, source) -> {
            LOGGER.info(source.getName());
            System.out.println("[SERVER CODE] DEATH DETECTED");
            player.networkHandler.disconnect(Text.of("You have died, bastard."));
        });
    }
}

