package com.example.francegrance;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.AntPathMatcher;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class FranceGranceApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    public void testPathMatcher() {
        final AntPathMatcher pathMatcher = new AntPathMatcher();
        assertTrue(pathMatcher.match("/images/**", "/images/home.png"));
    }

}
