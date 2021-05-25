package com.fininfo.java.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class ClosingEventTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClosingEvent.class);
        ClosingEvent closingEvent1 = new ClosingEvent();
        closingEvent1.setId(1L);
        ClosingEvent closingEvent2 = new ClosingEvent();
        closingEvent2.setId(closingEvent1.getId());
        assertThat(closingEvent1).isEqualTo(closingEvent2);
        closingEvent2.setId(2L);
        assertThat(closingEvent1).isNotEqualTo(closingEvent2);
        closingEvent1.setId(null);
        assertThat(closingEvent1).isNotEqualTo(closingEvent2);
    }
}
