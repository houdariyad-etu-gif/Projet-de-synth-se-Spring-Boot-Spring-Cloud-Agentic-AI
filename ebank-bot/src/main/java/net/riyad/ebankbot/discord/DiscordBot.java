package net.riyad.ebankbot.discord;

import com.zgamelogic.discord.annotations.DiscordController;
import com.zgamelogic.discord.annotations.DiscordMapping;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.riyad.ebankbot.agents.EbankAIAgent;

@DiscordController
public class DiscordBot {
    private EbankAIAgent ebankAIAgent;

    public DiscordBot (EbankAIAgent ebankAIAgent){
        this.ebankAIAgent = ebankAIAgent;
    }

    @DiscordMapping
    private void perform (MessageReceivedEvent event){
        if (event.getAuthor().isBot()) return;
        String query = event.getMessage().getContentRaw();
        String response = ebankAIAgent.chat(query, "discord-" + event.getChannel().getId());
        event.getChannel().sendMessage(response).queue();
    }
}
