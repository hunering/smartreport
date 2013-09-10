package nc.ui.pub.print;

import nc.bs.framework.common.NCLocator;
import nc.itf.uap.cil.ICilService;
import nc.ui.pub.ClientEnvironment;

public class PrintCil {
	public static boolean isTraining(){
	    if (ClientEnvironment.getServerTime().getYear() == 2013) 
	    	return false;
	    return ((ICilService)NCLocator.getInstance().lookup(ICilService.class.getName())).isNCDEMO();
	}
}
