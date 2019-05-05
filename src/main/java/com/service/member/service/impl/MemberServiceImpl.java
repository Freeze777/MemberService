package com.service.member.service.impl;

import com.service.member.dao.MemberServiceDAO;
import com.service.member.model.Member;
import com.service.member.service.MemberService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Component
@Service
public class MemberServiceImpl implements MemberService {
    private MemberServiceDAO memberServiceDAO;

    @Autowired
    public MemberServiceImpl(@NonNull final MemberServiceDAO memberServiceDAO) {
        this.memberServiceDAO = memberServiceDAO;
    }

    @Override
    public Long create(@NonNull final Member member) throws SQLException {
        return memberServiceDAO.create(member);
    }

    @Override
    public void delete(@NonNull List<Long> inactiveMemberIds) {
        memberServiceDAO.delete(inactiveMemberIds);

    }

    @Override
    public void update(Member member) throws SQLException {
        memberServiceDAO.update(member);
    }

    @Override
    public Member read(Long memberId) {
        return memberServiceDAO.read(memberId);
    }

    @Override
    public List<Member> list() {
        return memberServiceDAO.list();
    }
}
