package com.team1.etarcade.egg.service.egg;


import com.team1.etarcade.egg.Client.UserClient;
import com.team1.etarcade.egg.domain.Egg;
import com.team1.etarcade.egg.dto.EggResponseDTO;
import com.team1.etarcade.egg.dto.UserFeignResponseDTO;
import com.team1.etarcade.egg.repository.EggRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EggService {


    private final EggRepository eggRepository;
    private final UserClient userClient;

    @Transactional
    public EggResponseDTO acquireEgg(Long userId) {
        // FeignClient를 통해 사용자 정보 조회
        UserFeignResponseDTO userInfo = userClient.getUserInfo(userId);
        if (userInfo.getPoint() < 100) {
            throw new IllegalStateException("포인트가 부족합니다.");
        }

        // 알 생성
        Egg newEgg = Egg.builder()
                .userId(userId)
                .isHatchable(false)
                .isHatched(false)
                .build();
        Egg savedEgg = eggRepository.save(newEgg);

        // 포인트 차감 (별도의 Feign API 필요할 수 있음)
        // userClient.deductPoints(userId, 100);  <- 추후 구현

        return new EggResponseDTO(savedEgg.getId(), userId, userInfo.getPoint() - 100, "알을 획득했습니다!");
    }

    public List<EggResponseDTO> getAllEggs() {

            return eggRepository.findAll().stream()
                    .map(egg -> new EggResponseDTO(
                            egg.getId(),
                            egg.getUserId(),
                            100,
                            egg.isHatchable() ? "부화 가능" : "부화 대기 중"
                    ))
                    .toList();

    }
}
