package com.natem135.deathbans.utils;

import com.natem135.deathbans.config.DeathBanConfig;
import com.natem135.deathbans.config.DeathBanConfigManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;

public class DeathBanLengthCalculator {
    private static final DeathBanConfig pluginConfig = DeathBanConfigManager.getConfig();

    public static int calculateBanMS(ServerPlayerEntity player) {
        // min(Base Ban length * (scaling_factor * 1000 * 60 * num_deaths), max_ban_length_ms)
        if(!pluginConfig.scale_ban_times) {
            return pluginConfig.base_ban_length_ms;
        }
        int player_deaths = player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.DEATHS));
        return (int) Math.min(pluginConfig.max_ban_length_ms, pluginConfig.base_ban_length_ms + (player_deaths * 1000 * 60 * pluginConfig.scaling_factor));
    }
}
