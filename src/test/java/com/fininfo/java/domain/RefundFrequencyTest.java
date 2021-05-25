package com.fininfo.java.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class RefundFrequencyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefundFrequency.class);
        RefundFrequency refundFrequency1 = new RefundFrequency();
        refundFrequency1.setId(1L);
        RefundFrequency refundFrequency2 = new RefundFrequency();
        refundFrequency2.setId(refundFrequency1.getId());
        assertThat(refundFrequency1).isEqualTo(refundFrequency2);
        refundFrequency2.setId(2L);
        assertThat(refundFrequency1).isNotEqualTo(refundFrequency2);
        refundFrequency1.setId(null);
        assertThat(refundFrequency1).isNotEqualTo(refundFrequency2);
    }
}
