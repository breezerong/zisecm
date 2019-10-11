package com.ecm.common.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
/**
 * 脚本工具
 * @author Haihong Rong
 *
 */
public class ScriptUtils {
	/**
	 * 执行条件表达式
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public static boolean executeBooleanCondition(String condition) throws Exception {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		return (boolean)engine.eval(condition);
	}
}
