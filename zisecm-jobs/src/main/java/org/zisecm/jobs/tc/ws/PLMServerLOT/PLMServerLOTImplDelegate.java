package org.zisecm.jobs.tc.ws.PLMServerLOT;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.axis.MessageContext;
import org.apache.log4j.Logger;
import org.zisecm.jobs.tc.utils.DataManagement;
import org.zisecm.jobs.tc.utils.Query;
import org.zisecm.jobs.tc.utils.Workflow;
import org.zisecm.jobs.tc.clientx.Session;
import com.teamcenter.rac.aif.common.AIFTreeCellRenderer;
import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.services.strong.core.DataManagementService;
import com.teamcenter.services.strong.core._2006_03.DataManagement.CreateItemsOutput;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.client.model.ServiceData;
import com.teamcenter.soa.client.model.Type;
import com.teamcenter.soa.client.model.strong.Folder;
import com.teamcenter.soa.client.model.strong.TC_Project;
import com.teamcenter.soa.client.model.strong.User;
import com.teamcenter.soa.client.model.strong.WorkspaceObject;

@javax.jws.WebService(targetNamespace = "http://PLMServerLOT.ws.cnpe.com/", serviceName = "PLMServerLOTImplService", portName = "PLMServerLOTImplPort")
public class PLMServerLOTImplDelegate {

	org.zisecm.jobs.tc.ws.PLMServerLOT.PLMServerLOTImpl pLMServerLOTImpl = new org.zisecm.jobs.tc.ws.PLMServerLOT.PLMServerLOTImpl();

	public boolean loginTC() {
		return pLMServerLOTImpl.loginTC();
	}

	public ReturnVal setFileData(FileDataMessageBody fileDataRequest) {
		return pLMServerLOTImpl.setFileData(fileDataRequest);
	}

	public ReturnVal setCRData(CRDataMessageBody crDataRequest) {
		return pLMServerLOTImpl.setCRData(crDataRequest);
	}

	public void getICMACP1000Data(List<ICMACPMessageBody> iCMACPMessageBody) {
		pLMServerLOTImpl.getICMACP1000Data(iCMACPMessageBody);
	}

	public ReturnVal setFaxData(FaxDataMessageBody faxDataRequest) {
		return pLMServerLOTImpl.setFaxData(faxDataRequest);
	}

	public ReturnVal setTAData(TADataMessageBody taDataRequest) {
		return pLMServerLOTImpl.setTAData(taDataRequest);
	}

	public ReturnVal setDesignReviewF(
			DesignReviewFDataMessageBody designReviewFDataRequest) {
		return pLMServerLOTImpl.setDesignReviewF(designReviewFDataRequest);
	}

	public ReturnVal setIITFData(IITFDataMessageBody iitfDataRequest) {
		return pLMServerLOTImpl.setIITFData(iitfDataRequest);
	}

	public void main(String[] args) throws Exception {
		pLMServerLOTImpl.main(args);
	}

	public ReturnVal setNCRData(NCRDataMessageBody setNCRDataRequest) {
		return pLMServerLOTImpl.setNCRData(setNCRDataRequest);
	}

	public ReturnVal setIICSData(IICSDataMessageBody iicsDataRequest) {
		return pLMServerLOTImpl.setIICSData(iicsDataRequest);
	}

	public ReturnVal setAp1000IICSData(Ap1000IICSDataMessageBody iicsDataRequest) {
		return pLMServerLOTImpl.setAp1000IICSData(iicsDataRequest);
	}

	public ReturnVal setAp1000IITFData(Ap1000IITFDataMessageBody iitfDataRequest) {
		return pLMServerLOTImpl.setAp1000IITFData(iitfDataRequest);
	}

	public ReturnVal setFuData(FUDataMessageBody fuDataRequest) {
		return pLMServerLOTImpl.setFuData(fuDataRequest);
	}

	public ReturnVal setDesignData(DesignDataMessageBody designDataMessage) {
		return pLMServerLOTImpl.setDesignData(designDataMessage);
	}

	public ReturnVal setResultDare(ResultMessage resultMessage) {
		return pLMServerLOTImpl.setResultDare(resultMessage);
	}

}