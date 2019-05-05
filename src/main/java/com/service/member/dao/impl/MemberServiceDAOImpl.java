package com.service.member.dao.impl;

import com.google.common.collect.Lists;
import com.service.member.dao.MemberServiceDAO;
import com.service.member.dao.MemberServiceRepository;
import com.service.member.exception.MemberNotFoundException;
import com.service.member.model.entity.Member;
import com.service.member.utils.MemberServiceUtils;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Component
public class MemberServiceDAOImpl implements MemberServiceDAO {
    @Autowired
    private MemberServiceRepository memberServiceRepository;


    @Override
    public Long create(@NonNull final Member member) {
        return memberServiceRepository.save(member).getMemberId();
    }

    @Override
    public void delete(@NonNull final List<Long> inactiveMemberIds) {
        inactiveMemberIds.forEach((memberId) -> {
            if (!memberServiceRepository.exists(memberId)) {
                throw new MemberNotFoundException("Member " + memberId + " does not exists. Can't delete.");
            }
        });

        inactiveMemberIds.forEach((memberId) -> {
            memberServiceRepository.delete(memberId);
        });
    }

    @Override
    public Member update(@NonNull final Member member) {
        if (!memberServiceRepository.exists(member.getMemberId())) {
            throw new MemberNotFoundException("Member " + member.getMemberId() + " does not exists.");
        }
        final Member existingMember = memberServiceRepository.findOne(member.getMemberId());
        MemberServiceUtils.merge(existingMember, member);
        return memberServiceRepository.save(existingMember);
    }

    @Override
    public Member read(@NonNull final Long memberId) {
        if (!memberServiceRepository.exists(memberId)) {
            throw new MemberNotFoundException("Member " + memberId + " does not exists.");
        }
        return memberServiceRepository.findOne(memberId);
    }

    @Override
    public List<Member> list() {
        return Lists.newArrayList(memberServiceRepository.findAll());
    }
}
