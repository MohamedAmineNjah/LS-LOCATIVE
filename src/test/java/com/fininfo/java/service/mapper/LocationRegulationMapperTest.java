package com.fininfo.java.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LocationRegulationMapperTest {

    private LocationRegulationMapper locationRegulationMapper;

    @BeforeEach
    public void setUp() {
        locationRegulationMapper = new LocationRegulationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(locationRegulationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(locationRegulationMapper.fromId(null)).isNull();
    }
}
