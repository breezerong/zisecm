package com.ecm.icore.service;

import java.util.List;

import com.ecm.core.entity.EcmMenu;
import com.ecm.core.exception.AccessDeniedException;

/**
 * 
 * @author Haihong Rong
 * @date 2019年10月17日 下午1:46:10
 */
public interface IMenuService {

	List<EcmMenu> getMenuWithChild(String token);

	EcmMenu getObjectByName(String token, String name);

	EcmMenu getMyMenu(String token, String menuName, String lang) throws AccessDeniedException;


}
