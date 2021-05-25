package com.fininfo.java.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class ReglementTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReglementType.class);
        ReglementType reglementType1 = new ReglementType();
        reglementType1.setId(1L);
        ReglementType reglementType2 = new ReglementType();
        reglementType2.setId(reglementType1.getId());
        assertThat(reglementType1).isEqualTo(reglementType2);
        reglementType2.setId(2L);
        assertThat(reglementType1).isNotEqualTo(reglementType2);
        reglementType1.setId(null);
        assertThat(reglementType1).isNotEqualTo(reglementType2);
    }
}
