package com.fininfo.java.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class LotBailTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LotBail.class);
        LotBail lotBail1 = new LotBail();
        lotBail1.setId(1L);
        LotBail lotBail2 = new LotBail();
        lotBail2.setId(lotBail1.getId());
        assertThat(lotBail1).isEqualTo(lotBail2);
        lotBail2.setId(2L);
        assertThat(lotBail1).isNotEqualTo(lotBail2);
        lotBail1.setId(null);
        assertThat(lotBail1).isNotEqualTo(lotBail2);
    }
}
