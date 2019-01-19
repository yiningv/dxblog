package com.yiningv.dxblog.api.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface GitHubWebhookService {

    void handlePushPayload(JsonNode payloadJson);
}
