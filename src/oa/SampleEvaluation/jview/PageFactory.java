package oa.SampleEvaluation.jview;

import com.ysp.service.BaseService;

import oa.SampleEvaluation.enums.PageInitType;

public class PageFactory {
	
	private PageFactory() {
		throw new IllegalStateException("PageFactory class");
	}

	public static Page getPage(BaseService service, String actionObjName) {
		Page page = null;
//		// ���s�ʧ@�B�z�i�J�I
//		switch (PageInitType.valueOf(actionObjName)) {
//		case QUERY_PAGE_INIT:// �i�J�d�ߵe��
//			page = new QueryPage(service);
//			break;
//		case DETAIL_PAGE_INIT:// �i�J���ӵe��
//			page = new DetailPage(service);
//			break;
//		default:
//			break;
//		}
		return page;

	}
}
