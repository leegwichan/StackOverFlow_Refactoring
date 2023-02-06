package com.team17.preProject.helper.stub;

import com.team17.preProject.domain.answer.dto.AnswerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class AnswerStub {

    private static AnswerDto.Response singleResponseBody;
    private static Page<AnswerDto.Response> multiResponseBody;

    static {
        singleResponseBody = new AnswerDto.Response(1, "Answer content입니다.",
                1, LocalDateTime.now(), LocalDateTime.now(), MemberStub.getSubResponse());

        ArrayList<AnswerDto.Response> multiResponseBodyContent = new ArrayList();
        multiResponseBodyContent.add(new AnswerDto.Response(1, "Answer content입니다.1",
                1, LocalDateTime.now(), LocalDateTime.now(), MemberStub.getSubResponse()));
        multiResponseBodyContent.add(new AnswerDto.Response(2, "Answer content입니다.2",
                2, LocalDateTime.now(), LocalDateTime.now(), MemberStub.getSubResponse()));
        multiResponseBodyContent.add(new AnswerDto.Response(3,  "Answer content입니다.3",
                3, LocalDateTime.now(), LocalDateTime.now(), MemberStub.getSubResponse()));
        multiResponseBodyContent.add(new AnswerDto.Response(4,  "Answer content입니다.4",
                4, LocalDateTime.now(), LocalDateTime.now(), MemberStub.getSubResponse()));

        multiResponseBody = new PageImpl(multiResponseBodyContent,
                PageRequest.of(0,10, Sort.by("answerId").descending()),
                multiResponseBodyContent.size());
    }

    public static AnswerDto.Response getSingleResponseBody(){
        return singleResponseBody;
    }

    public static Page<AnswerDto.Response> getMultiResponseBody() {
        return multiResponseBody;
    }
}
