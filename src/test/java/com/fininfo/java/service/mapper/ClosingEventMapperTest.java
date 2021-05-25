package com.fininfo.java.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ClosingEventMapperTest {

    private ClosingEventMapper closingEventMapper;

    @BeforeEach
    public void setUp() {
        closingEventMapper = new ClosingEventMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(closingEventMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(closingEventMapper.fromId(null)).isNull();
    }
}
