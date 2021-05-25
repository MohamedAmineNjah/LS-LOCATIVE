package com.fininfo.java.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class BaseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Base.class);
        Base base1 = new Base();
        base1.setId(1L);
        Base base2 = new Base();
        base2.setId(base1.getId());
        assertThat(base1).isEqualTo(base2);
        base2.setId(2L);
        assertThat(base1).isNotEqualTo(base2);
        base1.setId(null);
        assertThat(base1).isNotEqualTo(base2);
    }
}
