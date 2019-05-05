package com.service.member.service;

import com.service.member.model.Member;

import java.sql.SQLException;
import java.util.List;

public interface MemberService {

    Long create(Member member) throws SQLException;

    void delete(List<Long> inactiveMemberIds);

    void update(Member member) throws SQLException;

    Member read(Long memberId);

    List<Member> list();


}
