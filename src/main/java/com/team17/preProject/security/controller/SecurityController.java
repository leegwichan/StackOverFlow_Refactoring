package com.team17.preProject.security.controller;

import com.team17.preProject.dto.SingleResponseDto;
import com.team17.preProject.exception.businessLogic.BusinessLogicException;
import com.team17.preProject.exception.businessLogic.ExceptionCode;
import com.team17.preProject.domain.member.dto.MemberDto;
import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.domain.member.mapper.MemberMapper;
import com.team17.preProject.security.auth.PrincipalDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SecurityController {

    private final MemberMapper memberMapper;

    @GetMapping("/fail")
    public ResponseEntity loginFail(@RequestParam String message){

        return new ResponseEntity(
                new Response(400, message), HttpStatus.BAD_REQUEST
        );
    }

    @GetMapping("/success")
    public ResponseEntity successLogin() {

        return new ResponseEntity<>(
                new Response(200, "로그인 되었습니다."), HttpStatus.OK);
    }

    @Secured("ROLE_USER")
    @GetMapping("/info")
    public ResponseEntity returnLoginInfo(Authentication authentication){
        if (authentication == null) throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();

        MemberDto.Response response = memberMapper.memberToMemberResponseDto(member);
        return new ResponseEntity(
                new SingleResponseDto<>(response), HttpStatus.OK
        );
    }

    @GetMapping("/logout/success")
    public ResponseEntity successLogout(){
        return new ResponseEntity(
                new Response(200, "정상적으로 로그아웃 되었습니다."), HttpStatus.OK);
    }

    @GetMapping("/denied")
    public ResponseEntity failedSecure(@RequestParam String message){

        return new ResponseEntity(
                new Response(403, message), HttpStatus.NOT_ACCEPTABLE
        );
    }

    @GetMapping("/not-secured")
    public ResponseEntity notSecured(){

        return new ResponseEntity(
                new Response(401, "로그인이 되지 않았습니다."), HttpStatus.UNAUTHORIZED
        );
    }

    @AllArgsConstructor
    @Getter
    private static class Response{
        private int status;
        private String message;
    }
}
