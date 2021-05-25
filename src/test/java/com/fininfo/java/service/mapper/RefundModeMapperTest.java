package com.fininfo.java.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RefundModeMapperTest {

    private RefundModeMapper refundModeMapper;

    @BeforeEach
    public void setUp() {
        refundModeMapper = new RefundModeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(refundModeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(refundModeMapper.fromId(null)).isNull();
    }
}
