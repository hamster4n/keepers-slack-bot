package ua.com.juja.microservices.keepers.slackbot.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import ua.com.juja.microservices.keepers.slackbot.exception.ApiError;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Nikolay Horushko
 */
public class AbstractRestRepository {
    protected HttpHeaders setupBaseHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return headers;
    }

    protected ApiError convertToApiError(HttpClientErrorException httpClientErrorException) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(httpClientErrorException.getResponseBodyAsString(), ApiError.class);
        } catch (IOException e) {
            return new ApiError(
                    500, "BotInternalError",
                    "I'm, sorry. I cannot parse api error message from remote service :(",
                    "Cannot parse api error message from remote service",
                    e.getMessage(),
                    Collections.singletonList(httpClientErrorException.getMessage())
            );
        }
    }
}
