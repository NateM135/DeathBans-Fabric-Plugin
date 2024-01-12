package com.natem135.deathbans.utils;

import club.minnced.discord.webhook.WebhookClient;
import com.natem135.deathbans.DeathBans;
import com.natem135.deathbans.config.DeathBanConfigManager;
import com.natem135.deathbans.config.DeathBanConfig;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;

import java.time.ZonedDateTime;

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
            // WebhookMessageBuilder builder = new WebhookMessageBuilder();

            WebhookEmbed embed = new WebhookEmbedBuilder()
                    .setColor(0x00FF00)
                    .setThumbnailUrl("https://static1.cbrimages.com/wordpress/wp-content/uploads/2023/02/youre-under-arrest-1.jpeg")
                    .setTitle(new WebhookEmbed.EmbedTitle("DeathBan Notification", null))
                    .addField(new WebhookEmbed.EmbedField(
                            true,
                            death_message,
                            ""
                    ))
                    .setFooter(new WebhookEmbed.EmbedFooter("Made with <3", null))
                    .setTimestamp(ZonedDateTime.now())
                    .build();

            client.send(embed);
        }
        catch (Exception e) {
            DeathBans.LOGGER.error("Discord notify feature enabled but webhook failed to initialize. Reason: " + e);
        }


    }

}
