package com.example.homework.orders.repository;

import com.example.homework.orders.domain.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public List<Order> findOrdersByMember(String name) {
        return em.createQuery("select o from Order o"
                + " join o.member m"
                + " where m.name like :name", Order.class)
                .setParameter("name", name)
                .getResultList();
    }
}
