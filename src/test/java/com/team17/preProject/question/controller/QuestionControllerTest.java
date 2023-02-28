package com.team17.preProject.question.controller;

import com.team17.preProject.domain.answer.service.AnswerService;
import com.team17.preProject.domain.question.controller.QuestionControllerImpl;
import com.team17.preProject.dto.MultiResponseDto;
import com.team17.preProject.dto.SingleResponseDto;
import com.team17.preProject.helper.stub.QuestionStub;
import com.team17.preProject.domain.question.dto.QuestionDto;
import com.team17.preProject.domain.question.entity.Question;
import com.team17.preProject.domain.question.mapper.QuestionMapper;
import com.team17.preProject.domain.question.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static com.team17.preProject.helper.util.docs.Document.*;
import static com.team17.preProject.helper.util.docs.JsonDocumentUtils.getResponseFieldData;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuestionControllerImpl.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class QuestionControllerTest implements QuestionControllerTestHelper{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;
    @MockBean
    private AnswerService answerService;
    @MockBean
    private QuestionMapper mapper;

    // @Test
    public void getQuestionTest() throws Exception{
        QuestionDto.Response response = QuestionStub.getSingleResponseBody();

        given(questionService.inquireQuestion(anyLong())).willReturn(null);
        given(answerService.findAnswersByQuestion(anyLong()))
                .willReturn(null);
        given(mapper.questionToQuestionResponseDto(any()))
                .willReturn(response);

        SingleResponseDto responseDto = new SingleResponseDto(response);

        ResultActions actions = mockMvc.perform(getRequestBuilder(getURI(response.getQuestionId())));
        andExpectJsonPathEqualDto(actions, responseDto)
                .andExpect(status().isOk())
                .andDo(getMethodDocument("get-question",
                        getResponseFieldData(responseDto)
                ));
    }

    // @Test
    public void getQuestionsTest() throws Exception{
        Page<QuestionDto.SubResponse> response = QuestionStub.getMultiSubResponseBody();

        given(questionService.findQuestions(anyInt(), anyInt())).willReturn(QuestionStub.getMultiEntity());
        given(mapper.questionsToQuestionSubResponseDtos(any())).willReturn(response.getContent());

        MultiResponseDto responseDto = new MultiResponseDto(response.getContent(), response);

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", String.valueOf(response.getPageable().getPageNumber()));
        queryParams.add("size", String.valueOf(response.getSize()));

        ResultActions actions = mockMvc.perform(getRequestBuilder(getURI(), queryParams));
        andExpectJsonPathEqualDto(actions, responseDto)
                .andExpect(status().isOk())
                .andDo(getMethodDocument("get-questions",
                        List.of(queryParameterDescriptor("page", "현재 페이지"),
                                 queryParameterDescriptor("size", "페이지 크기")),
                        getResponseFieldData(responseDto)));

    }

    // @Test
    public void postQuestion() throws Exception{
        QuestionDto.Post request = (QuestionDto.Post) QuestionStub.getRequestBody().get(HttpMethod.POST);
        QuestionDto.SubResponse response = QuestionStub.getSingleSubResponseBody();

        given(mapper.questionPostDtoToQuestion(any())).willReturn(null);
        given(questionService.createQuestion(any())).willReturn(null);
        given(mapper.questionToQuestionSubResponseDto(any())).willReturn(response);

        SingleResponseDto responseDto = new SingleResponseDto<>(response);

        ResultActions actions = mockMvc.perform(postRequestBuilder(getURI(), toJsonContent(request)));
        andExpectJsonPathEqualDto(actions, responseDto)
                .andExpect(status().isCreated())
                .andDo(postMethodDocument("post-question",
                        getResponseFieldData(request),
                        getResponseFieldData(responseDto)));

    }

    // @Test
    public void patchQuestion() throws Exception{
        QuestionDto.Patch request = (QuestionDto.Patch) QuestionStub.getRequestBody().get(HttpMethod.PATCH);
        QuestionDto.SubResponse response = QuestionStub.getSingleSubResponseBody();

        given(mapper.questionPatchDtoToQuestion(any())).willReturn(new Question());
        given(questionService.updateQuestion(any())).willReturn(null);
        given(mapper.questionToQuestionSubResponseDto(any())).willReturn(response);

        SingleResponseDto responseDto = new SingleResponseDto(response);

        ResultActions actions = mockMvc.perform(patchRequestBuilder(getURI(response.getQuestionId()), toJsonContent(request)));
        andExpectJsonPathEqualDto(actions, responseDto)
                .andExpect(status().isOk())
                .andDo(patchMethodDocument("patch-question",
                        getResponseFieldData(request),
                        getResponseFieldData(responseDto)));
    }

    // @Test
    public void deleteQuestion() throws Exception{

        doNothing().when(questionService).deleteQuestion(anyLong());

        ResultActions actions = mockMvc.perform(
                deleteRequestBuilder(getURI(1L)))
                .andExpect(status().isNoContent())
                .andDo(deleteMethodDocument("delete-question"));
    }
}
