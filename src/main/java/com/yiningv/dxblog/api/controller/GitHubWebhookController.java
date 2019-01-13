package com.yiningv.dxblog.api.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yiningv.dxblog.VERSION;
import com.yiningv.dxblog.api.service.GitHubWebhookService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.MessageDigest;

@Controller
@RequestMapping("/api")
@Slf4j
public class GitHubWebhookController {

    @Autowired
    private GitHubWebhookService webhookService;

    @PostMapping("/hook_push")
    public ResponseEntity<String> hookPushApi(@RequestHeader("X-GitHub-Event") String event,
                                  @RequestHeader("X-Hub-Signature") String signature,
                                  @RequestBody String payload) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Webhook-Version", VERSION.getVersionNumber());

        if (event == null || !"push".equals(event)) {
            return new ResponseEntity<>("Invalid event.", headers, HttpStatus.BAD_REQUEST);
        }

        if (signature == null) {
            return new ResponseEntity<>("No signature given.", headers, HttpStatus.BAD_REQUEST);
        }
        String secretKey = System.getenv("GH_SECRET_KEY");
        String computed = String.format("sha1=%s", new HmacUtils(HmacAlgorithms.HMAC_SHA_1, secretKey).hmacHex(payload));
        if (!MessageDigest.isEqual(signature.getBytes(), computed.getBytes())) {
            return new ResponseEntity<>("Invalid signature.", headers, HttpStatus.UNAUTHORIZED);
        }

        try {
            log.info(payload);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode payloadJson = objectMapper.readTree(payload);
            JsonNode headCommitJson = payloadJson.get("head_commit");
            // 处理commit中的added,modified,removed
            webhookService.handlePushPayload(payload);
        } catch (Exception e) {
            log.error("Unable to parse payload.", e);
            return new ResponseEntity<>("Unable to parse payload.", headers, HttpStatus.BAD_REQUEST);
        }

        int bytes = payload.getBytes().length;
        return new ResponseEntity<>(String.format("Received %d bytes.", bytes), headers, HttpStatus.OK);
    }
}
