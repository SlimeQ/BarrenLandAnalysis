package barrenlandanalysis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class BarrenLandAnalysisControllerTests {
    @Autowired
   	MockMvc mockMvc;

	@Test
	public void shouldAnalyzeInputString() throws Exception {
		mockMvc.perform(
			post("/analyze").contentType(MediaType.TEXT_PLAIN)
				.content("{\"48 192 351 200\", \"48 392 351 407\", \"100 52 135 547\", \"260 52 275 547\"}")
		).andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$.output", is("23684 184224")))
		.andExpect(jsonPath("$.visualization", containsString("data:image/png;base64, ")));
	}
}