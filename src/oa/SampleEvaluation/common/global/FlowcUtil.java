package oa.SampleEvaluation.common.global;

import java.lang.reflect.Constructor;
import java.sql.SQLException;

import com.ysp.util.DateTimeUtil;

import jcx.db.talk;
import oa.SampleEvaluation.daointerface.IFlowcDao;
import oa.SampleEvaluation.daointerface.IFlowcHisDao;
import oa.SampleEvaluation.daointerface.ITableDao;
import oa.SampleEvaluation.dto.FlowcDto;
import oa.SampleEvaluation.dto.FlowcHisDto;
import oa.SampleEvaluation.dto.SampleEvaluationSubBaseDto;

public class FlowcUtil {
	public static FlowcDto getFlowcDtoWithData(String gateName, SampleEvaluationSubBaseDto s) {
		FlowcDto Dto = new FlowcDto();
		Dto.setId(s.getOwnPno());
		String time = DateTimeUtil.getApproveAddSeconds(0);
		Dto.setF_INP_ID(s.getApplicant());
		Dto.setF_INP_STAT(gateName);
		Dto.setF_INP_TIME(time);
		return Dto;
	}

	public static FlowcHisDto getFlowcHisDtoWithData(String gateName, SampleEvaluationSubBaseDto s) {
		FlowcHisDto Dto = new FlowcHisDto();
		Dto.setId(s.getOwnPno());
		String time = DateTimeUtil.getApproveAddSeconds(0);
		Dto.setF_INP_ID(s.getApplicant());
		Dto.setF_INP_STAT(gateName);
		Dto.setF_INP_TIME(time);
		return Dto;
	}

	public static void goSubFlow(String type, SampleEvaluationSubBaseDto s, talk t, String gateName)
			throws ClassNotFoundException, SQLException, Exception {

		try {
			Class<?> mainDao = Class.forName("oa.SampleEvaluation" + type + ".dao.SampleEvaluation" + type + "DaoImpl");
			Constructor<?> DaoCon = mainDao.getConstructor(talk.class);
			ITableDao<SampleEvaluationSubBaseDto> Dao = (ITableDao<SampleEvaluationSubBaseDto>) DaoCon.newInstance(t);

			if (Dao.findById(s.getOwnPno()) != null) {
				Dao.update(s);
			} else {
				// insert一筆子流程主檔
				Dao.add(s);

				FlowcDto Dto = FlowcUtil.getFlowcDtoWithData(gateName, s);
				IFlowcDao<FlowcDto> secfDao = (IFlowcDao<FlowcDto>) Class
						.forName("oa.SampleEvaluation" + type + ".dao.SampleEvaluation" + type + "FlowcDaoImpl")
						.newInstance();
				secfDao.create(t.getConnectionFromPool(), Dto);
				FlowcHisDto his = FlowcUtil.getFlowcHisDtoWithData(gateName, s);

				IFlowcHisDao<FlowcHisDto> secfhDao = (IFlowcHisDao<FlowcHisDto>) Class
						.forName("oa.SampleEvaluation" + type + ".dao.SampleEvaluation" + type + "FlowcHisDaoImpl")
						.newInstance();

				secfhDao.create(t.getConnectionFromPool(), his);
			}
		} catch (ClassNotFoundException e) {
			System.out.println("找不到類別");
		}

	}
}
