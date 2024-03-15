package com.skcc.egovcore.core.paging.resolver;

import com.skcc.egovcore.core.paging.annotation.PageDefault;
import com.skcc.egovcore.core.paging.dto.PageDto;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Deprecated
// page 입력받는 데이터
public class PagingArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(PageDto.class) &&
                parameter.hasParameterAnnotation(PageDefault.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        PageDefault parameterAnnotation = parameter.getParameterAnnotation(PageDefault.class);
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        Enumeration<?> enumeration = request.getParameterNames();

//        MultiReadHttpServletRequest multiReadRequest = new MultiReadHttpServletRequest((HttpServletRequest) request);
//
//        ServletInputStream inputStream = multiReadRequest.getInputStream();
//        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
//        if (!Objects.isNull(parameterAnnotation)) {
//            int value = parameterAnnotation.value();
//        }


        return null;
//        return new PageDto(10);
    }
}
