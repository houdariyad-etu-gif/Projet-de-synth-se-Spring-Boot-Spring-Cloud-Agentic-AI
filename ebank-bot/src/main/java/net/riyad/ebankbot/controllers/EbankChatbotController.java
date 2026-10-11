package net.riyad.ebankbot.controllers;

import net.riyad.ebankbot.agents.EbankAIAgent;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EbankChatbotController {
    private EbankAIAgent ebankAIAgent;

    public EbankChatbotController(EbankAIAgent ebankAIAgent) {
        this.ebankAIAgent = ebankAIAgent;
    }
    @GetMapping(value= "/chat",produces = MediaType.TEXT_PLAIN_VALUE)
    public String chat(@RequestParam(name = "query", defaultValue = "Bonjour") String query,
                       @RequestParam(name = "conversationId", defaultValue = "http-client") String conversationId) {
        return ebankAIAgent.chat(query, conversationId);
    }
}
