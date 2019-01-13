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

    @Async
    @Override
    public void handlePushPayload(String payload) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode payloadJson = objectMapper.readTree(payload);
            JsonNode headCommitJson = payloadJson.get("head_commit");
        } catch (Exception e) {
            log.error("handlePushPayload error.", e);
        }
    }
}
