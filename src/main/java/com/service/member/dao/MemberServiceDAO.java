package com.service.member.dao;

import com.service.member.model.entity.Member;

import java.util.List;

public interface MemberServiceDAO {

    Long create(Member member);

    void delete(List<Long> inactiveMemberIds);

    Member update(Member member);

    Member read(Long memberId);

    List<Member> list();
}
