package org.zisecm.jobs.business;

import java.io.IOException;

public interface ISyncPublicNet {


	boolean exportData(String type) throws IOException, Exception;

	boolean importData(String actionName);
}
