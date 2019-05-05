package com.service.member.dao.impl;

import com.service.member.dao.MemberServiceDAO;
import com.service.member.model.entity.Member;
import com.service.member.utils.MemberServiceUtils;
import lombok.NonNull;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemberServiceHibernateDAOImpl implements MemberServiceDAO {
    private final SessionFactory sessionFactory;
    private static final Logger LOG = Logger.getLogger(MemberServiceHibernateDAOImpl.class.getName());

    public MemberServiceHibernateDAOImpl() {
        try {
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            LOG.log(Level.SEVERE, "Session Factory could not be created.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Override
    public Long create(@NonNull final Member member) {
        final Session session = sessionFactory.getCurrentSession();
        final Transaction transaction = session.beginTransaction();
        final Long id = (Long) session.save(member);
        transaction.commit();
        return id;
    }

    @Override
    public Member read(@NonNull final Long memberId) {
        final Session session = sessionFactory.getCurrentSession();
        final Transaction transaction = session.beginTransaction();
        final Member member = (Member) session.get(Member.class, memberId);
        transaction.commit();
        return member;

    }

    @Override
    public void delete(@NonNull final List<Long> inactiveMemberIds) {
        final Session session = sessionFactory.getCurrentSession();
        final Transaction transaction = session.beginTransaction();

        final Query selectQuery = session.createQuery("FROM Member m WHERE m.memberId IN (:inactiveMemberIds)");
        selectQuery.setParameterList("inactiveMemberIds", inactiveMemberIds);
        final List<Member> list = selectQuery.list();

        final List<Long> imageIds = new ArrayList<>();
        for (Member member : list) {
            imageIds.add(member.getImage().getImageId());
        }

        final Query memberDeleteQuery = session.createQuery("DELETE FROM Member e WHERE e.memberId IN (:inactiveMemberIds)");
        memberDeleteQuery.setParameterList("inactiveMemberIds", inactiveMemberIds);
        memberDeleteQuery.executeUpdate();

        final Query imageDeleteQuery = session.createQuery("DELETE FROM Image i WHERE i.imageId IN (:imageIds)");
        imageDeleteQuery.setParameterList("imageIds", imageIds);
        imageDeleteQuery.executeUpdate();

        transaction.commit();
    }

    @Override
    public List<Member> list() {
        final Session session = sessionFactory.getCurrentSession();
        final Transaction transaction = session.beginTransaction();
        final Query selectQuery = session.createQuery("FROM Member");
        final List<Member> list = selectQuery.list();
        transaction.commit();
        return list;
    }

    @Override
    public Member update(@NonNull final Member member) {
        final Member existingMember = read(member.getMemberId());
        MemberServiceUtils.merge(existingMember, member);
        final Session session = sessionFactory.getCurrentSession();
        final Transaction transaction = session.beginTransaction();
        session.update(existingMember);
        transaction.commit();
        return existingMember;
    }
}