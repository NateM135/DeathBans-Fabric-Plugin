package com.natem135.deathbans.utils;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import com.natem135.deathbans.DeathBans;
import com.natem135.deathbans.config.DeathBanConfigManager;
import com.natem135.deathbans.config.DeathBanConfig;

public class DeathBansDiscordNotifier {

    public static void sendDiscordNotification(String death_message) {
        DeathBans.LOGGER.info("Inside Function");
        DeathBanConfig config = DeathBanConfigManager.getConfig();
        if(!config.send_discord_update_on_death) {
            DeathBans.LOGGER.error("Attempted to send discord notification when feature is not enabled.");
            return;
        }
        if(config.webhook_url.isEmpty()) {
            DeathBans.LOGGER.error("DeathBan Discord notification is enabled but no webhook link is provided.");
            return;
        }
        try (WebhookClient client = WebhookClient.withUrl(config.webhook_url)){
            WebhookMessageBuilder builder = new WebhookMessageBuilder();
            builder.setUsername("DeathBans");
            builder.setContent("Hello World");
            client.send(builder.build());
        }
        catch (Exception e) {
            DeathBans.LOGGER.error("Discord notify feature enabled but webhook failed to initialize. Reason: " + e);
        }


    }

}
