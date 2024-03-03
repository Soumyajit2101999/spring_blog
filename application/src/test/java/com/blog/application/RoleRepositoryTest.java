package com.blog.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.blog.application.model.Role;
import com.blog.application.model.User;
import com.blog.application.repository.RoleRepository;
import com.blog.application.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTest {

	@Autowired private RoleRepository repo;
	@Autowired
	private UserRepository userRepository;
    
    @Test
    public void testCreateRoles() {
        Role admin = new Role("ROLE_ADMIN");
        Role editor = new Role("ROLE_AUTHOR");
        Role customer = new Role("ROLE_VIEWER");
         
        repo.saveAll(List.of(admin, editor, customer));
         
        long count = repo.count();
        assertEquals(3, count);
    }
    
    @Test
    public void testAssignRoleToUser() {
        Integer userId = 1;
        User user = userRepository.findById(userId).get();
        user.addRole(new Role(1));
        user.addRole(new Role(2));
        user.addRole(new Role(3));
        User updatedUser = userRepository.save(user);
        assertThat(updatedUser.getRoles()).hasSize(1);
         
    }
}
