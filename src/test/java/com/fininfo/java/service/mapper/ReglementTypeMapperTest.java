package com.fininfo.java.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ReglementTypeMapperTest {

    private ReglementTypeMapper reglementTypeMapper;

    @BeforeEach
    public void setUp() {
        reglementTypeMapper = new ReglementTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(reglementTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(reglementTypeMapper.fromId(null)).isNull();
    }
}
