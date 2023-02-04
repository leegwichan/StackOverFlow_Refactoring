package com.team17.preProject.helper.util.docs;

import java.util.HashMap;
import java.util.Map;

public class DocumentFieldDescription {
    private static Map<String, String> fieldDescriptions = new HashMap<>();
    static {
        fieldDescriptions.put("data", "결과 데이터");
        fieldDescriptions.put("createdAt", "생성 시각");
        fieldDescriptions.put("modifiedAt", "수정 시각");

        fieldDescriptions.put("page", "현재 페이지");
        fieldDescriptions.put("size", "현재 페이지 크기");
        fieldDescriptions.put("totalElements", "총 data 개수");
        fieldDescriptions.put("totalPages", "전체 페이지 수");

        fieldDescriptions.put("memberId", "Member 식별자");
        fieldDescriptions.put("answerId", "Answer 식별자");
        fieldDescriptions.put("questionId", "Question 식별자");
        fieldDescriptions.put("bestAnswerId", "Question에서 채택된 Answer의 식별자");

        fieldDescriptions.put("title", "글 제목");
        fieldDescriptions.put("content", "글 내용");
        fieldDescriptions.put("view", "조회수");
        fieldDescriptions.put("vote", "추천 수");

        fieldDescriptions.put("memberTitle", "글 제목");
        fieldDescriptions.put("email", "이메일");
        fieldDescriptions.put("displayName", "화면에 보이는 Name");
        fieldDescriptions.put("image", "User 이미지 URL");
        fieldDescriptions.put("location", "User의 주요 활동 위치");
        fieldDescriptions.put("aboutMe", "나에 대한 설명");

        fieldDescriptions.put("member", "회원 정보");
        fieldDescriptions.put("answers", "Question의 Answer에 대한 정보");
    }

    public static Map<String, String> getFieldDescriptions() {
        return fieldDescriptions;
    }


}
