package com.fininfo.java.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class GarantTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Garant.class);
        Garant garant1 = new Garant();
        garant1.setId(1L);
        Garant garant2 = new Garant();
        garant2.setId(garant1.getId());
        assertThat(garant1).isEqualTo(garant2);
        garant2.setId(2L);
        assertThat(garant1).isNotEqualTo(garant2);
        garant1.setId(null);
        assertThat(garant1).isNotEqualTo(garant2);
    }
}
