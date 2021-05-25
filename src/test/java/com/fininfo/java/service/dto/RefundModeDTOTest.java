package com.fininfo.java.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class RefundModeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefundModeDTO.class);
        RefundModeDTO refundModeDTO1 = new RefundModeDTO();
        refundModeDTO1.setId(1L);
        RefundModeDTO refundModeDTO2 = new RefundModeDTO();
        assertThat(refundModeDTO1).isNotEqualTo(refundModeDTO2);
        refundModeDTO2.setId(refundModeDTO1.getId());
        assertThat(refundModeDTO1).isEqualTo(refundModeDTO2);
        refundModeDTO2.setId(2L);
        assertThat(refundModeDTO1).isNotEqualTo(refundModeDTO2);
        refundModeDTO1.setId(null);
        assertThat(refundModeDTO1).isNotEqualTo(refundModeDTO2);
    }
}
