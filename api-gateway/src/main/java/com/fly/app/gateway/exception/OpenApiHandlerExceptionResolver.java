package com.fly.app.gateway.exception;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.model.JSONValue;

/**
 * @author tim.yin
 * @date 2015年12月10日 下午12:41:49
 * @version 1.0
 * @Description:TODO
 */

@Component
public class OpenApiHandlerExceptionResolver extends
		ExceptionHandlerExceptionResolver {

	private static Logger logger = LoggerFactory
			.getLogger(OpenApiHandlerExceptionResolver.class);

	private final List<ExceptionResponseBuilder> exceptionResponseBuilders = new ArrayList<ExceptionResponseBuilder>() {
		/**
		 * 自定义的异常都需要定义在这里。后面会根据异常类型进行检测 构建相应的异常返回
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{

			add(new BusinessExceptionResponseBuilder());
			add(new NoSuchRouteServiceExceptionResponseBuilder());
			add(new RequestValidatorExceptionResponseBuilder());

		}
	};
	private final ExceptionResponseBuilder defaultExceptionResponse = new UnknowExceptionReponseBuilder();

	private ExceptionResponseBuilder buildExceptionResponse(
			final Exception exception) {
		for (final ExceptionResponseBuilder e : exceptionResponseBuilders) {
			if (e.support(exception)) {
				return e;
			}
		}
		return defaultExceptionResponse;
	}

	@Override
	protected ModelAndView doResolveHandlerMethodException(
			HttpServletRequest request, HttpServletResponse response,
			HandlerMethod handlerMethod, Exception exception) {
		if (!(exception instanceof BusinessException)) {
			exception.printStackTrace();
		}
		ExceptionResponseBuilder exceptionResponseBuilder = buildExceptionResponse(exception);
		try {
			response.setContentType("text/html;charset=utf-8");
			JSONValue jsonValue = JSONMapper.toJSON(exceptionResponseBuilder
					.buildExceptionResponse(exception));

			response.getWriter().write(jsonValue.render(true));
		} catch (Exception e) {
			logger.info("输出返回错误:", e);
		} finally {
			if (!(exception instanceof BusinessException)) {
				logger.error(exception.getMessage());
			} else {
				// log.info(exception);
			}
		}

		return new ModelAndView();
	}
}
