package com.natem135.deathbans.utils;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
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
        if(!config.discord_notify_send_msg_on_death) {
            DeathBans.LOGGER.error("Attempted to send discord notification when feature is not enabled.");
            return;
        }
        if(config.discord_notify_webhook_url.isEmpty()) {
            DeathBans.LOGGER.error("DeathBan Discord notification is enabled but no webhook link is provided.");
            return;
        }
        try (WebhookClient client = WebhookClient.withUrl(config.discord_notify_webhook_url)){
            WebhookEmbed embed = new WebhookEmbedBuilder()
                    .setColor(0x00FF00)
                    .setTitle(new WebhookEmbed.EmbedTitle("DeathBan Notification", null))
                    .addField(new WebhookEmbed.EmbedField(
                            true,
                            death_message,
                            ""
                    ))
                    .setFooter(new WebhookEmbed.EmbedFooter("Made with <3", null))
                    .setTimestamp(ZonedDateTime.now())
                    .build();

            WebhookMessageBuilder builder = new WebhookMessageBuilder();
            builder.setUsername(config.discord_notify_webhook_username);
            builder.setAvatarUrl(config.discord_notify_pfp_url);
            builder.addEmbeds(embed);

            client.send(builder.build());
        }
        catch (Exception e) {
            DeathBans.LOGGER.error("Discord notify feature enabled but webhook failed to initialize. Reason: " + e);
        }


    }

}
