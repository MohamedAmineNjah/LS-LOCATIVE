package com.fininfo.java.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class LocationEventTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationEvent.class);
        LocationEvent locationEvent1 = new LocationEvent();
        locationEvent1.setId(1L);
        LocationEvent locationEvent2 = new LocationEvent();
        locationEvent2.setId(locationEvent1.getId());
        assertThat(locationEvent1).isEqualTo(locationEvent2);
        locationEvent2.setId(2L);
        assertThat(locationEvent1).isNotEqualTo(locationEvent2);
        locationEvent1.setId(null);
        assertThat(locationEvent1).isNotEqualTo(locationEvent2);
    }
}
