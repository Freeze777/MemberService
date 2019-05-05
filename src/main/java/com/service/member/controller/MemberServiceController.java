package com.service.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.member.model.entity.Image;
import com.service.member.model.entity.Member;
import com.service.member.service.MemberService;
import com.service.member.utils.MemberServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Rest controller that handles API requests
 */
@ControllerAdvice
@RestController
public class MemberServiceController extends ResponseEntityExceptionHandler {

    @Autowired
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "/create", method = RequestMethod.POST, headers = "Content-Type=multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestParam("member") final String memberString,
                       @RequestParam("image") MultipartFile file)
            throws IOException, SQLException, ValidationException {
        final Member member = objectMapper.readValue(memberString, Member.class);
        final Image image = MemberServiceUtils.getImageFromMultipartFile(file);
        member.setImage(image);
        return memberService.create(member);
    }

    @RequestMapping(value = "/read/{memberId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Member read(@PathVariable("memberId") final Long memberId) {
        return memberService.read(memberId);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody final List<Long> memberIds) {
        memberService.delete(memberIds);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Member> list() {
        return memberService.list();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, headers = "Content-Type=multipart/form-data")
    @ResponseStatus(HttpStatus.OK)
    public Member update(@RequestParam("member") final String memberString,
                         @RequestParam(value = "image", required = false) MultipartFile file)
            throws IOException, SQLException, ValidationException {
        final Member member = objectMapper.readValue(memberString, Member.class);
        if (member.getMemberId() == null) {
            throw new ValidationException("Member ID cannot be null for update");
        }
        if (file != null) {
            final Image image = MemberServiceUtils.getImageFromMultipartFile(file);
            member.setImage(image);
        }
        return memberService.update(member);
    }
}
