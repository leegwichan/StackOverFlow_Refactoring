package com.team17.preProject.exception.businessLogic;

import lombok.Getter;

public enum ExceptionCode {

    MEMBER_NOT_FOUND(404, "Member not found"),
    QUESTION_NOT_FOUND(404, "Question not found"),
    ANSWER_NOT_FOUND(404, "Answer not found"),
    ALREADY_FOLLOW_POST(400, "Already follow post"),
    NOT_FOLLOW_POST(400, "Not follow post"),
    ALREADY_EXIST_EMAIL(400, "Already exist email"),
    ALREADY_VOTE_GOOD(400, "Already vote good"),
    ALREADY_VOTE_BAD(400, "Already vote bad"),
    FAIL_SEND_EMAIL(300, "이메일 주소가 잘못되었거나 서버에서 메일을 보내지 못했습니다."),
    FAIL_SEND_EMAIL_BY_SERVER(500, "서버에서 이메일을 전송하지 못했습니다."),
    NOT_VERIFIED_ANSWER_OF_QUESTION(400, "해당 질문의 답변이 아닙니다."),
    ALREADY_EXIST_BEST_ANSWER(400, "Already exist best answer"),
    MEMBER_NOT_MATCHED(400, "요청한 회원 식별자와 로그인 유저의 정보가 일치하지 않습니다."),
    TRY_LOGIN_AGAIN(500, "로그인을 다시 시도해주세요."),
    SAME_QUESTION_ANSWER_MEMBER(400, "자신의 질문에 답변을 달 수 없습니다."),
    SAME_WRITER_VOTER(400, "자신의 글에는 투표할 수 없습니다."),
    NOT_IMAGE_EXTENSION(400, "이미지 파일이 아닙니다."),
    STRANGE_DATA(500, Constants.SERVER_ERROR_MESSAGE),

    VOTE_VALUE_INCORRECT(500, Constants.SERVER_ERROR_MESSAGE),
    ;

    private static class Constants {
        private static final String SERVER_ERROR_MESSAGE = "서버 오류입니다 관리자에게 문의하세요.";
    }

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message){
        this.status = code;
        this.message = message;
    }
}
