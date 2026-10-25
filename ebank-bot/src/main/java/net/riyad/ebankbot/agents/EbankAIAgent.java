package net.riyad.ebankbot.agents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class EbankAIAgent {
    private final ChatClient chatClient;

    public EbankAIAgent(ChatClient.Builder chatClientBuilder,
                        ToolCallbackProvider tools) {
        this.chatClient = chatClientBuilder
                .defaultSystem("""
                    Tu es un assistant bancaire. 
                    Utilise les outils pour répondre de manière concise.
                    Quand tu génères des tableaux, utilise la syntaxe Markdown standard.
                    """)
                .defaultTools(tools)
                .build();
    }

    public String chat(String query) {
        return chatClient.prompt()
                .user(query)
                .call()
                .content();
    }

    public Flux<String> chatStream(String query) {
        return chatClient.prompt()
                .user(query)
                .stream()
                .content();
    }
}