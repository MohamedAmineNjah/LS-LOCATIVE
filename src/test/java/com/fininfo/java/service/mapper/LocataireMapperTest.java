package com.fininfo.java.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LocataireMapperTest {

    private LocataireMapper locataireMapper;

    @BeforeEach
    public void setUp() {
        locataireMapper = new LocataireMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(locataireMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(locataireMapper.fromId(null)).isNull();
    }
}
