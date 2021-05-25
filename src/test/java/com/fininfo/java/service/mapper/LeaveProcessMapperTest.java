package com.fininfo.java.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LeaveProcessMapperTest {

    private LeaveProcessMapper leaveProcessMapper;

    @BeforeEach
    public void setUp() {
        leaveProcessMapper = new LeaveProcessMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(leaveProcessMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(leaveProcessMapper.fromId(null)).isNull();
    }
}
