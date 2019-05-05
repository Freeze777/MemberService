package com.service.member.dao;

import com.service.member.model.entity.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberServiceRepository extends CrudRepository<Member, Long>{
}