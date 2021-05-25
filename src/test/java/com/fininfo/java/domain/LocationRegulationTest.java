package com.fininfo.java.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class LocationRegulationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationRegulation.class);
        LocationRegulation locationRegulation1 = new LocationRegulation();
        locationRegulation1.setId(1L);
        LocationRegulation locationRegulation2 = new LocationRegulation();
        locationRegulation2.setId(locationRegulation1.getId());
        assertThat(locationRegulation1).isEqualTo(locationRegulation2);
        locationRegulation2.setId(2L);
        assertThat(locationRegulation1).isNotEqualTo(locationRegulation2);
        locationRegulation1.setId(null);
        assertThat(locationRegulation1).isNotEqualTo(locationRegulation2);
    }
}
