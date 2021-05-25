package com.fininfo.java.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class LocationEventDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationEventDTO.class);
        LocationEventDTO locationEventDTO1 = new LocationEventDTO();
        locationEventDTO1.setId(1L);
        LocationEventDTO locationEventDTO2 = new LocationEventDTO();
        assertThat(locationEventDTO1).isNotEqualTo(locationEventDTO2);
        locationEventDTO2.setId(locationEventDTO1.getId());
        assertThat(locationEventDTO1).isEqualTo(locationEventDTO2);
        locationEventDTO2.setId(2L);
        assertThat(locationEventDTO1).isNotEqualTo(locationEventDTO2);
        locationEventDTO1.setId(null);
        assertThat(locationEventDTO1).isNotEqualTo(locationEventDTO2);
    }
}
