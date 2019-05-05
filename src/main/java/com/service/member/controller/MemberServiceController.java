package com.service.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.service.member.utils.MemberServiceUtils;
import com.service.member.model.Image;
import com.service.member.model.Member;
import com.service.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@RestController
public class MemberServiceController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "/create", method = RequestMethod.POST, headers = "Content-Type=multipart/form-data")
    public Long create(@RequestParam("member") final String memberString,
                       @RequestParam("image") MultipartFile file) throws IOException, SQLException {
        final Member member = objectMapper.readValue(memberString, Member.class);
        final Blob blob = MemberServiceUtils.getBlobFromMultipartFile(file);
        final Image image = Image.builder().image(blob).build();
        member.setImage(image);
        return memberService.create(member);
    }

    @RequestMapping(value = "/read/{memberId}", method = RequestMethod.GET)
    public Member read(@PathVariable("memberId") final Long memberId) {
        return memberService.read(memberId);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete(@RequestBody final List<Long> memberIds) {
        memberService.delete(memberIds);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Member> list() {
        return memberService.list();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, headers = "Content-Type=multipart/form-data")
    public void update(@RequestParam("member") final String memberString,
                       @RequestParam(value = "image", required = false) MultipartFile file) throws IOException, SQLException {
        final Member member = objectMapper.readValue(memberString, Member.class);
        Preconditions.checkNotNull(member.getMemberId());
        if (file != null) {
            final Blob blob = MemberServiceUtils.getBlobFromMultipartFile(file);
            final Image image = Image.builder().image(blob).build();
            member.setImage(image);
        }
        memberService.update(member);
    }
}
