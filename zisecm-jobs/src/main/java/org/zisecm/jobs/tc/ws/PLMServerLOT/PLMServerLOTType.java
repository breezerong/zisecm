package org.zisecm.jobs.tc.ws.PLMServerLOT;

import java.util.List;


public interface PLMServerLOTType {
	
	public ReturnVal setFileData(FileDataMessageBody fileDataRequest) ;
	
	public ReturnVal setFaxData(FaxDataMessageBody faxDataRequest);
	
	public ReturnVal setCRData(CRDataMessageBody crDataRequest);
	
	public void getICMACP1000Data(List<ICMACPMessageBody> iCMACPMessageBody);
	
	public ReturnVal setTAData(TADataMessageBody taDataRequest);
	
	public ReturnVal setDesignReviewF(DesignReviewFDataMessageBody designReviewFDataRequest);
	
	
	public ReturnVal setIITFData(IITFDataMessageBody iitfDataRequest);
	
	
	public ReturnVal setNCRData(NCRDataMessageBody setNCRDataRequest);
	
	public ReturnVal setIICSData(IICSDataMessageBody iicsDataRequest);
	
	
	public ReturnVal setAp1000IICSData(Ap1000IICSDataMessageBody iicsDataRequest);
	public ReturnVal setAp1000IITFData(Ap1000IITFDataMessageBody iitfDataRequest);
	
	public ReturnVal setDesignData(DesignDataMessageBody designDataMessage);
	
	public ReturnVal setResultDare(ResultMessage resultMessage);
	
	public ReturnVal setFuData(FUDataMessageBody fuDataRequest);
	
	
}
