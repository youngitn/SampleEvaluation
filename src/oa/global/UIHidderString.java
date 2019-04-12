package oa.global;

/**
 * The Class UIHidderString.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class UIHidderString {
	
	/**
	 * <b>隱藏Dmaker底層新增按鈕</b><br>
	 * 傳入：無<br>
	 * 回傳：無<br>
	 * 說明：隱藏Dmaker底層新增按鈕<br>
	 * 開發人：51884.
	 *
	 * @return  [String]
	 */
	public static  String hideDmakerAddButton() {
		return "try{document.getElementById('em_add_button').style.display = 'none';}catch(err){}";
	}

	/**
	 * <b>隱藏Dmaker上方標籤</b><br>
	 * 傳入：無<br>
	 * 回傳：無<br>
	 * 說明：隱藏Dmaker上方標籤<br>
	 * 開發人：51884.
	 *
	 * @return  [String]
	 */
	public static  String hideDmakerFlowPanel() {
		return "try{document.getElementById('flow_panel').style.display = 'none';}catch(err){}";
	}

	/**
	 * <b>隱藏詳細詳細列表功能</b><br>
	 * 傳入：無<br>
	 * 回傳：無<br>
	 * 說明：隱藏詳細詳細列表功能<br>
	 * 開發人：51884.
	 *
	 * @return  [String]
	 */
	public static  String hideFlowButtonDetail() {
		return "try{document.getElementById('flow_button_detail').style.display = 'none';}catch(e){}";
	}

	/**
	 * <b>隱藏詳細列表的簽核意見與簽核按鈕</b><br>
	 * 傳入：無<br>
	 * 回傳：無<br>
	 * 說明：隱藏詳細列表的簽核意見與簽核按鈕，本方法只能隱藏錢10顆按鈕，超過就必須再修改<br>
	 * 開發人：51884.
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
