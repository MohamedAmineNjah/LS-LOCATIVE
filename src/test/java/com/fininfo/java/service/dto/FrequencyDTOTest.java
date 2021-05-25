package com.fininfo.java.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class FrequencyDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FrequencyDTO.class);
        FrequencyDTO frequencyDTO1 = new FrequencyDTO();
        frequencyDTO1.setId(1L);
        FrequencyDTO frequencyDTO2 = new FrequencyDTO();
        assertThat(frequencyDTO1).isNotEqualTo(frequencyDTO2);
        frequencyDTO2.setId(frequencyDTO1.getId());
        assertThat(frequencyDTO1).isEqualTo(frequencyDTO2);
        frequencyDTO2.setId(2L);
        assertThat(frequencyDTO1).isNotEqualTo(frequencyDTO2);
        frequencyDTO1.setId(null);
        assertThat(frequencyDTO1).isNotEqualTo(frequencyDTO2);
    }
}
