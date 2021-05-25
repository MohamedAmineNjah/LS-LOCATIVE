package com.fininfo.java.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class LocataireTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Locataire.class);
        Locataire locataire1 = new Locataire();
        locataire1.setId(1L);
        Locataire locataire2 = new Locataire();
        locataire2.setId(locataire1.getId());
        assertThat(locataire1).isEqualTo(locataire2);
        locataire2.setId(2L);
        assertThat(locataire1).isNotEqualTo(locataire2);
        locataire1.setId(null);
        assertThat(locataire1).isNotEqualTo(locataire2);
    }
}
