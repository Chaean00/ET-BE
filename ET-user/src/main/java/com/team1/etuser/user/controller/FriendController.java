package com.team1.etuser.user.controller;

import com.team1.etuser.user.dto.SubscriptionRequestDto;
import com.team1.etuser.user.dto.SubscriptionResponseDto;
import com.team1.etuser.user.service.FriendService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@RestController
@RequestMapping("/api/users")
public class FriendController {
    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @GetMapping("/subscription")
    public ResponseEntity<SubscriptionResponseDto> getSubscriptionsByName(@RequestHeader(value = "X-Id") Long id, @RequestParam String query) {
        SubscriptionResponseDto subscriptionResponseDto = friendService.getSubscriptionsByName(id);
        return ResponseEntity.ok(subscriptionResponseDto);
    }


    @PostMapping("/subscription")
    public ResponseEntity<Void> subscribe(@RequestHeader(value = "X-Id") Long id, @RequestBody SubscriptionRequestDto requestDto) {
        friendService.subscribe(id, requestDto.getSubscribedId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/subscription")
    public ResponseEntity<Void> unsubscribe(@RequestHeader(value = "X-Id") Long id, @RequestBody SubscriptionRequestDto requestDto) {
        friendService.unsubscribe(id, requestDto.getSubscribedId());
        return ResponseEntity.ok().build();

    }
}
