package com.example.homework.members.domain.entity;

import com.example.homework.members.domain.enums.Gender;
import com.example.homework.orders.domain.entity.Order;
import lombok.*;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = false)
	private String nickName;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String phoneNum;

	@Column(nullable = false)
	private String email;

	private Gender gender;

	private String salt;

	@OneToMany(mappedBy = "member")
	private List<Order> orders;

	public Order getRecentOrder() {
		return orders.stream().sorted(Comparator.comparing(Order::getOrderDate).reversed()).findFirst().orElse(null);
	}
}
