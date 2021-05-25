package com.fininfo.java.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class LeaveProcessTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveProcess.class);
        LeaveProcess leaveProcess1 = new LeaveProcess();
        leaveProcess1.setId(1L);
        LeaveProcess leaveProcess2 = new LeaveProcess();
        leaveProcess2.setId(leaveProcess1.getId());
        assertThat(leaveProcess1).isEqualTo(leaveProcess2);
        leaveProcess2.setId(2L);
        assertThat(leaveProcess1).isNotEqualTo(leaveProcess2);
        leaveProcess1.setId(null);
        assertThat(leaveProcess1).isNotEqualTo(leaveProcess2);
    }
}
