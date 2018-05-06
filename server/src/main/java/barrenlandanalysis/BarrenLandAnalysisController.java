package barrenlandanalysis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.InputStream;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

import org.springframework.web.servlet.ModelAndView;
import java.util.Base64;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

@Controller
public class BarrenLandAnalysisController {

	@RequestMapping("/")
	public ModelAndView index() {

		String defaultInput = "{\"48 192 351 207\", \"48 392 351 407\", \"120 52 135 547\", \"260 52 275 547\"}";
		Map<String, String> data = analyze(defaultInput);

		ModelAndView mav = new ModelAndView("analysis");
		mav.addObject("input", defaultInput);
		mav.addObject("output", data.get("output"));
		mav.addObject("visualization", data.get("visualization"));

		return mav;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/analyze", produces={"application/json"})
	public Map<String, String> analyze(@RequestBody String input) {
		BarrenLandAnalyzer analyzer = new BarrenLandAnalyzer(400, 600);


		Map<String, String> responseBody = new HashMap<String, String>();
		responseBody.put("output", analyzer.analyzeInputString(input));
		
		try {
			// get visualization image as a base64 string
			BufferedImage img = analyzer.visualizeLandRaw();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(img, "bmp", baos);
			baos.flush();
			byte[] imgInBytes = baos.toByteArray();
			baos.close();

			responseBody.put("visualization", "data:image/png;base64, " + Base64.getEncoder().encodeToString(imgInBytes));
		} catch (IOException e) {
            System.err.println("IOException when writing visualization file!");
        }
        return responseBody;
	}
}