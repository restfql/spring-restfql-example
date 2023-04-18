package com.restfql.spring.example;

import graphql.language.Document;
import graphql.language.Field;
import graphql.language.OperationDefinition;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class RestFQLAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    private Map<String, Object> GetSubset(Object body, List<Field> fql){
        var response = new HashMap<String, Object>();
        fql.stream().forEach( field -> {
            System.out.println(field.getName());
            System.out.println(field.getSelectionSet());
            try {
                response.put(
                        field.getName(),
                        field.getSelectionSet() != null
                                ? GetSubset(
                                        body.getClass().getField(field.getName()).get(body),
                                        field.getSelectionSet().getSelections().stream().map(selection -> (Field) selection).toList())
                                : body.getClass().getField(field.getName()).get(body));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        });
        return response;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                               MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                               ServerHttpRequest request, ServerHttpResponse response) {
        var fqlQueryParam = UriComponentsBuilder.fromHttpRequest(request).build().getQueryParams().get("fql");
        if(fqlQueryParam == null)
            return body;

        Document fql;
        try {
            fql = graphql.parser.Parser.parse(URLDecoder.decode(fqlQueryParam.get(0), "utf-8"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        var fields = ((OperationDefinition)fql.getDefinitions().get(0)).getSelectionSet()
                .getSelections().stream()
                .map(selection -> (Field) selection).toList();

        return GetSubset(body, fields);
    }

}
