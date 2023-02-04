package com.team17.preProject.helper.util.docs;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

public interface ApiDocumentUtils {

    static OperationRequestPreprocessor getRequestPreProcessor() {
        return preprocessRequest(
                modifyUris() // (1)
                        .scheme("https")
                        .host("docs.api.com")
                        .removePort(),
                prettyPrint());
    }

    static OperationResponsePreprocessor getResponsePreProcessor() {
            return preprocessResponse(prettyPrint());
    }
}
