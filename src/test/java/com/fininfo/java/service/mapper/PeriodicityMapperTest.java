package com.fininfo.java.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PeriodicityMapperTest {

    private PeriodicityMapper periodicityMapper;

    @BeforeEach
    public void setUp() {
        periodicityMapper = new PeriodicityMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(periodicityMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(periodicityMapper.fromId(null)).isNull();
    }
}
