package com.fininfo.java.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class BailTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bail.class);
        Bail bail1 = new Bail();
        bail1.setId(1L);
        Bail bail2 = new Bail();
        bail2.setId(bail1.getId());
        assertThat(bail1).isEqualTo(bail2);
        bail2.setId(2L);
        assertThat(bail1).isNotEqualTo(bail2);
        bail1.setId(null);
        assertThat(bail1).isNotEqualTo(bail2);
    }
}
