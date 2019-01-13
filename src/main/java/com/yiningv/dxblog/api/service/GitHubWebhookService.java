package com.yiningv.dxblog.api.service;

public interface GitHubWebhookService {


    void handlePushPayload(String payload);
}
