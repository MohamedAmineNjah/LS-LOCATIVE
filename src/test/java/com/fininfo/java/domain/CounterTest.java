package com.fininfo.java.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fininfo.java.web.rest.TestUtil;

public class CounterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Counter.class);
        Counter counter1 = new Counter();
        counter1.setId(1L);
        Counter counter2 = new Counter();
        counter2.setId(counter1.getId());
        assertThat(counter1).isEqualTo(counter2);
        counter2.setId(2L);
        assertThat(counter1).isNotEqualTo(counter2);
        counter1.setId(null);
        assertThat(counter1).isNotEqualTo(counter2);
    }
}
