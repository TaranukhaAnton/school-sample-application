package com.school.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.school.web.rest.TestUtil;

public class SchollClassTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchollClass.class);
        SchollClass schollClass1 = new SchollClass();
        schollClass1.setId(1L);
        SchollClass schollClass2 = new SchollClass();
        schollClass2.setId(schollClass1.getId());
        assertThat(schollClass1).isEqualTo(schollClass2);
        schollClass2.setId(2L);
        assertThat(schollClass1).isNotEqualTo(schollClass2);
        schollClass1.setId(null);
        assertThat(schollClass1).isNotEqualTo(schollClass2);
    }
}
