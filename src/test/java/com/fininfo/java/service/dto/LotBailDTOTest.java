package com.fininfo.java.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class LotBailDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LotBailDTO.class);
        LotBailDTO lotBailDTO1 = new LotBailDTO();
        lotBailDTO1.setId(1L);
        LotBailDTO lotBailDTO2 = new LotBailDTO();
        assertThat(lotBailDTO1).isNotEqualTo(lotBailDTO2);
        lotBailDTO2.setId(lotBailDTO1.getId());
        assertThat(lotBailDTO1).isEqualTo(lotBailDTO2);
        lotBailDTO2.setId(2L);
        assertThat(lotBailDTO1).isNotEqualTo(lotBailDTO2);
        lotBailDTO1.setId(null);
        assertThat(lotBailDTO1).isNotEqualTo(lotBailDTO2);
    }
}
