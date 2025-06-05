package com.logger.enhancer.LoggerEnhancer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logger.enhancer.LoggerEnhancer.dto.LogEntry;
import com.logger.enhancer.LoggerEnhancer.dto.LoggerResult;
import com.logger.enhancer.LoggerEnhancer.util.JavaParserUtil;
import com.logger.enhancer.LoggerEnhancer.util.ZipUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LoggerEnhancerService {

    private static final Logger logger = LoggerFactory.getLogger(LoggerEnhancerService.class);

    public List<LoggerResult> processZip(MultipartFile file) throws Exception {
        logger.info("Logger enhancement started");
        File tempDir = ZipUtil.unzipToTemp(file);
        List<Path> javaFiles = JavaParserUtil.findJavaFiles(tempDir.toPath());
        List<LoggerResult> loggerResultList = new ArrayList<>();
        for (Path path : javaFiles) {
            String content = Files.readString(path);
            String analysis = callChatGPT(PromptConstants.PROMPT_CONSTANT1+content);
            List<LoggerResult> loggerResult1 = parseResponse(analysis);
            loggerResultList.addAll(loggerResult1);
            String analysis2 = callChatGPT(PromptConstants.PROMPT_CONSTANT2+content);
            List<LoggerResult> loggerResult2 = parseResponse(analysis2);
            loggerResultList.addAll(loggerResult2);
            String analysis3 = callChatGPT(PromptConstants.PROMPT_CONSTANT3+content);
            List<LoggerResult> loggerResult3 = parseResponse(analysis3);
            loggerResultList.addAll(loggerResult3);
            //Files.writeString(path, analysis); // Overwrite or save backup as needed
        }
        logger.info("Logger enhancement completed for {} files.", javaFiles.size());
        return loggerResultList;
    }

    public List<LoggerResult> parseResponse(String fullResponse) {
        try {
            // Regex to extract JSON array from the response
            Pattern jsonArrayPattern = Pattern.compile("\\[\\s*\\{.*?\\}\\s*\\]", Pattern.DOTALL);
            Matcher matcher = jsonArrayPattern.matcher(fullResponse);

            if (matcher.find()) {
                String jsonArray = matcher.group();
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(jsonArray, new TypeReference<List<LoggerResult>>() {});
            } else {
                System.err.println("No valid JSON array found in response.");
                return List.of();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }


    private String callChatGPT(String prompt) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> request = new HashMap<>();
        request.put("model", "gpt-40");
        request.put("messages", List.of(Map.of("role","user", "content", prompt)));
        request.put("temperature",0.3);
        HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setBearerAuth("c9bdfb8a81744a85b613dd6397108490");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity<>(request, httpHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity("https://bh-in-openai-tracetroopers.openai.azure.com/openai/deployments/gpt-4o/chat/completions?api-version=2025-01-01-preview", httpEntity, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response.getBody());
        return root.get("choices").get(0).get("message").get("content").asText();
    }



    private String callOpenAI(String javaCode) throws IOException {
        String prompt = "Hello";
        //"Analyze and enhance logger usage for each Java class and method..."; // Use full prompt here

        // HttpClient code to call OpenAI API
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://bh-in-openai-tracetroopers.openai.azure.com/openai/deployments/gpt-4o/chat/completions?api-version=2025-01-01-preview"))
                .header("Authorization", "Bearer c9bdfb8a81744a85b613dd6397108490")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(buildRequestBody(javaCode, prompt)))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Extract modified code or JSON suggestion
        return parseOpenAIResponse(response.body());
    }

    private String buildRequestBody(String code, String prompt) {
        return """
            {
              "model": "gpt-4",
              "messages": [
                {"role": "system", "content": "You are a Java code analysis assistant."},
                {"role": "user", "content": "%s\\n%s"}
              ]
            }
            """.formatted(prompt, code);
    }

    private String parseOpenAIResponse(String body) {
        // Parse response JSON and extract the updated method/class text
        return body; // Replace with actual parsing logic
    }
}
