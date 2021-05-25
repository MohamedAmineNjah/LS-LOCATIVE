package com.fininfo.java.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class BailDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BailDTO.class);
        BailDTO bailDTO1 = new BailDTO();
        bailDTO1.setId(1L);
        BailDTO bailDTO2 = new BailDTO();
        assertThat(bailDTO1).isNotEqualTo(bailDTO2);
        bailDTO2.setId(bailDTO1.getId());
        assertThat(bailDTO1).isEqualTo(bailDTO2);
        bailDTO2.setId(2L);
        assertThat(bailDTO1).isNotEqualTo(bailDTO2);
        bailDTO1.setId(null);
        assertThat(bailDTO1).isNotEqualTo(bailDTO2);
    }
}
