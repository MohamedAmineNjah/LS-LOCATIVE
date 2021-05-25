package com.fininfo.java.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoriesMapperTest {

    private InventoriesMapper inventoriesMapper;

    @BeforeEach
    public void setUp() {
        inventoriesMapper = new InventoriesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(inventoriesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(inventoriesMapper.fromId(null)).isNull();
    }
}
