package com.fininfo.java.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class LocataireDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocataireDTO.class);
        LocataireDTO locataireDTO1 = new LocataireDTO();
        locataireDTO1.setId(1L);
        LocataireDTO locataireDTO2 = new LocataireDTO();
        assertThat(locataireDTO1).isNotEqualTo(locataireDTO2);
        locataireDTO2.setId(locataireDTO1.getId());
        assertThat(locataireDTO1).isEqualTo(locataireDTO2);
        locataireDTO2.setId(2L);
        assertThat(locataireDTO1).isNotEqualTo(locataireDTO2);
        locataireDTO1.setId(null);
        assertThat(locataireDTO1).isNotEqualTo(locataireDTO2);
    }
}
