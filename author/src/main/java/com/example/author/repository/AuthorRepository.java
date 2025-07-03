package com.example.author.repository;

import com.example.author.aggregate.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {
    // JpaRepository가 기본 CRUD 메서드를 모두 제공
    // 필요하다면 메서드 시그니처를 여기에 추가 가능
}
