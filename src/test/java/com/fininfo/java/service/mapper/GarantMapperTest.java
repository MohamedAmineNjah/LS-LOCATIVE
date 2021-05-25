package com.fininfo.java.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GarantMapperTest {

    private GarantMapper garantMapper;

    @BeforeEach
    public void setUp() {
        garantMapper = new GarantMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(garantMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(garantMapper.fromId(null)).isNull();
    }
}
