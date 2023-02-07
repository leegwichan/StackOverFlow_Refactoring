package com.team17.preProject.helper.stub;

import com.team17.preProject.domain.answer.dto.AnswerDto;
import com.team17.preProject.dto.MultiResponseDto;
import com.team17.preProject.domain.question.dto.QuestionDto;
import com.team17.preProject.domain.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionStub {
    private static Map<HttpMethod, Object> requestBody;
    private static QuestionDto.Response singleResponseBody;
    private static QuestionDto.SubResponse singleSubResponseBody;
    private static Page<QuestionDto.SubResponse> multiSubResponseBody;
    private static Page<Question> multiEntity;

    static {
        requestBody = new HashMap<>();
        requestBody.put(HttpMethod.POST, new QuestionDto.Post("Question title입니다.1","Question content 입니다.1",1));
        requestBody.put(HttpMethod.PATCH, new QuestionDto.Patch("Question title입니다.1변경","Question content 입니다.1변경"));

        Page<AnswerDto.Response> pageAnswerDto = AnswerStub.getMultiResponseBody();
        MultiResponseDto multiResponseDto = new MultiResponseDto(pageAnswerDto.getContent(), pageAnswerDto);

        singleResponseBody = new QuestionDto.Response(1,"Question title입니다.1","Question content 입니다.1",1,1,
                Stub.getLocalDateTime(),Stub.getLocalDateTime(),MemberStub.getSubResponse(),pageAnswerDto.getContent(),4,null);
        singleSubResponseBody = new QuestionDto.SubResponse(1,"Question title입니다.1","Question content 입니다.1",1,1,
                Stub.getLocalDateTime(),Stub.getLocalDateTime(),MemberStub.getSubResponse(),4,null);

        List<QuestionDto.SubResponse> multiSubResponseBodyContent = new ArrayList<>();
        multiSubResponseBodyContent.add(new QuestionDto.SubResponse(1,"Question title입니다.1","Question content 입니다.1",1,1,
                Stub.getLocalDateTime(),Stub.getLocalDateTime(),MemberStub.getSubResponse(),4,null));
        multiSubResponseBodyContent.add(new QuestionDto.SubResponse(2,"Question title입니다.2","Question content 입니다.2",4,-4,
                Stub.getLocalDateTime(),Stub.getLocalDateTime(),MemberStub.getSubResponse(),4,1L));
        multiSubResponseBodyContent.add(new QuestionDto.SubResponse(3,"Question title입니다.3","Question content 입니다.3",1,1,
                Stub.getLocalDateTime(),Stub.getLocalDateTime(),MemberStub.getSubResponse(),4,null));
        multiSubResponseBodyContent.add(new QuestionDto.SubResponse(4,"Question title입니다.4","Question content 입니다.4",2,2,
                Stub.getLocalDateTime(),Stub.getLocalDateTime(),MemberStub.getSubResponse(),4,1L));
        multiSubResponseBody = new PageImpl<>(multiSubResponseBodyContent,
                PageRequest.of(0,10, Sort.by("questionId").descending()),
                multiSubResponseBodyContent.size());

        Question question = new Question();
        multiEntity = new PageImpl<>(List.of(question, question, question, question), PageRequest.of(0,10, Sort.by("questionId").descending()),1);
    }

    public static Map<HttpMethod, Object> getRequestBody(){
        return requestBody;
    }

    public static QuestionDto.Response getSingleResponseBody(){
        return singleResponseBody;
    }

    public static QuestionDto.SubResponse getSingleSubResponseBody() {
        return singleSubResponseBody;
    }

    public static Page<QuestionDto.SubResponse> getMultiSubResponseBody(){
        return multiSubResponseBody;
    }

    public static Page<Question> getMultiEntity(){
        return multiEntity;
    }

    public static final Question ENTITY;
    public static final QuestionDto.SubResponse SUB_RESPONSE;
    static {
        ENTITY = new Question();
        ENTITY.setQuestionId(3L);
        ENTITY.setTitle("question title");
        ENTITY.setContent("question content");
        ENTITY.setView(100L);
        ENTITY.setVote(5L);
        ENTITY.setMember(MemberStub.ENTITY);

        SUB_RESPONSE = new QuestionDto.SubResponse();
        SUB_RESPONSE.setQuestionId(ENTITY.getQuestionId());
        SUB_RESPONSE.setTitle(ENTITY.getTitle());
        SUB_RESPONSE.setContent(ENTITY.getContent());
        SUB_RESPONSE.setView(ENTITY.getView());
        SUB_RESPONSE.setVote(ENTITY.getVote());
        SUB_RESPONSE.setCreatedAt(ENTITY.getCreatedAt());
        SUB_RESPONSE.setModifiedAt(ENTITY.getModifiedAt());
        SUB_RESPONSE.setMember(MemberStub.SUB_RESPONSE);
        SUB_RESPONSE.setAnswerCount(ENTITY.getAnswers().size());
    }
}
