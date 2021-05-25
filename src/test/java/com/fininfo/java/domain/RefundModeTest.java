package com.fininfo.java.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class RefundModeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefundMode.class);
        RefundMode refundMode1 = new RefundMode();
        refundMode1.setId(1L);
        RefundMode refundMode2 = new RefundMode();
        refundMode2.setId(refundMode1.getId());
        assertThat(refundMode1).isEqualTo(refundMode2);
        refundMode2.setId(2L);
        assertThat(refundMode1).isNotEqualTo(refundMode2);
        refundMode1.setId(null);
        assertThat(refundMode1).isNotEqualTo(refundMode2);
    }
}
