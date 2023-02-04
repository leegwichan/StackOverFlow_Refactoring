package com.team17.preProject.helper.util.test;

import com.google.gson.Gson;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public interface ControllerTestHelper<T> {

    String MEMBER_URL = "/members";

    default MockHttpServletRequestBuilder postRequestBuilder(URI uri,
                                                             String content) {
        return post(uri)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
    }

    default MockHttpServletRequestBuilder patchRequestBuilder(URI uri,
                                               String content) {
        return patch(uri)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

    }

    default MockHttpServletRequestBuilder getRequestBuilder(URI uri) {
        return get(uri)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
    }

    default MockHttpServletRequestBuilder getRequestBuilder(URI uri, MultiValueMap<String, String> queryParams) {
        return get(uri)
                .params(queryParams)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
    }

    default MockHttpServletRequestBuilder deleteRequestBuilder(URI uri) {
        return delete(uri);
    }

    default URI createURI(String url) {
        return UriComponentsBuilder.newInstance().path(url).build().toUri();
    }

    default URI createURI(String url, long resourceId) {
        return UriComponentsBuilder.newInstance().path(url).buildAndExpand(resourceId).toUri();
    }

    default String toJsonContent(T t) {
        Gson gson = new Gson();
        String content = gson.toJson(t);
        return content;
    }

    default ResultActions andExpectJsonPathEqualDto(ResultActions actions, Object object) throws Exception{

        List<Object[]> jsonAndValues = JsonUtils.getJsonPathAndValue(object);

        ResultActions resultActions = actions;
        for (Object[] jsonAndValue : jsonAndValues){
            if (jsonAndValue[1].getClass() == LocalDateTime.class) continue;
            if (jsonAndValue[1] == List.class){
                resultActions = resultActions
                        .andExpect(jsonPath( (String) jsonAndValue[0]).isArray());
            } else {
                resultActions = resultActions
                        .andExpect(jsonPath((String) jsonAndValue[0]).value(jsonAndValue[1]));
            }
        }

        return resultActions;
    }
}
