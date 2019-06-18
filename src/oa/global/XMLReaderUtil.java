package oa.global;

import java.io.File;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * �غc��:��JXML���|���o�ɮצ� �]�m�`�I�W��,���o�`�I�C�� ���ovector
 * 
 * @author u52116
 *
 */
public class XMLReaderUtil {

	String nodeName;
	String path;
	NodeList nl;
	Document doc;

	public XMLReaderUtil(String path) throws Throwable {
		this.path = path;
		DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dFactory.newDocumentBuilder();
		this.doc = builder.parse(new File(path));
		// this.nl = doc.getElementsByTagName(nodeName);
	}

	public NodeList getNodeList() {

		return nl;
	}

	public int getNodeListNum() {
		
		return nl.getLength();
	}

	public void setNodeName(String nodeName) {
		this.nl = doc.getElementsByTagName(nodeName);
	}

	public Vector<String> getVector(String nodeAttributeName) throws Throwable {
		// �i�۩wHTML�����U��쪺�w�]�ȻP���s���ʧ@
		// �ǤJ�� value
		Vector<String> v1 = new Vector<String>();

		for (int i = 0; i < nl.getLength(); i++) {
			v1.add(nl.item(i).getAttributes().getNamedItem(nodeAttributeName).getNodeValue());
		}
		return v1;
	}
}
