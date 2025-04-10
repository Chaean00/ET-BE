package com.team1.etuser.friend;

import com.team1.etuser.friend.domain.Friend;
import com.team1.etuser.friend.domain.FriendId;
import com.team1.etuser.user.domain.User;
import com.team1.etuser.friend.dto.FriendRes;
import com.team1.etuser.friend.dto.SubscriptionRes;
import com.team1.etuser.user.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public FriendService(FriendRepository friendRepository, UserRepository userRepository) {
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
    }

    // 구독하는 사람 목록 조회
    public SubscriptionRes getSubscriptions(Long id) {

        List<FriendRes> friends = friendRepository.findBySubscriberId(id)
                .stream()
                .map(FriendRes::new)
                .collect(Collectors.toList());

        return new SubscriptionRes(friends.size(), friends);
    }

    // 새로운 구독 추가
    @Transactional
    public void subscribe(Long id, Long subscribedId) {
        // 이미 구독한 경우 방
        FriendId friendId = new FriendId(id, subscribedId);
        if (friendRepository.existsById(friendId)) {
            throw new RuntimeException("이미 구독한 사용자입니다.");
        }

        // 사용자 조회 (존재하지 않으면 예외 발생)
        User subscriber = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("구독하는 사용자를 찾을 수 없습니다."));
        User subscribed = userRepository.findById(subscribedId)
                .orElseThrow(() -> new RuntimeException("구독 대상 사용자를 찾을 수 없습니다."));

        // 새로운 구독 관계 생성 후 저장
        Friend friend = Friend.builder()
                .friendId(friendId)
                .subscriber(subscriber)
                .subscribed(subscribed)
                .build();

        friendRepository.save(friend);
    }

    // 구독 취소
    @Transactional
    public void unsubscribe(Long id, Long subscribedId) {
        FriendId friendId = new FriendId(id, subscribedId);

        if (!friendRepository.existsById(friendId)) {
            throw new RuntimeException("구독 정보가 존재하지 않습니다.");
        }

        friendRepository.deleteById(friendId);
    }
}
