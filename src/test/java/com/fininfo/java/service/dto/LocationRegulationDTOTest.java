package com.fininfo.java.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class LocationRegulationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationRegulationDTO.class);
        LocationRegulationDTO locationRegulationDTO1 = new LocationRegulationDTO();
        locationRegulationDTO1.setId(1L);
        LocationRegulationDTO locationRegulationDTO2 = new LocationRegulationDTO();
        assertThat(locationRegulationDTO1).isNotEqualTo(locationRegulationDTO2);
        locationRegulationDTO2.setId(locationRegulationDTO1.getId());
        assertThat(locationRegulationDTO1).isEqualTo(locationRegulationDTO2);
        locationRegulationDTO2.setId(2L);
        assertThat(locationRegulationDTO1).isNotEqualTo(locationRegulationDTO2);
        locationRegulationDTO1.setId(null);
        assertThat(locationRegulationDTO1).isNotEqualTo(locationRegulationDTO2);
    }
}
