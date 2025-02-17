package com.my.mapService.repository;

import com.my.mapService.dto.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class ListUserRepositoryTest {

    ListUserRepository repository = new ListUserRepository();
    User user1, user2;

    @BeforeEach
    void setUp() {
        // 테스트를 위한 User 객체 준비
        user1 = new User();
        user1.setUserId("user1");
        user1.setNickName("Nick1");
        user1.setPassword("pass1");

        user2 = new User();
        user2.setUserId("user2");
        user2.setNickName("Nick2");
        user2.setPassword("pass2");

        // 저장
        repository.save(user1);
        repository.save(user2);
    }

    @AfterEach
    void tearDown() {
        // 각 테스트 후 사용자 목록 초기화
        repository.deleteById(user1.getUserId());
        repository.deleteById(user2.getUserId());
    }

    @Test
    @DisplayName("사용자 저장 테스트")
    void save() {
        // Given
        User newUser = new User();
        newUser.setUserId("user3");
        newUser.setNickName("Nick3");
        newUser.setPassword("pass3");

        // When
        User savedUser = repository.save(newUser);

        // Then
        assertNotNull(savedUser.getUserId());  // ID가 자동으로 생성되었는지 확인
        assertEquals(newUser.getUserId(), savedUser.getUserId());
        assertEquals(newUser.getNickName(), savedUser.getNickName());
    }

    @Test
    @DisplayName("ID로 사용자 검색 테스트")
    void findById() {
        // When
        User foundUser = repository.findById(user1.getUserId()).orElse(null);

        // Then
        assertNotNull(foundUser);
        assertEquals(user1.getUserId(), foundUser.getUserId());
        assertEquals(user1.getNickName(), foundUser.getNickName());
    }

    @Test
    @DisplayName("ID로 사용자 검색 실패 테스트")
    void findById_Fail() {
        // When
        User foundUser = repository.findById("nonExistentUser").orElse(null);

        // Then
        assertNull(foundUser);  // 존재하지 않는 사용자 검색 시 null 반환
    }

    @Test
    @DisplayName("전체 사용자 검색 테스트")
    void findAll() {
        // When
        List<User> allUsers = repository.findAll();

        // Then
        assertEquals(2, allUsers.size());  // 두 명의 사용자 존재해야 함
    }

    @Test
    @DisplayName("ID로 사용자 삭제 테스트")
    void deleteById() {
        // When
        repository.deleteById(user1.getUserId());

        // Then
        User foundUser = repository.findById(user1.getUserId()).orElse(null);
        assertNull(foundUser);  // 삭제 후 해당 사용자는 존재하지 않아야 함
    }

    @Test
    @DisplayName("사용자 정보 수정 테스트")
    void updateById() {
        // Given
        User updatedUser = new User();
        updatedUser.setUserId(user2.getUserId());
        updatedUser.setNickName("UpdatedNick");
        updatedUser.setPassword("UpdatedPass");

        // When
        User result = repository.updateById(user2.getUserId(), updatedUser);

        // Then
        assertNotNull(result);
        assertEquals("UpdatedNick", result.getNickName());
        assertEquals("UpdatedPass", result.getPassword());
    }
}
