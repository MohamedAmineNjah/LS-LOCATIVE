package com.fininfo.java.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BaseMapperTest {

    private BaseMapper baseMapper;

    @BeforeEach
    public void setUp() {
        baseMapper = new BaseMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(baseMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(baseMapper.fromId(null)).isNull();
    }
}
