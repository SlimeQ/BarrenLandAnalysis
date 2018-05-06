package barrenlandanalysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private BarrenLandAnalysisController controller;

    @Test
    public void contextLoads() throws Exception {
    	assertThat(controller).isNotNull();
    }
}