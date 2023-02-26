package com.team17.preProject.domain.answer.service;

import com.team17.preProject.domain.answer.repository.AnswerRepository;
import com.team17.preProject.domain.member.service.MemberService;
import com.team17.preProject.domain.question.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = {AnswerServiceImpl.class})
public class AnswerServiceTest {

    @Autowired private AnswerService answerService;
    @MockBean private AnswerRepository answerRepository;
    @MockBean private QuestionService questionService;
    @MockBean private MemberService memberService;


}
