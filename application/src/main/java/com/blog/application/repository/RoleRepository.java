package com.blog.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.application.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

}
