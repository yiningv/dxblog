package com.yiningv.dxblog.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.scheduling.annotation.Async;

public interface GitHubWebhookService {

    @Async
    void syncReposPosts(JsonNode payloadJson);

    void handlePushPayload(JsonNode payloadJson);
}
