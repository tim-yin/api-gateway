package com.fly.app.gateway.metrics;

import java.io.File;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Configuration;

import com.codahale.metrics.CsvReporter;
import com.codahale.metrics.MetricRegistry;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;

/**
 * @author tim.yin
 * @date 2015年11月30日 下午1:37:38
 * @version 1.0
 * @Description:目前使用不到 对接配置:spring-metrics.xml
 */
@Configuration
@EnableMetrics
@Deprecated
public class MetricsReportConfig extends MetricsConfigurerAdapter {

	@Override
	public void configureReporters(MetricRegistry metricRegistry) {

		String csvReportPath = "/opt/metrics";
		final CsvReporter reporter = CsvReporter.forRegistry(metricRegistry)
				.formatFor(Locale.CHINESE).convertRatesTo(TimeUnit.SECONDS)
				.convertDurationsTo(TimeUnit.MILLISECONDS)
				.build(new File(csvReportPath));

		reporter.start(15, TimeUnit.MINUTES);

	}
}
