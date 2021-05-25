package com.fininfo.java.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class LeaveProcessDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveProcessDTO.class);
        LeaveProcessDTO leaveProcessDTO1 = new LeaveProcessDTO();
        leaveProcessDTO1.setId(1L);
        LeaveProcessDTO leaveProcessDTO2 = new LeaveProcessDTO();
        assertThat(leaveProcessDTO1).isNotEqualTo(leaveProcessDTO2);
        leaveProcessDTO2.setId(leaveProcessDTO1.getId());
        assertThat(leaveProcessDTO1).isEqualTo(leaveProcessDTO2);
        leaveProcessDTO2.setId(2L);
        assertThat(leaveProcessDTO1).isNotEqualTo(leaveProcessDTO2);
        leaveProcessDTO1.setId(null);
        assertThat(leaveProcessDTO1).isNotEqualTo(leaveProcessDTO2);
    }
}
