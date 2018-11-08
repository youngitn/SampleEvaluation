package oa.SampleEvaluation.common.global;

import java.io.FileWriter;
import java.io.IOException;

public class DebugUtil {

	public static void out(String name, String in) throws IOException {
		FileWriter fw = new FileWriter(name);
		fw.write(in);
		fw.close();
	}
}
