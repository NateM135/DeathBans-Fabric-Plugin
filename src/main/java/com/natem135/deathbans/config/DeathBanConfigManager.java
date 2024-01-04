package com.natem135.deathbans.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.natem135.deathbans.DeathBansServer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class DeathBanConfigManager {
    private static DeathBanConfig CONFIG = new DeathBanConfig();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static DeathBanConfig getConfig() {
        return CONFIG;
    }

    public static boolean loadConfig() {
        try {
            File configDir = Paths.get("", "config", "deathbans").toFile();

            boolean res = configDir.mkdirs();

            if (res) {
                DeathBansServer.LOGGER.info(String.format("Created mod configuration directory at %s", configDir.getAbsolutePath()));
            }

            File configFile = new File(configDir, "config.json");
            if(configFile.exists()) {
                DeathBansServer.LOGGER.info("Config File Found! Reading config...");
                CONFIG = GSON.fromJson(new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8), CONFIG.getClass());
            }
            else {
                DeathBansServer.LOGGER.info("Config File Does Not Exist. Creating default configuration file...");
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(configFile), StandardCharsets.UTF_8));
                writer.write(GSON.toJson(CONFIG));
                writer.close();
            }

            return true;
        }
        catch(Exception e){
            DeathBansServer.LOGGER.error(e.getMessage());
            return false;
        }

    }
}
