package com.my.mapService.service;

import com.my.mapService.dto.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    // In-memory List로 사용자 목록을 저장 (실제 애플리케이션에서는 DB 연동)
    private List<User> userList = new ArrayList<>();  // 여기에 List를 초기화합니다.

    // 신규회원가입 기능
    public Long signIn(User user) {
        userList.add(user);  // 사용자 추가
        System.out.println("회원가입 완료: " + user.getUserId());
        return (long) userList.size();  // 신규 사용자 리스트에서 index로 Long 리턴
    }

    // 아이디로 검색해서 1개 찾기
    public Optional<User> findById(String id) {
        return userList.stream()
                .filter(user -> user.getUserId().equals(id))
                .findFirst();  // 해당 아이디의 사용자가 있으면 반환, 없으면 빈 Optional 반환
    }

    // 전체 리스트 출력
    public List<User> findAll() {
        return userList;  // 모든 사용자 목록 반환
    }

    // 삭제 처리
    public void deleteById(String id) {
        userList.removeIf(user -> user.getUserId().equals(id));  // userId에 맞는 사용자 삭제
        System.out.println("사용자 삭제 완료: " + id);
    }

    // 사용자 수정 처리
    public void update(User updatedUser) {
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            if (user.getUserId().equals(updatedUser.getUserId())) {
                // userId는 수정하지 않고, nickname과 password만 수정
                user.setNickName(updatedUser.getNickName());
                user.setPassword(updatedUser.getPassword());
                System.out.println("사용자 수정 완료: " + updatedUser.getUserId());
                break;
            }
        }
    }
}

