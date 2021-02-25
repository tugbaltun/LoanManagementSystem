package com.example.loanManagement;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestBase {
    protected static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
