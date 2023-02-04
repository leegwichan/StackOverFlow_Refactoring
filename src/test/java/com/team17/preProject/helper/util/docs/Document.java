package com.team17.preProject.helper.util.docs;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;

import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.snippet.Attributes.key;

public interface Document {
    static RestDocumentationResultHandler getMethodDocument(String identifier,
                                                            List<ParameterDescriptor> queryParameters,
                                                            List<FieldDescriptor> responseFields){
        return document(identifier,
                ApiDocumentUtils.getRequestPreProcessor(),
                ApiDocumentUtils.getResponsePreProcessor(),
                requestParameters(queryParameters),
                responseFields(responseFields));
    }

    static RestDocumentationResultHandler getMethodDocument(String identifier,
                                                            List<FieldDescriptor> responseFields){
        return document(identifier,
                ApiDocumentUtils.getRequestPreProcessor(),
                ApiDocumentUtils.getResponsePreProcessor(),
                responseFields(responseFields));
    }

    static RestDocumentationResultHandler postMethodDocument(String identifier,
                                                             List<FieldDescriptor> requestFields,
                                                             List<FieldDescriptor> responseFields){
        return document(identifier,
                ApiDocumentUtils.getRequestPreProcessor(),
                ApiDocumentUtils.getResponsePreProcessor(),
                requestFields(requestFields),
                responseFields(responseFields));

    }

    static RestDocumentationResultHandler patchMethodDocument(String identifier,
                                                             List<FieldDescriptor> requestFields,
                                                             List<FieldDescriptor> responseFields){
        return document(identifier,
                ApiDocumentUtils.getRequestPreProcessor(),
                ApiDocumentUtils.getResponsePreProcessor(),
                requestFields(requestFields),
                responseFields(responseFields));

    }

    static RestDocumentationResultHandler deleteMethodDocument(String identifier){
        return document(identifier,
                ApiDocumentUtils.getRequestPreProcessor(),
                ApiDocumentUtils.getResponsePreProcessor());
    }

    static ParameterDescriptor queryParameterDescriptor(String name, String description){
        return parameterWithName(name).description(description);
    }

    private static FieldDescriptor field(String jsonPath, JsonFieldType type, String description){
        return fieldWithPath(jsonPath).type(type).description(description);
    }

    static ParameterDescriptor pathParameter(String name, String description){
        return parameterWithName(name).description(description);
    }


}

