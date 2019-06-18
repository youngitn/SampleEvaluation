package oa.SampleEvaluation.test;

import org.w3c.dom.NodeList;

import jcx.jform.hproc;
import oa.global.XMLReaderUtil;

public class Filetest extends hproc {

	@Override
	public String action(String arg0) throws Throwable {
		XMLReaderUtil xml = new XMLReaderUtil("babylon\\ysphr\\oa\\SampleEvaluation\\config\\test.xml");
		//�ؼФ���
		xml.setNodeName("item");
		NodeList nl = xml.getNodeList();
		int num = xml.getNodeListNum();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < num; i++) {
			//���o����OOO  EX:<XX>OOO</XX>
			sb.append(nl.item(i).getTextContent());
			//���o���� �ݩ�node   name='n'  EX: <XX name='n'>OOO</XX>
			sb.append(nl.item(i).getAttributes().getNamedItem("name"));
			//���o���� �ݩ�node����name�ݩʭ� n  EX: <XX name='n'>OOO</XX>
			sb.append(nl.item(i).getAttributes().getNamedItem("name").getNodeValue());
		}

		message(num + "" + sb.toString());
		return null;
	}

}
