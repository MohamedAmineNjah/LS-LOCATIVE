package com.fininfo.java.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RefundFrequencyMapperTest {

    private RefundFrequencyMapper refundFrequencyMapper;

    @BeforeEach
    public void setUp() {
        refundFrequencyMapper = new RefundFrequencyMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(refundFrequencyMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(refundFrequencyMapper.fromId(null)).isNull();
    }
}
