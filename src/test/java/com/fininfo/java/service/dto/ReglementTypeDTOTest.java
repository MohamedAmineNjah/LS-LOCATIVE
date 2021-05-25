package com.fininfo.java.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class ReglementTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReglementTypeDTO.class);
        ReglementTypeDTO reglementTypeDTO1 = new ReglementTypeDTO();
        reglementTypeDTO1.setId(1L);
        ReglementTypeDTO reglementTypeDTO2 = new ReglementTypeDTO();
        assertThat(reglementTypeDTO1).isNotEqualTo(reglementTypeDTO2);
        reglementTypeDTO2.setId(reglementTypeDTO1.getId());
        assertThat(reglementTypeDTO1).isEqualTo(reglementTypeDTO2);
        reglementTypeDTO2.setId(2L);
        assertThat(reglementTypeDTO1).isNotEqualTo(reglementTypeDTO2);
        reglementTypeDTO1.setId(null);
        assertThat(reglementTypeDTO1).isNotEqualTo(reglementTypeDTO2);
    }
}
