package com.fininfo.java.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class CounterDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CounterDTO.class);
        CounterDTO counterDTO1 = new CounterDTO();
        counterDTO1.setId(1L);
        CounterDTO counterDTO2 = new CounterDTO();
        assertThat(counterDTO1).isNotEqualTo(counterDTO2);
        counterDTO2.setId(counterDTO1.getId());
        assertThat(counterDTO1).isEqualTo(counterDTO2);
        counterDTO2.setId(2L);
        assertThat(counterDTO1).isNotEqualTo(counterDTO2);
        counterDTO1.setId(null);
        assertThat(counterDTO1).isNotEqualTo(counterDTO2);
    }
}
