package com.niwodai.app.gateway.metrics;

import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.CsvReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.niwodai.app.gateway.remote.call.DubboRemoteCall;
import com.niwodai.app.gateway.route.dto.UniqueServiceDefined;

/**
 * @author tim.yin
 * @date 2015年12月2日 上午10:16:40
 * @version 1.0
 * @Description:这里做一个度量的模板方法，具体度量主体留给子类回调实现
 */

public class RestApiTimerManager<T> {

	private static Logger logger = LoggerFactory
			.getLogger(RestApiTimerManager.class);

	static final MetricRegistry metrics = new MetricRegistry();

	private final Map<String, Timer> metersMaps = new ConcurrentHashMap<String, Timer>();

	private DubboRemoteCall processCallback;

	static {
		// 打印到 csv 文件中 每15分钟打印一次
		String csvReportPath = "/opt/metrics";
		final CsvReporter reporter = CsvReporter.forRegistry(metrics)
				.formatFor(Locale.CHINESE).convertRatesTo(TimeUnit.SECONDS)
				.convertDurationsTo(TimeUnit.MILLISECONDS)
				.build(new File(csvReportPath));

		// TODO 自己设置度量间隔
		reporter.start(15, TimeUnit.MINUTES);
	}

	public RestApiTimerManager(DubboRemoteCall callback) {
		this.processCallback = callback;
	}

	private Timer fetchTimer(String interfaceConcatMethod) {
		logger.info("metrics 度量开始...........");

		if (!metersMaps.containsKey(interfaceConcatMethod)) {
			metersMaps.put(interfaceConcatMethod,
					metrics.timer(interfaceConcatMethod));
		}

		return metersMaps.get(interfaceConcatMethod);
	}

	static void stopReport(Timer.Context context) {
		logger.info("metrics 度量结束...........");
		context.stop();
	}

	/**
	 * 主体留给子类回调实现
	 */
	public Object process(UniqueServiceDefined uniqueServiceDefined,
			Map<String, Object> params) throws Exception {
		Object obj = new Object();

		// set reportconfig move to static method

		Timer timer = fetchTimer(uniqueServiceDefined.getServiceInterfaceName()
				.concat(uniqueServiceDefined.getServiceMethod()));

		final Timer.Context context = timer.time();

		try {
			obj = processCallback.call(uniqueServiceDefined, params);
		} catch (Exception e) {
			throw e;
		} finally {
			stopReport(context);
		}

		return obj;
	}
}
