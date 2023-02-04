package com.team17.preProject.member.controller;

import com.team17.preProject.helper.util.test.ControllerTestHelper;

import java.net.URI;

public interface MemberControllerTestHelper extends ControllerTestHelper {
    String MEMBER_URL = "/members";
    default URI getURI() {
        return createURI(MEMBER_URL);
    }

    default URI getURI(long memberId) {
        return createURI(MEMBER_URL + "/{memberId}", memberId);
    }
}
