package com.yiningv.dxblog.api.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface GitHubWebhookService {

    void handlePingPayload(JsonNode payloadJson) throws Exception;

    void handlePushPayload(JsonNode payloadJson);

}
