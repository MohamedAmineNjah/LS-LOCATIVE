package com.fininfo.java.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class PeriodicityDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeriodicityDTO.class);
        PeriodicityDTO periodicityDTO1 = new PeriodicityDTO();
        periodicityDTO1.setId(1L);
        PeriodicityDTO periodicityDTO2 = new PeriodicityDTO();
        assertThat(periodicityDTO1).isNotEqualTo(periodicityDTO2);
        periodicityDTO2.setId(periodicityDTO1.getId());
        assertThat(periodicityDTO1).isEqualTo(periodicityDTO2);
        periodicityDTO2.setId(2L);
        assertThat(periodicityDTO1).isNotEqualTo(periodicityDTO2);
        periodicityDTO1.setId(null);
        assertThat(periodicityDTO1).isNotEqualTo(periodicityDTO2);
    }
}
