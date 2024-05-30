package com.demo.socialsync.Interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IS3Service {
    String uploadFile(MultipartFile file, String name)throws IOException;
    String uploadFilePerfil(MultipartFile file, String idusuario)throws IOException;
    String deleteFile(String fileName) throws IOException;
}
