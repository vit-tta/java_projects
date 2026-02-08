package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Конфигурация HTTP клиента.
 * Конфигурирует конвертеры для разных типов данных (JSON, multipart).
 * Это нужно, чтобы API Gateway мог общаться с другими микросервисами.
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        restTemplate.setRequestFactory(requestFactory);

        // Настраиваем конвертеры
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();

        // Конвертер для multipart/form-data
        messageConverters.add(new AllEncompassingFormHttpMessageConverter());

        // Конвертер для обычных форм
        messageConverters.add(new FormHttpMessageConverter());

        // Конвертер для строк
        messageConverters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));

        // Конвертер для JSON
        messageConverters.add(new MappingJackson2HttpMessageConverter());

        // Конвертер для байтовых массивов (файлы)
        messageConverters.add(new ByteArrayHttpMessageConverter());

        restTemplate.setMessageConverters(messageConverters);

        return restTemplate;
    }
}