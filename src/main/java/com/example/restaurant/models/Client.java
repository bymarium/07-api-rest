package com.example.restaurant.models;

import com.example.restaurant.constants.Type;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "clients")
public class Client{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String lastName;
	private String email;
	private String userType = Type.COMMON.getName();
	private Float adjust = 1f;

	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
	private List<Order> orders;

	public Client(Long id, String name, String lastName, String email) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
	}

	public Client() {
	}
}