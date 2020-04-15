package com.sap;

import java.util.HashMap;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class TextAnanlysisController {

     @GetMapping("/textAnalysis")
	HashMap<String, String> performAnalysis(@NotNull String input) {
		HashMap<String, String> rtnMap = new HashMap<String, String>();
		rtnMap.put("No of Words", String.valueOf(input.split("\\s+").length));
		return rtnMap;
	}

   
}
