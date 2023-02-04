package com.team17.preProject.question.controller;

import com.team17.preProject.helper.util.test.ControllerTestHelper;

import java.net.URI;

public interface QuestionControllerTestHelper extends ControllerTestHelper {
    String QUESTION_URL = "/questions";
    default URI getURI() {
        return createURI(QUESTION_URL);
    }

    default URI getURI(long questionId) {
        return createURI(QUESTION_URL + "/{questionId}", questionId);
    }
}
