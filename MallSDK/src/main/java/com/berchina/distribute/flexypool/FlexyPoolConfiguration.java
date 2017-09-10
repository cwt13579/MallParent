package com.berchina.distribute.flexypool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.vladmihalcea.flexypool.FlexyPoolDataSource;
import com.vladmihalcea.flexypool.adaptor.C3P0PoolAdapter;
import com.vladmihalcea.flexypool.config.Configuration;
import com.vladmihalcea.flexypool.connection.JdkConnectionProxyFactory;
import com.vladmihalcea.flexypool.metric.codahale.CodahaleMetrics;
import com.vladmihalcea.flexypool.strategy.IncrementPoolOnTimeoutConnectionAcquiringStrategy;
import com.vladmihalcea.flexypool.strategy.RetryConnectionAcquiringStrategy;

//@org.springframework.context.annotation.Configuration
public class FlexyPoolConfiguration {
	@Autowired
	private ComboPooledDataSource poolingDataSource;

	//@Value("${flexy.pool.uniqueId}")
	private String uniqueId;

	@Bean
	public Configuration<ComboPooledDataSource> configuration() {
		return new Configuration.Builder<ComboPooledDataSource>(uniqueId,
				poolingDataSource, C3P0PoolAdapter.FACTORY)
				.setMetricsFactory(CodahaleMetrics.FACTORY)
				.setConnectionProxyFactory(JdkConnectionProxyFactory.INSTANCE)
				.setJmxEnabled(true).setMetricLogReporterMillis(5).build();
	}

	@Bean(initMethod = "start", destroyMethod = "stop")
	public FlexyPoolDataSource dataSource() {
		Configuration<ComboPooledDataSource> configuration = configuration();
		return new FlexyPoolDataSource<ComboPooledDataSource>(
				configuration,
				new IncrementPoolOnTimeoutConnectionAcquiringStrategy.Factory(5),
				new RetryConnectionAcquiringStrategy.Factory(2));
	}
}
