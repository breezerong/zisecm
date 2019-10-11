package com.ecm.icore.service;

import java.util.List;
import java.util.Map;

public interface IMongoDBService {
	public List<Map<String,Object>> searchDocumentData(String collName,String name,String typeName);
}
