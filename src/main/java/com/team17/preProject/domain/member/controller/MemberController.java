package com.team17.preProject.domain.member.controller;

import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.domain.member.service.MemberService;
import com.team17.preProject.dto.SingleResponseDto;
import com.team17.preProject.helper.upload.S3Upload;
import com.team17.preProject.domain.member.dto.MemberDto;
import com.team17.preProject.domain.member.mapper.MemberMapper;
import com.team17.preProject.security.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Positive;
import java.io.IOException;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Validated
public class MemberController {

    private final MemberService memberService;
    private final SecurityService securityService;
    private final MemberMapper mapper;
    private final S3Upload s3Upload;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") @Positive long memberId) {

        Member findMember = memberService.findMember(memberId);
        MemberDto.Response response = mapper.memberToMemberResponseDto(findMember);
        return new ResponseEntity(
                new SingleResponseDto(response), HttpStatus.OK);

    }

    @GetMapping("/find-password")
    public ResponseEntity findPassword(@RequestParam @Email(message = "이메일 형식이 아닙니다.") String email){
        memberService.resetPasswordByEmail(email);
        return new ResponseEntity(new SingleResponseDto<>("이메일 발송 완료."), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity postMember(@RequestBody @Valid MemberDto.Post requestBody){

        Member member = mapper.memberPostDtoToMember(requestBody);
        member.updatePassword(bCryptPasswordEncoder.encode(requestBody.getPassword()));

        Member postMember = memberService.createMember(member);
        MemberDto.Response response = mapper.memberToMemberResponseDto(member);

        return new ResponseEntity(
                new SingleResponseDto<>(response), HttpStatus.OK
        );
    }

    @PatchMapping("/{member-id}")
    @Secured("ROLE_USER")
    public ResponseEntity patchMember(@PathVariable("member-id") @Positive long memberId,
                                      @RequestBody MemberDto.Patch requestBody,
                                      Authentication authentication) {
        securityService.checkMemberEqual(authentication, memberId);

        requestBody.setMemberId(memberId);
        Member member = mapper.memberPatchDtoToMember(requestBody);

        Member updateMember = memberService.updateMember(member);
        MemberDto.Response response = mapper.memberToMemberResponseDto(updateMember);
        return new ResponseEntity<>(
                new SingleResponseDto(response), HttpStatus.OK
        );

    }

    @PatchMapping("/profile-image/{member-id}")
    @Secured("ROLE_USER")
    public ResponseEntity patchImage(@PathVariable("member-id") @Positive long memberId,
                                     Authentication authentication,
                                     @RequestParam("images") MultipartFile multipartFile,
                                     @RequestParam String fileSize) throws IOException {

        securityService.checkMemberEqual(authentication, memberId);
        String url = s3Upload.uploadImage(multipartFile.getInputStream(),
                multipartFile.getOriginalFilename(), fileSize);

        Member member = Member.builder()
                .memberId(memberId)
                .image(url).build();
        Member updateMember = memberService.updateMember(member);
        MemberDto.Response response = mapper.memberToMemberResponseDto(updateMember);
        return new ResponseEntity<>(
                new SingleResponseDto(response), HttpStatus.OK
        );
    }

    @DeleteMapping("/{member-id}")
    @Secured("ROLE_USER")
    public ResponseEntity deleteMember(@PathVariable("member-id") @Positive long memberId,
                                       Authentication authentication) {
        securityService.checkMemberEqual(authentication, memberId);

        memberService.deleteMember(memberId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/check")
    public ResponseEntity checkEmail(@RequestParam @Email(message = "이메일 형식이 아닙니다.") String email){
        memberService.checkEmailValidate(email);
        return new ResponseEntity(HttpStatus.OK);
    }
}
