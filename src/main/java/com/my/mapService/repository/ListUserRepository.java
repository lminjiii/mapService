package com.my.mapService.repository;

import com.my.mapService.dto.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListUserRepository implements UserRepository {

    private List<User> userStore = new ArrayList<>();

    @Override
    public User save(User user) {
        userStore.add(user);
        return user;
    }

    @Override
    public Optional<User> findById(String userId) {
        return userStore.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userStore);  // 전체 사용자 목록 반환
    }

    @Override
    public void deleteById(String userId) {
        userStore.removeIf(user -> user.getUserId().equals(userId));  // userId에 해당하는 사용자 삭제
    }

    @Override
    public User updateById(String userId, User updatedUser) {
        for (int i = 0; i < userStore.size(); i++) {
            User user = userStore.get(i);
            if (user.getUserId().equals(userId)) {
                userStore.set(i, updatedUser);  // 사용자 정보 업데이트
                return updatedUser;
            }
        }
        return null;  // 사용자가 없으면 null 반환
    }
}
