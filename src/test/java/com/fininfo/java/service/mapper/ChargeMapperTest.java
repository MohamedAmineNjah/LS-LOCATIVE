package com.fininfo.java.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ChargeMapperTest {

    private ChargeMapper chargeMapper;

    @BeforeEach
    public void setUp() {
        chargeMapper = new ChargeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(chargeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(chargeMapper.fromId(null)).isNull();
    }
}
