package com.fininfo.java.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class BaseDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BaseDTO.class);
        BaseDTO baseDTO1 = new BaseDTO();
        baseDTO1.setId(1L);
        BaseDTO baseDTO2 = new BaseDTO();
        assertThat(baseDTO1).isNotEqualTo(baseDTO2);
        baseDTO2.setId(baseDTO1.getId());
        assertThat(baseDTO1).isEqualTo(baseDTO2);
        baseDTO2.setId(2L);
        assertThat(baseDTO1).isNotEqualTo(baseDTO2);
        baseDTO1.setId(null);
        assertThat(baseDTO1).isNotEqualTo(baseDTO2);
    }
}
