package com.example.demofakecloud.entity;

import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;

@Setter
@Getter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;
}
