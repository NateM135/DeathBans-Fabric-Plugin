package com.natem135.deathbans.config;

public class DeathBanConfig {
    // Ban length specified by min(Base Ban length * (scaling_factor * num_deaths), max_ban_length_ms)
    // Death Ban Length
    public int base_ban_length_ms = 60*1000*10;
    // Toggle Scaling Ban Window
    public boolean scale_ban_times = false;
    // Scaling Factor per Death for Ban
    public double scaling_factor = 1.5;
    // Max Ban Time
    public int max_ban_length_ms = 60*1000*30;
    // Ban message, showed to users when they attempt to rejoin with active death-ban
    public String ban_message = "You are currently death-banned.";
}
