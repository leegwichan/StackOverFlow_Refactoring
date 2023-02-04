package com.team17.preProject.member.controller;

import com.team17.preProject.dto.SingleResponseDto;
import com.team17.preProject.helper.stub.MemberStub;
import com.team17.preProject.helper.util.docs.JsonDocumentUtils;
import com.team17.preProject.member.dto.MemberDto;
import com.team17.preProject.member.entity.Member;
import com.team17.preProject.member.mapper.MemberMapper;
import com.team17.preProject.member.service.MemberService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;

import static com.team17.preProject.helper.util.docs.Document.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.team17.preProject.helper.util.docs.JsonDocumentUtils.*;

import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.Map;

@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class MemberControllerTest implements MemberControllerTestHelper {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;
    @MockBean
    private MemberMapper mapper;

    //@Test
    public void getMemberTest() throws Exception{
        MemberDto.Response response = MemberStub.getSingleResponseBody();

        given(memberService.findMember(response.getMemberId()))
                .willReturn(null);
        given(mapper.memberToMemberResponseDto(any()))
                .willReturn(response);

        SingleResponseDto responseDto = new SingleResponseDto<>(response);

        ResultActions actions = mockMvc.perform(getRequestBuilder(getURI(response.getMemberId())));
        andExpectJsonPathEqualDto(actions, responseDto)
                .andExpect(status().isOk())
                .andDo(getMethodDocument("get-member",
                        getResponseFieldData(responseDto)));

    }

    //@Test
    public void patchMember() throws Exception{
        MemberDto.Patch request = (MemberDto.Patch) MemberStub.getRequestBody().get(HttpMethod.PATCH);
        MemberDto.Response response = MemberStub.getSingleResponseBody();

        given(mapper.memberPatchDtoToMember(Mockito.any())).willReturn(new Member());
        given(memberService.updateMember(Mockito.any())).willReturn(null);
        given(mapper.memberToMemberResponseDto(Mockito.any())).willReturn(response);

        SingleResponseDto responseDto = new SingleResponseDto<>(response);

        Map<JsonDocumentUtils.Option, String[]> requestOption = new HashMap<>();
        requestOption.put(Option.IGNORED, new String[]{"memberId"});
        requestOption.put(Option.OPTIONAL, new String[]{"displayName", "image", "location", "title", "aboutMe"});

        ResultActions actions = mockMvc.perform(
                patchRequestBuilder(getURI(1L), toJsonContent(request)));
        andExpectJsonPathEqualDto(actions, responseDto)
                .andExpect(status().isOk())
                .andDo(patchMethodDocument("patch-member",
                        getResponseFieldData(request, requestOption),
                        getResponseFieldData(responseDto)));
    }

    //@Test
    public void deleteMember() throws Exception{
        doNothing().when(memberService).deleteMember(anyLong());

        ResultActions actions = mockMvc.perform(
                deleteRequestBuilder(getURI(1L)))
                .andExpect(status().isNoContent())
                .andDo(deleteMethodDocument("delete-member"));
    }


}
