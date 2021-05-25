package com.fininfo.java.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class RefundFrequencyDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefundFrequencyDTO.class);
        RefundFrequencyDTO refundFrequencyDTO1 = new RefundFrequencyDTO();
        refundFrequencyDTO1.setId(1L);
        RefundFrequencyDTO refundFrequencyDTO2 = new RefundFrequencyDTO();
        assertThat(refundFrequencyDTO1).isNotEqualTo(refundFrequencyDTO2);
        refundFrequencyDTO2.setId(refundFrequencyDTO1.getId());
        assertThat(refundFrequencyDTO1).isEqualTo(refundFrequencyDTO2);
        refundFrequencyDTO2.setId(2L);
        assertThat(refundFrequencyDTO1).isNotEqualTo(refundFrequencyDTO2);
        refundFrequencyDTO1.setId(null);
        assertThat(refundFrequencyDTO1).isNotEqualTo(refundFrequencyDTO2);
    }
}
