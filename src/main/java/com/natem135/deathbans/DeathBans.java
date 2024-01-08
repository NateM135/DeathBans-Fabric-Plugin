package com.natem135.deathbans;

import com.natem135.deathbans.config.DeathBanConfigManager;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeathBans implements ModInitializer {
	public static final String MOD_ID = "deathbans";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("[+] Initializing DeathBans Plugin");
		boolean res =  DeathBanConfigManager.loadConfig();
		if(!res) {
			LOGGER.error("Critical error when loading DeathBans plugin.");
		}
	}
}