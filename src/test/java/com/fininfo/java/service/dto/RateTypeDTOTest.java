package com.fininfo.java.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class RateTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RateTypeDTO.class);
        RateTypeDTO rateTypeDTO1 = new RateTypeDTO();
        rateTypeDTO1.setId(1L);
        RateTypeDTO rateTypeDTO2 = new RateTypeDTO();
        assertThat(rateTypeDTO1).isNotEqualTo(rateTypeDTO2);
        rateTypeDTO2.setId(rateTypeDTO1.getId());
        assertThat(rateTypeDTO1).isEqualTo(rateTypeDTO2);
        rateTypeDTO2.setId(2L);
        assertThat(rateTypeDTO1).isNotEqualTo(rateTypeDTO2);
        rateTypeDTO1.setId(null);
        assertThat(rateTypeDTO1).isNotEqualTo(rateTypeDTO2);
    }
}
