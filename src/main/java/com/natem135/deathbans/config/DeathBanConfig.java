package com.natem135.deathbans.config;

public class DeathBanConfig {
    // Ban length specified by min(ban length * (scaling_factor * num_deaths), max_ban_length_ms)
    // Death Ban Length
    public int initial_ban_length_ms = 60*1000*10;
    // Ban message, showed to users when they attempt to rejoin with active death-ban
    public String ban_message = "You are currently death-banned.";
}
