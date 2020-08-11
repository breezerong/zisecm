package com.ecm.core.sync;

import java.io.IOException;

public interface ISyncPublicNet {

	boolean importData();

	boolean exportData(String type) throws IOException, Exception;
}
