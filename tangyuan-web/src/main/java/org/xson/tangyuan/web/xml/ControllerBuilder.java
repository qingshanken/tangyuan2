package org.xson.tangyuan.web.xml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.xson.tangyuan.web.xml.InterceptVo.InterceptType;

public class ControllerBuilder {

	protected BuilderContext	bc				= null;

	// 引用标志
	protected String			refMark			= "@";
	protected String			urlSeparator	= "/";
	protected String			leftBrackets	= "{";
	protected String			rightBrackets	= "}";

	protected String serviceNameToUrl(String serviceName) {
		if (!serviceName.startsWith(urlSeparator)) {
			serviceName = urlSeparator + serviceName;
		}
		return serviceName.replaceAll("\\.", "/");
	}

	protected List<MethodObject> getInterceptList(String url, List<InterceptVo> list, InterceptType type) {
		List<MethodObject> result = null;
		List<InterceptVo> matchList = new ArrayList<InterceptVo>();
		// 全局的
		List<InterceptVo> globalList = null;

		if (InterceptType.ASSEMBLY == type) {
			globalList = this.bc.getAssemblyList();
		} else if (InterceptType.BEFORE == type) {
			globalList = this.bc.getBeforeList();
		} else {
			globalList = this.bc.getAfterList();
		}

		for (InterceptVo baVo : globalList) {
			if (baVo.match(url)) {
				matchList.add(baVo);
			}
		}
		if (list.size() > 0) {
			matchList.addAll(list);
		}

		if (matchList.size() > 0) {
			Collections.sort(matchList);// sort
			result = new ArrayList<MethodObject>();
			for (InterceptVo baVo : matchList) {
				result.add(baVo.getMo());
			}
		}

		return result;
	}

}
