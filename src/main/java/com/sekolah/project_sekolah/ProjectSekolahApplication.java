package com.sekolah.project_sekolah;

import com.sekolah.project_sekolah.model.Role;
import com.sekolah.project_sekolah.model.User;
import com.sekolah.project_sekolah.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ProjectSekolahApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectSekolahApplication.class, args);
	}

	// Bean encoder disediakan Spring
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CommandLineRunner initData(UserRepository userRepository, BCryptPasswordEncoder encoder) {
		return args -> {
			if (userRepository.findByEmail("admin@sekolah.com").isEmpty()) {
				User admin = new User();
				admin.setName("Admin IT");
				admin.setEmail("admin@sekolah.com");
				admin.setPassword(encoder.encode("admin123")); // hash password
				admin.setRole(Role.ADMIN_IT);
				userRepository.save(admin);
				System.out.println("✅ Admin IT berhasil ditambahkan");
			}
		};
	}
}
