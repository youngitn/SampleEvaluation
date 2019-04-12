package oa.global;

/**
 * The Class UIHidderString.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class UIHidderString {
	
	/**
	 * <b>����Dmaker���h�s�W���s</b><br>
	 * �ǤJ�G�L<br>
	 * �^�ǡG�L<br>
	 * �����G����Dmaker���h�s�W���s<br>
	 * �}�o�H�G51884.
	 *
	 * @return  [String]
	 */
	public static  String hideDmakerAddButton() {
		return "try{document.getElementById('em_add_button').style.display = 'none';}catch(err){}";
	}

	/**
	 * <b>����Dmaker�W�����</b><br>
	 * �ǤJ�G�L<br>
	 * �^�ǡG�L<br>
	 * �����G����Dmaker�W�����<br>
	 * �}�o�H�G51884.
	 *
	 * @return  [String]
	 */
	public static  String hideDmakerFlowPanel() {
		return "try{document.getElementById('flow_panel').style.display = 'none';}catch(err){}";
	}

	/**
	 * <b>���øԲӸԲӦC��\��</b><br>
	 * �ǤJ�G�L<br>
	 * �^�ǡG�L<br>
	 * �����G���øԲӸԲӦC��\��<br>
	 * �}�o�H�G51884.
	 *
	 * @return  [String]
	 */
	public static  String hideFlowButtonDetail() {
		return "try{document.getElementById('flow_button_detail').style.display = 'none';}catch(e){}";
	}

	/**
	 * <b>���øԲӦC��ñ�ַN���Pñ�֫��s</b><br>
	 * �ǤJ�G�L<br>
	 * �^�ǡG�L<br>
	 * �����G���øԲӦC��ñ�ַN���Pñ�֫��s�A����k�u�����ÿ�10�����s�A�W�L�N�����A�ק�<br>
	 * �}�o�H�G51884.
	 *
	 * @return  [String]
	 */
	public static  String hideBatchApproveFunction() {
		StringBuffer js = new StringBuffer();
		js.append("try{document.getElementById('approve.note.caption').style.display='none';}catch(e){}");
		js.append("try{document.getElementById('approve.note').style.display='none';}catch(e){}");
		js.append("try{document.getElementById('approve_button_0').style.display='none';}catch(e){}");
		js.append("try{document.getElementById('approve_button_1').style.display='none';}catch(e){}");
		js.append("try{document.getElementById('approve_button_2').style.display='none';}catch(e){}");
		js.append("try{document.getElementById('approve_button_3').style.display='none';}catch(e){}");
		js.append("try{document.getElementById('approve_button_4').style.display='none';}catch(e){}");
		js.append("try{document.getElementById('approve_button_5').style.display='none';}catch(e){}");
		js.append("try{document.getElementById('approve_button_6').style.display='none';}catch(e){}");
		js.append("try{document.getElementById('approve_button_7').style.display='none';}catch(e){}");
		js.append("try{document.getElementById('approve_button_8').style.display='none';}catch(e){}");
		js.append("try{document.getElementById('approve_button_9').style.display='none';}catch(e){}");

		return js.toString();
	}
}
