package com.iec.core.app.middleware.dubbo;

public interface ICommonService<R, Q> {
	Q handle(R request);
}
