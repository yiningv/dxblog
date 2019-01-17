package com.yiningv.dxblog.api.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yiningv.dxblog.api.service.GitHubWebhookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class GitHubWebhookServiceImpl implements GitHubWebhookService {

    @Override
    public void handlePingPayload(JsonNode payloadJson) {
        JsonNode repository = payloadJson.get("repository");
        String repositoryNodeId = repository.get("node_id").asText(null);
        long repositoryId = repository.get("id").asLong(0L);
        JsonNode repositoryUrl = repository.get("contents_url");
        
    }

    @Async
    @Override
    public void handlePushPayload(JsonNode payloadJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode headCommitJson = payloadJson.get("head_commit");
        } catch (Exception e) {
            log.error("handlePushPayload error.", e);
        }
    }

}
