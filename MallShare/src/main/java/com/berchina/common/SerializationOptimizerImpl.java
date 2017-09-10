
package com.berchina.common; 

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.berchina.vo.HashMapExt;
/** 

 * @author 作者姓名  E-mail: email地址 

 * @version 创建时间：2017年5月16日 上午11:00:15 

 * 类说明 implements SerializationOptimizer

 */

public class SerializationOptimizerImpl  {    
	public Collection<Class> getSerializableClasses() {       
	   List<Class> classes = new LinkedList<Class>();
	   classes.add(HashMapExt.class);
       return classes;
}
}