package com.iec.core.app.middleware.dubbo.scan;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import com.iec.core.app.middleware.RequestMapping;
import com.iec.core.app.middleware.dubbo.IDubboService;
import com.iec.core.app.middleware.dubbo.IMethodScan;

/**
 * @author apple
 *  
 */
public class ControllerMethodScan implements IMethodScan{
	
	
	private static final Logger log = LoggerFactory.getLogger(ControllerMethodScan.class);
	private ModuleClasses mp;
	
	public ControllerMethodScan(){}

	public ControllerMethodScan(ModuleClasses mp) {
		super();
		this.mp = mp;
	}
	
	

	public ModuleClasses getMp() {
		return mp;
	}



	public void setMp(ModuleClasses mp) {
		this.mp = mp;
	}



	@Override
	public Map<String, Method> scan(ApplicationContext applicationContext,Class<? extends IDubboService>[] dubboServiceClasses)
			throws IOException, ClassNotFoundException {
		return loadModuleClasses(applicationContext);
	}

	public final Map<String, Method> loadModuleClasses(ApplicationContext context)
			throws IOException, ClassNotFoundException {
		Set<String> classNameSet = new HashSet<String>();
		String[] externClassNames = mp.getExternClassNames();
		if (externClassNames != null && externClassNames.length > 0)
			classNameSet.addAll(Arrays.asList(externClassNames));

		final String patterPath = "classpath*:%s/*/*Controller.class";
		String[] packNames = mp.getPackNames();
		if (packNames != null && packNames.length > 0) {
			for (String packName : packNames) {
				Resource[] resources = context.getResources(String.format(
						patterPath, mp.getPackSubPath(packName)));

				for (int i = 0; i < resources.length; i++) {
					String path = resources[i].getURI().toString()
							.replaceAll("[/\\\\]", "\\.");
					String classPath = path.substring(path.indexOf(packName),
							path.lastIndexOf(".class"));
					classNameSet.add(classPath);
				}
			}
		}

		if (classNameSet.size() <= 0) {
			log.error("未配置模块类");
			throw new RuntimeException("未配置模块类");
		}
		Map<String, Method> methodMap = new ConcurrentHashMap<String, Method>();
		for (String className : classNameSet) {
			Class clazz = Class.forName(className);
			Method[] method = clazz.getDeclaredMethods();
			for (int i = 0; i < method.length; i++) {
				RequestMapping service = method[i].getAnnotation(RequestMapping.class);

				if (null == service)
					continue;
				String serviceName = service.id();

				if (methodMap.containsKey(serviceName)) {
					log.warn("[BaseMapping error]: serviceName conflict,"
							+ serviceName);
					throw new IOException(
							"[BaseMapping error]: serviceName conflict,"
									+ serviceName);
				}

				log.info(serviceName + ">>>>>" + clazz.getName());

				methodMap.put(serviceName, method[i]);
			}
		}
		return methodMap;
	}
}
