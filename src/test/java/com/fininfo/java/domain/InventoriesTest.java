package com.fininfo.java.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class InventoriesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inventories.class);
        Inventories inventories1 = new Inventories();
        inventories1.setId(1L);
        Inventories inventories2 = new Inventories();
        inventories2.setId(inventories1.getId());
        assertThat(inventories1).isEqualTo(inventories2);
        inventories2.setId(2L);
        assertThat(inventories1).isNotEqualTo(inventories2);
        inventories1.setId(null);
        assertThat(inventories1).isNotEqualTo(inventories2);
    }
}
