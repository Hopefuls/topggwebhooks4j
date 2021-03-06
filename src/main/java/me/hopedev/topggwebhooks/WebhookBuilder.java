package me.hopedev.topggwebhooks;


import me.hopedev.topggwebhooks.bots.BotWebhookListener;
import me.hopedev.topggwebhooks.enums.Options;
import me.hopedev.topggwebhooks.servers.GuildWebhookListener;

import java.util.ArrayList;
import java.util.HashMap;

public class WebhookBuilder {

    private final HashMap<String /*url context*/, ListenerCollection /*ListenerPack*/> listenerStorage = new HashMap<>();
    private int port = 6969;

    /**
     * Constructor
     */

    public WebhookBuilder() {
    }


    /**
     * @param port sets the Port that is used for the Webhook
     *             Example: http://example.com:<b>6969</b>/dblwebhooks
     *             Default: <b>6969</b>
     * @return the current WebhookBuilder instance
     */
    public final WebhookBuilder setPort(int port) {
        this.port = port;
        return this;
    }

    public final WebhookBuilder addGuildListener(String webhookPath, GuildWebhookListener listener, String webhookAuth, Options... options) {
        listenerStorage.put(webhookPath, new ListenerCollection(listener, webhookAuth, options));
        return this;
    }

    public final WebhookBuilder addBotListener(String webhookPath, BotWebhookListener listener, String webhookAuth, Options... options) {
        listenerStorage.put(webhookPath, new ListenerCollection(listener, webhookAuth, options));
        return this;
    }

    /**
     * Method to build the finished Webhook
     *
     * @return the built Webhook
     */
    public final Webhook build() {
        ArrayList<PathCollection> packs = new ArrayList<>();
        listenerStorage.forEach((s, o) -> packs.add(new PathCollection(s, o)));
        return new Webhook(this.port, packs);
    }

}
