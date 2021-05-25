package com.fininfo.java.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FrequencyMapperTest {

    private FrequencyMapper frequencyMapper;

    @BeforeEach
    public void setUp() {
        frequencyMapper = new FrequencyMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(frequencyMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(frequencyMapper.fromId(null)).isNull();
    }
}
