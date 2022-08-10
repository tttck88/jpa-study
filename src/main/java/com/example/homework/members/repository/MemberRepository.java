package com.example.homework.members.repository;

import com.example.homework.members.domain.dto.Paging;
import com.example.homework.members.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public List<Member> findMember(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<Member> findMembers(Member member, Paging paging) {
        StringBuffer filterQuery = new StringBuffer();

        if(member.getName() != null) {
            if(filterQuery.toString().equals("")) {
                filterQuery.append(" where m.name = :name");
            } else {
                filterQuery.append(" and m.name = :name");
            }
        }
        if(member.getEmail() != null) {
            if(filterQuery.toString().equals("")) {
                filterQuery.append(" where m.email = :email");
            } else {
                filterQuery.append(" and m.email = :email");
            }
        }

        TypedQuery<Member> query = em.createQuery("select m from Member m"
                        + filterQuery
                , Member.class);

        if(member.getName() != null) {
            query.setParameter("name", member.getName());
        }
        if(member.getEmail() != null) {
            query.setParameter("email", member.getEmail());
        }

        query.setFirstResult(paging.getOffset()).setMaxResults(paging.getLimit());

        return query.getResultList();
    }
}
