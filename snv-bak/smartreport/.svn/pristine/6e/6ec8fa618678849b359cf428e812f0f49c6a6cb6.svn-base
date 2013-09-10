package nc.ui.fl.pub;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.fi.pub.FIBException;
import nc.itf.uap.sfapp.IBillcodeRuleService;
import nc.vo.pub.BusinessException;

public class BillNoUtil {

	private static IBillcodeRuleService iBillcodeRuleService;
	
	public static String getBillCode(String billtype, String corpPk) throws BusinessException{
		String rt = null;
		try {
			rt = getBillcodeGenerator().getBillCode_RequiresNew(billtype, corpPk, null, null);
		} catch (Exception e) {
			Logger.debug(e);
			e.printStackTrace();
			throw new FIBException(e);
		}
		return rt;
	}
	
	private static IBillcodeRuleService getBillcodeGenerator() {
		if (iBillcodeRuleService == null) {
			iBillcodeRuleService = (IBillcodeRuleService) NCLocator.getInstance().lookup(IBillcodeRuleService.class.getName());
		}
		return iBillcodeRuleService;
	}
	

}
