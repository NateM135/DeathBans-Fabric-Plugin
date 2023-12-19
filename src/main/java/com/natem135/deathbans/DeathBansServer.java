package com.natem135.deathbans;

import net.fabricmc.api.DedicatedServerModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeathBansServer implements DedicatedServerModInitializer {
    public static final String MOD_ID = "deathbans";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    @Override
    public void onInitializeServer() {
        LOGGER.info("DeathBan Server Initializer !!!!");
    }
}

