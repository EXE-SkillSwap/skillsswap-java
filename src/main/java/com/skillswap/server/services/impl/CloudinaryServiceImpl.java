package com.skillswap.server.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.skillswap.server.services.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Override
    public Map upload(MultipartFile file) throws IOException {
        Map<String, Object> options = ObjectUtils.asMap(
                "quality", "100",                // Giữ nguyên chất lượng
                "fetch_format", "auto",          // Không chuyển định dạng nếu không cần
                "preserve_original", true        // Giữ ảnh gốc (tùy gói Cloudinary)
        );
        return cloudinary.uploader().upload(file.getBytes(), options);
    }

    @Override
    public Map delete(String publicId) throws IOException {
        return cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }
}
