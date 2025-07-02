package com.example.author.repository;

import com.example.author.aggregate.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID; // 이 import는 이제 필요 없습니다.

@Repository
public interface AuthorRepository extends JpaRepository<Author, String> { // ★ UUID에서 String으로 변경
}