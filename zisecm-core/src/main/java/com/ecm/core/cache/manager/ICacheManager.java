package com.ecm.core.cache.manager;
/**
 * 
 * @ClassName  ICacheManager   
 * @Description TODO(启动时加载缓存接口)   
 * @author yaozhigang
 * @date 2018年6月26日 上午11:21:32  
 *
 */
public interface ICacheManager<V> {
	/**
	 * 
	 * 初始化缓存
	 * 
	 */
	public void initAllCaches();
	
	/**
	 * 
	 * 刷新缓存
	 * 
	 */
	public V refreshCache(String key);
	
}
