package com.yiningv.dxblog.api.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yiningv.dxblog.api.service.GitHubWebhookService;
import com.yiningv.dxblog.model.ReposConfig;
import com.yiningv.dxblog.repository.ReposConfigRepository;
import com.yiningv.dxblog.support.HttpFetch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class GitHubWebhookServiceImpl implements GitHubWebhookService {

    @Autowired
    private HttpFetch httpFetch;

    @Autowired
    private ReposConfigRepository reposConfigRepository;

    @Override
    public void handlePingPayload(JsonNode payloadJson) throws Exception {
        JsonNode repository = payloadJson.get("repository");
        String reposId = repository.get("id").asText();
        String reposUrl = repository.get("url").asText();

        // 读取配置文件
        String s = httpFetch.get(reposUrl);

        ReposConfig reposConfig = null;

        reposConfigRepository.save(reposConfig);
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
