package com.sppart.admin.resolver;

import com.sppart.admin.exception.CommonErrorCode;
import com.sppart.admin.exception.SuperpositionAdminException;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class PageableVerificationArgumentResolver extends PageableHandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return super.supportsParameter(parameter);
    }

    @Override
    public Pageable resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                    NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String pageText = webRequest.getParameter("page");
        String sizeText = webRequest.getParameter("size");

        valid(pageText, sizeText);

        return super.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
    }

    private void valid(String pageText, String sizeText) {
        validEmpty(pageText);
        validEmpty(sizeText);
        validNumber(pageText);
        validNumber(sizeText);
        validPageRange(pageText);
        validSizeRange(sizeText);
    }

    private void validEmpty(String text) {
        if (text == null || text.isBlank()) {
            throw new SuperpositionAdminException(CommonErrorCode.INVALID_PARAMETER);
        }
    }

    private void validNumber(String text) {
        try {
            int value = Integer.parseInt(text);
            if (value < 1) {
                throw new SuperpositionAdminException(CommonErrorCode.INVALID_PARAMETER);
            }
        } catch (NumberFormatException e) {
            throw new SuperpositionAdminException(CommonErrorCode.INVALID_PARAMETER);
        }
    }

    private void validPageRange(String pageText) {
        int page = Integer.parseInt(pageText);
        if (page < 1) {
            throw new SuperpositionAdminException(CommonErrorCode.INVALID_PARAMETER);
        }
    }

    private void validSizeRange(String sizeText) {
        int size = Integer.parseInt(sizeText);
        if (size < 1) {
            throw new SuperpositionAdminException(CommonErrorCode.INVALID_PARAMETER);
        }
    }
}
