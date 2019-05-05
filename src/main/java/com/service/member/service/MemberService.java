package com.service.member.service;

import com.service.member.model.entity.Member;

import java.util.List;

public interface MemberService {

    Long create(Member member);

    void delete(List<Long> inactiveMemberIds);

    Member update(Member member);

    Member read(Long memberId);

    List<Member> list();

}
