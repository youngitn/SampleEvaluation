package oa.SampleEvaluation.jview;

import com.ysp.service.BaseService;

import oa.SampleEvaluation.enums.PageInitType;

public class PageFactory {
	
	private PageFactory() {
		throw new IllegalStateException("PageFactory class");
	}

	public static Page getPage(BaseService service, String actionObjName) {
		Page page = null;
//		// 按鈕動作處理進入點
//		switch (PageInitType.valueOf(actionObjName)) {
//		case QUERY_PAGE_INIT:// 進入查詢畫面
//			page = new QueryPage(service);
//			break;
//		case DETAIL_PAGE_INIT:// 進入明細畫面
//			page = new DetailPage(service);
//			break;
//		default:
//			break;
//		}
		return page;

	}
}
