package com.team1.etarcade.quiz.dto;

import com.team1.etarcade.quiz.domain.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuizRes {

    private Long id;

    private String title;

    private Difficulty difficulty;

    private boolean answer;

}