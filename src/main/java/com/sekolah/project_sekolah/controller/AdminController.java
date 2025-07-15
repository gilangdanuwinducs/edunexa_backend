package com.sekolah.project_sekolah.controller;

import com.sekolah.project_sekolah.model.User;
import com.sekolah.project_sekolah.repository.UserRepository;
import com.sekolah.project_sekolah.model.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasAuthority('ADMIN_IT')")
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PreAuthorize("hasAuthority('ADMIN_IT')")
    @GetMapping("/siswa")
    public ResponseEntity<List<User>> getAllSiswa() {
        return ResponseEntity.ok(userRepository.findByRole(Role.MURID));
    }

    @PreAuthorize("hasAuthority('ADMIN_IT')")
    @GetMapping("/guru")
    public ResponseEntity<List<User>> getAllGuru() {
        return ResponseEntity.ok(userRepository.findByRole(Role.GURU));
    }

    // @PreAuthorize("hasAuthority('ADMIN_IT')")
    // @GetMapping("/guru")
    // public ResponseEntity<List<User>> getAllGuru() {
    // return ResponseEntity.ok(userRepository.findByRole(Role.GURU));
    // }
}
