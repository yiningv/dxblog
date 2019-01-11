package com.yiningv.dxblog.api.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.security.MessageDigest;
import java.util.Map;

@Controller
public class GitHubWebhookController {

    @Autowired
    private Environment env;

    @PostMapping("/")
    public ResponseEntity<String> pushApi(@RequestHeader("X-GitHub-Event") String event,
                                  @RequestHeader("X-Hub-Signature") String signature,
                                  @RequestBody String payload) {
        HttpHeaders headers = new HttpHeaders();
        String appVersion = env.getRequiredProperty("application.version");
        headers.add("X-Webhook-Version", appVersion);

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
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode payloadJson = objectMapper.readTree(payload);
            JsonNode headCommitJson = payloadJson.get("head_commit");
            // 处理commit中的added,modified,removed
        } catch (Exception e) {
            return new ResponseEntity<>("Unable to parse payload.", headers, HttpStatus.BAD_REQUEST);
        }

        int bytes = payload.getBytes().length;
        return new ResponseEntity<>(String.format("Received %d bytes.", bytes), headers, HttpStatus.OK);
    }
}
