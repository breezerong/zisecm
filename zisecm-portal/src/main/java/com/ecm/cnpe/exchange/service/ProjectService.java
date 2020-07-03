package com.ecm.cnpe.exchange.service;

import java.util.List;

import com.ecm.cnpe.exchange.entity.ProjectEntity;

public interface ProjectService {
	List<ProjectEntity> getMyProject(String token);
	String getMyProjectCondition(String prefix, String toke);
}