package net.riyad.ebankbot.agents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;

@Service
public class EbankAIAgent {
    private final ChatClient chatClient;

    public EbankAIAgent(ChatClient.Builder chatClientBuilder,
                        ChatMemory chatMemory,
                        ToolCallbackProvider tools) {
        this.chatClient = chatClientBuilder
                .defaultSystem("""
                        Vous un assistant qui se charge de répondre aux question de l'utilisateur à propos des clients et des comptes bancaires, en fonction du contexte fourni.
                        Si aucun contexte n'est fourni, répond avec JE NE SAIS PAS
                        """)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultTools(tools)
                .build();
    }

    public String chat(String query, String conversationId) {
        return chatClient.prompt()
                .user(query)
                .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();
    }

}
