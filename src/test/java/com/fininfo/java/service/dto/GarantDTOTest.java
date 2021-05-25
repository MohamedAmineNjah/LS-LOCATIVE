package com.fininfo.java.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class GarantDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GarantDTO.class);
        GarantDTO garantDTO1 = new GarantDTO();
        garantDTO1.setId(1L);
        GarantDTO garantDTO2 = new GarantDTO();
        assertThat(garantDTO1).isNotEqualTo(garantDTO2);
        garantDTO2.setId(garantDTO1.getId());
        assertThat(garantDTO1).isEqualTo(garantDTO2);
        garantDTO2.setId(2L);
        assertThat(garantDTO1).isNotEqualTo(garantDTO2);
        garantDTO1.setId(null);
        assertThat(garantDTO1).isNotEqualTo(garantDTO2);
    }
}
