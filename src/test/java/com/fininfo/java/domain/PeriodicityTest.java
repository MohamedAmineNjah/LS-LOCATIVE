package com.fininfo.java.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class PeriodicityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Periodicity.class);
        Periodicity periodicity1 = new Periodicity();
        periodicity1.setId(1L);
        Periodicity periodicity2 = new Periodicity();
        periodicity2.setId(periodicity1.getId());
        assertThat(periodicity1).isEqualTo(periodicity2);
        periodicity2.setId(2L);
        assertThat(periodicity1).isNotEqualTo(periodicity2);
        periodicity1.setId(null);
        assertThat(periodicity1).isNotEqualTo(periodicity2);
    }
}
