package com.videohub.videohub.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.io.File;
import java.io.IOException;

@Component
public class MyResourceHttpRequestHandler extends ResourceHttpRequestHandler {
    public final static String ATTR_FILE = MyResourceHttpRequestHandler.class.getName() + ".file";

    @Override
    protected Resource getResource(HttpServletRequest request) throws IOException {

        final File file = (File) request.getAttribute(ATTR_FILE);
        return new FileSystemResource(file);
    }
}
