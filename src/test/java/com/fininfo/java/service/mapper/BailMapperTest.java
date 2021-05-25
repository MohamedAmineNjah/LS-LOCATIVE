package com.fininfo.java.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BailMapperTest {

    private BailMapper bailMapper;

    @BeforeEach
    public void setUp() {
        bailMapper = new BailMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(bailMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(bailMapper.fromId(null)).isNull();
    }
}
