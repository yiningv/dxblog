package com.yiningv.dxblog.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class TestController {

    @Autowired
    private Environment env;

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        String buildVersion = env.getProperty("application.version");
        String str = "{\"name\":\"inbox\",\"scripts\":{\"test\":\"mocha\"},\"license\":\"ISC\",\"head_commit\":[{\"ganache-cli\":\"^6.2.3\",\"mocha\":\"^5.2.0\"}]}";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(str);
            JsonNode head_commit = jsonNode.get("head_commit");
            for (int i = 0; i < head_commit.size(); i++) {
                System.out.println(head_commit.get(i).get("ganache-cli"));
            }

            Map<?, ?> map = objectMapper.readValue(str, Map.class);
            Object head_commit1 = map.get("head_commit");
            System.out.println(head_commit1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return buildVersion;
    }
}
