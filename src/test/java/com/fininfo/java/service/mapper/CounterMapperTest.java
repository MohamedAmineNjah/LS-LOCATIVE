package com.fininfo.java.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CounterMapperTest {

    private CounterMapper counterMapper;

    @BeforeEach
    public void setUp() {
        counterMapper = new CounterMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(counterMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(counterMapper.fromId(null)).isNull();
    }
}
