package com.team2.songgpt.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = CommentController.class)
class CommentControllerTest {

    @BeforeEach
    public void setup() {

    }

    @Test
    void saveComment() {
    }

    @Test
    void modifyComment() {
    }

    @Test
    void deleteComment() {
    }
}