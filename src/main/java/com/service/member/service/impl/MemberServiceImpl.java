package com.service.member.service.impl;

import com.service.member.dao.MemberServiceDAO;
import com.service.member.model.entity.Member;
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

    @Autowired
    private MemberServiceDAO memberServiceDAO;

    @Override
    public Long create(@NonNull final Member member) {
        return memberServiceDAO.create(member);
    }

    @Override
    public void delete(@NonNull List<Long> inactiveMemberIds) {
        memberServiceDAO.delete(inactiveMemberIds);
    }

    @Override
    public Member update(Member member) {
        return memberServiceDAO.update(member);
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
