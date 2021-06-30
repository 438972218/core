package com.xdcplus.swagger;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import springfox.documentation.RequestHandler;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.swagger.web.ApiKeyVehicle;

import java.util.List;
import java.util.function.Predicate;

/**
 * Swagger工具类
 *
 * @author 贾荣
 * @date 2021/05/13 17:22
 */
public class SwaggerUtil {

	/**
	 * 获取包集合
	 *
	 * @param basePackages 多个包名集合
	 */
	public static Predicate<RequestHandler> basePackages(final List<String> basePackages) {
		return input -> declaringClass(input).transform(handlerPackage(basePackages)).or(true);
	}

	private static Function<Class<?>, Boolean> handlerPackage(final List<String> basePackages) {
		return input -> {
			// 循环判断匹配
			for (String strPackage : basePackages) {
				boolean isMatch = input.getPackage().getName().startsWith(strPackage);
				if (isMatch) {
					return true;
				}
			}
			return false;
		};
	}

	private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
		return Optional.fromNullable(input.declaringClass());
	}

	public static ApiKey apiKey(String name, String keyName, String passAs) {
		return new ApiKey(name, keyName, passAs);
	}

	public static ApiKey apiKey(String name, String keyName, String passAs, List<VendorExtension> vendorExtensions) {
		return new ApiKey(name, keyName, passAs, vendorExtensions);
	}

	public static ApiKey apiKey() {
		return new ApiKey("Authorization", "Authorization", ApiKeyVehicle.HEADER.getValue());
	}


}
