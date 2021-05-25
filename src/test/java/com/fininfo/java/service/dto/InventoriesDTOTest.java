package com.fininfo.java.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class InventoriesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventoriesDTO.class);
        InventoriesDTO inventoriesDTO1 = new InventoriesDTO();
        inventoriesDTO1.setId(1L);
        InventoriesDTO inventoriesDTO2 = new InventoriesDTO();
        assertThat(inventoriesDTO1).isNotEqualTo(inventoriesDTO2);
        inventoriesDTO2.setId(inventoriesDTO1.getId());
        assertThat(inventoriesDTO1).isEqualTo(inventoriesDTO2);
        inventoriesDTO2.setId(2L);
        assertThat(inventoriesDTO1).isNotEqualTo(inventoriesDTO2);
        inventoriesDTO1.setId(null);
        assertThat(inventoriesDTO1).isNotEqualTo(inventoriesDTO2);
    }
}
