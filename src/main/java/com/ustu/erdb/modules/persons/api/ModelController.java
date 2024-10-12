package com.ustu.erdb.modules.persons.api;

import com.ustu.erdb.modules.persons.dto.UserRequestDto;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ModelController {

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/imgs/";;
    private static final String SCHEMA_NAME = "SCHEMA";

    @PostMapping("/create-models")
    public ResponseEntity<Object> createModel(@RequestParam("label") String label,
                                             @RequestParam("topic") String topic,
                                             @RequestParam("description") String description,
                                             @RequestParam("schema") MultipartFile file) {
        Map<String, String> response = new HashMap<>();

        if (file.isEmpty()) {
            response.put("message", "Выберите файл для загрузки!");
            return new ResponseEntity<>(Map.of("error", "Ошибка загрузки файла"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            File uploadDir = new File(UPLOAD_DIR + label);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs(); // Создает директории, если они не существуют
            }
            // Сохранение файла
            File dest = new File(UPLOAD_DIR + String.format("%s/%s", label, file.getOriginalFilename()));
            file.transferTo(dest);
            response.put("message", "Файл загружен успешно: " + label);
            response.put("imageLabel", String.format("/imgs/%s/%s", label, file.getOriginalFilename())); // URL для доступа к изображению
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            response.put("message", "Ошибка при загрузке файла: " + e.getMessage());
            return new ResponseEntity<>(Map.of("error", "Ошибка загрузки файла"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-schema/{label}")
    public ResponseEntity<Object> getSchemaByCode(@PathVariable String label) {
        File schemaPath = new File(UPLOAD_DIR + label);
        if (!schemaPath.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        File[] files = schemaPath.listFiles();
        File imageFile = files[0];

        FileSystemResource resource = new FileSystemResource(imageFile);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource);

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
}
