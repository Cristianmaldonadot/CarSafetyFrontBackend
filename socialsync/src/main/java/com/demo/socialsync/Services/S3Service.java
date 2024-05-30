package com.demo.socialsync.Services;

import com.demo.socialsync.Interfaces.IS3Service;
import com.demo.socialsync.Models.Usuario;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class S3Service implements IS3Service {

    private final S3Client s3Client;

    @Autowired
    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String uploadFile(MultipartFile file, String name) throws IOException {
        try {

            // Leer la imagen original
            InputStream inputStream = new ByteArrayInputStream(file.getBytes());

            // Redimensionar la imagen proporcionalmente
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Thumbnails.of(inputStream)
                    .width(1300) // Ancho máximo deseado
                    .keepAspectRatio(true) // Mantener la proporción
                    .outputQuality(1.0) // Calidad de salida (1.0 = sin pérdida)
                    .toOutputStream(outputStream);

            // Cargar la imagen redimensionada en S3
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket("imagenes-tienda")
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .key(name)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(outputStream.toByteArray()));

            return "Archivo Subido";
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    @Override
    public String uploadFilePerfil(MultipartFile file, String nombreArchivo) throws IOException {
        try {

            // Leer la imagen original
            InputStream inputStream = new ByteArrayInputStream(file.getBytes());

            // Redimensionar la imagen proporcionalmente
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Thumbnails.of(inputStream)
                    .width(200) // Ancho máximo deseado
                    .keepAspectRatio(true) // Mantener la proporción
                    .outputQuality(1.0) // Calidad de salida (1.0 = sin pérdida)
                    .toOutputStream(outputStream);

            // Cargar la imagen redimensionada en S3
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket("imagenes-tienda")
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .key(nombreArchivo)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(outputStream.toByteArray()));

            return "Archivo Subido";
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    @Override
    public String deleteFile(String fileName) throws IOException {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket("imagenes-tienda")
                    .key(fileName)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);
            System.out.println(fileName);
            return "Archivo Borrado correctamente";
        } catch (S3Exception e) {
            throw new IOException(e.getMessage());
        }
    }

}
