package com.fininfo.java.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class ClosingEventDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClosingEventDTO.class);
        ClosingEventDTO closingEventDTO1 = new ClosingEventDTO();
        closingEventDTO1.setId(1L);
        ClosingEventDTO closingEventDTO2 = new ClosingEventDTO();
        assertThat(closingEventDTO1).isNotEqualTo(closingEventDTO2);
        closingEventDTO2.setId(closingEventDTO1.getId());
        assertThat(closingEventDTO1).isEqualTo(closingEventDTO2);
        closingEventDTO2.setId(2L);
        assertThat(closingEventDTO1).isNotEqualTo(closingEventDTO2);
        closingEventDTO1.setId(null);
        assertThat(closingEventDTO1).isNotEqualTo(closingEventDTO2);
    }
}
