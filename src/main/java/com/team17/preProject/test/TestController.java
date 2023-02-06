package com.team17.preProject.test;

import com.team17.preProject.dto.SingleResponseDto;
import com.team17.preProject.exception.businessLogic.BusinessLogicException;
import com.team17.preProject.exception.businessLogic.ExceptionCode;
import com.team17.preProject.helper.email.EmailSender;
import com.team17.preProject.helper.upload.S3Upload;
import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.security.auth.PrincipalDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Profile("dev")
@RestController
@AllArgsConstructor
@RequestMapping("/api/test")
public class TestController {

    private final EmailSender emailSender;
    private final S3Upload s3Upload;

    @GetMapping
    public String sayhello() {
        return "우리 팀 화이팅!!";
    }

    @Secured("ROLE_USER")
    @GetMapping("/login")
    public String checkLogin(Authentication authentication){
        if (authentication == null) throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();

        return "로그인이 된 상태입니다." + member.getEmail();
    }

    @GetMapping("/send-mail")
    public String checkSendMail(@RequestParam String email) {
        String[] mail = new String[]{email};
        try{
            emailSender.setMailSender(mail, "TEAM17 이메일 전송 테스트입니다.", "TEAM17 이메일 전송 테스트입니다.");
        } catch (Exception e){
            throw new BusinessLogicException(ExceptionCode.FAIL_SEND_EMAIL);
        }
        return email + " 메일함 확인하세요.";
    }

    @PostMapping("/upload")
    public ResponseEntity uploadFile(@RequestParam("images") MultipartFile multipartFile,
                                     @RequestParam String fileSize) throws IOException{

        String url = s3Upload.upload(multipartFile.getInputStream(),
                multipartFile.getOriginalFilename(), fileSize);
        Response response = new Response(200, url);

        return new ResponseEntity(
                new SingleResponseDto(response), HttpStatus.OK
        );
    }

    @Getter
    @AllArgsConstructor
    public static class Response{
        private int status;
        private String data;
    }


}
