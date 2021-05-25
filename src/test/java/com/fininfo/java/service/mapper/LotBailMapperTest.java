package com.fininfo.java.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LotBailMapperTest {

    private LotBailMapper lotBailMapper;

    @BeforeEach
    public void setUp() {
        lotBailMapper = new LotBailMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(lotBailMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(lotBailMapper.fromId(null)).isNull();
    }
}
