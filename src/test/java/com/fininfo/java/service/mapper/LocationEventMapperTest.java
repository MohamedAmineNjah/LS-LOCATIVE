package com.fininfo.java.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LocationEventMapperTest {

    private LocationEventMapper locationEventMapper;

    @BeforeEach
    public void setUp() {
        locationEventMapper = new LocationEventMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(locationEventMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(locationEventMapper.fromId(null)).isNull();
    }
}
