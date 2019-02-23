package oa.SampleEvaluation;

import jcx.jform.hproc;
import java.io.*;
import java.util.*;
import jcx.util.*;
import oa.SampleEvaluation.dto.SampleEvaluation;
import jcx.html.*;
import jcx.db.*;

public class GGG extends hproc {
	public String action(String value) throws Throwable {
		SampleEvaluation s = new SampleEvaluation();
		s.setFormDataIntoDto(this);
		message(s.getFileSdsNote());
		return value;
	}

	public String getInformation() {
		return "---------------button5(button5).html_action()----------------";
	}
}
