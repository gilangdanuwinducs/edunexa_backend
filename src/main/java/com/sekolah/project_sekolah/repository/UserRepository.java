package com.sekolah.project_sekolah.repository;

import com.sekolah.project_sekolah.model.Role;
import com.sekolah.project_sekolah.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findByRole(Role role);
}
