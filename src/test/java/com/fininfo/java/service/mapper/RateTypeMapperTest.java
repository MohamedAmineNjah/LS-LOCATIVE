package com.fininfo.java.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RateTypeMapperTest {

    private RateTypeMapper rateTypeMapper;

    @BeforeEach
    public void setUp() {
        rateTypeMapper = new RateTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(rateTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(rateTypeMapper.fromId(null)).isNull();
    }
}
