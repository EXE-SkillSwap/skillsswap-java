package com.skillswap.server.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface CloudinaryService {

    Map upload(MultipartFile file) throws IOException;
    Map delete(String publicId) throws IOException;
}
