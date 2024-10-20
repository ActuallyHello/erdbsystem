package com.ustu.erdb.modules.models.api;

import com.ustu.erdb.modules.models.store.models.Model;
import com.ustu.erdb.modules.models.store.models.ModelInfo;
import com.ustu.erdb.modules.models.store.repository.ModelInfoRepository;
import com.ustu.erdb.modules.models.store.repository.ModelRepository;
import com.ustu.erdb.modules.persons.dto.UserRequestDto;
import com.ustu.erdb.modules.persons.store.models.Person;
import com.ustu.erdb.modules.persons.store.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class ModelController {

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/imgs/";
    ;
    private static final String SCHEMA_NAME = "SCHEMA";
    private static final String CREATE_MODEL_PAGE = "models/create-model";
    private static final String MODEL_PAGE = "models/model";

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ModelRepository modelRepository;
    @Autowired
    private ModelInfoRepository modelInfoRepository;

    @GetMapping("/create-model")
    public String getCreateModelPage(HttpSession httpSession, org.springframework.ui.Model pageModel, @RequestParam(required = false) Boolean isTaskResult) {
        if (httpSession.getAttribute("personid") == null) {
            return "redirect:/login";
        }
        pageModel.addAttribute("title", "Создание модели");
        pageModel.addAttribute("isTaskResult", isTaskResult);
        return CREATE_MODEL_PAGE;
    }

    @PostMapping("/create-model")
    public String createModelPage(HttpSession httpSession, org.springframework.ui.Model pageModel,
                                  @RequestParam("label") String label,
                                  @RequestParam("topic") String topic,
                                  @RequestParam("description") String description,
                                  @RequestParam(value = "isTaskResult", required = false) Boolean isTaskResult,
                                  @RequestParam("schema") MultipartFile file) {
        try {
            Person person = personRepository.findById((Long) httpSession.getAttribute("personid"))
                    .orElseThrow(() -> new RuntimeException("Ошибка при определении пользователя!"));

            if (file.isEmpty()) {
                throw new RuntimeException("Ошибка при загрузке файла!");
            }
            File uploadDir = new File(UPLOAD_DIR + label);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs(); // Создает директории, если они не существуют
            }
            // Сохранение схемы модели
            String schemaPath = UPLOAD_DIR + String.format("%s/%s", label, file.getOriginalFilename());
            File dest = new File(schemaPath);
            file.transferTo(dest);

            Model model = Model.builder()
                    .person(person)
                    .label(label)
                    .description(description)
                    .topic(topic)
                    .schemaPath(schemaPath)
                    .isTaskResult(isTaskResult != null ? isTaskResult : false)
                    .build();
            model = modelRepository.save(model);
            return String.format("redirect:/model/%d", model.getId());
        } catch (Exception e) {
            pageModel.addAttribute("error", "Ошибка при создании модели! " + e.getMessage());
            return CREATE_MODEL_PAGE;
        }
    }

    @GetMapping("/model/{id}")
    public String getModelPage(HttpSession httpSession, org.springframework.ui.Model pageModel,
                               @PathVariable Long id) {
        if (httpSession.getAttribute("personid") == null) {
            return "redirect:/login";
        }
        pageModel.addAttribute("title", "Модель");
        try {
            Person person = personRepository.findById((Long) httpSession.getAttribute("personid"))
                    .orElseThrow(() -> new RuntimeException("Ошибка при определении пользователя!"));
            Model model = modelRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Ошибка при определении модели с идентификатором " + id));
            ModelInfo modelInfo = modelInfoRepository.findByModel(model)
                            .orElse(null);
            pageModel.addAttribute("model", model);
            pageModel.addAttribute("person", person);
            pageModel.addAttribute("modelinfo", modelInfo);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                    .withZone(ZoneId.systemDefault());
            pageModel.addAttribute("modelCreatedAt", formatter.format(model.getCreatedAt()));
            pageModel.addAttribute("isTaskResult", model.getIsTaskResult() != null ? model.getIsTaskResult() : false);
            return MODEL_PAGE;
        } catch (Exception e) {
            pageModel.addAttribute("error", "Ошибка при получении модели! " + e.getMessage());
            return MODEL_PAGE;
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

    @PostMapping("/model-info")
    public String modelInfo(HttpSession httpSession, org.springframework.ui.Model pageModel,
                            @RequestParam Integer tables,
                            @RequestParam Integer relations,
                            @RequestParam String archetype,
                            @RequestParam Integer normalization,
                            @RequestParam Long modelId) {
        try {
            Model model = modelRepository.findById(modelId)
                    .orElseThrow(() -> new RuntimeException("Модели с таким идентификатором не существует: " + modelId));
            ModelInfo modelInfo = ModelInfo.builder()
                    .tableCount(tables)
                    .relationCount(relations)
                    .normalizationLevel(normalization)
                    .archetype(archetype)
                    .model(model)
                    .build();
            modelInfoRepository.save(modelInfo);
            return "redirect:/model/" + modelId;
        } catch (Exception e) {
            e.printStackTrace();
            pageModel.addAttribute("error", "Ошибка при получении модели! " + e.getMessage());
            return MODEL_PAGE;
        }
    }

//    @PostMapping("/create-models")
//    public ResponseEntity<Object> createModel(@RequestParam("label") String label,
//                                             @RequestParam("topic") String topic,
//                                             @RequestParam("description") String description,
//                                             @RequestParam("personId") Long personId,
//                                             @RequestParam("isTaskResult") Boolean isTaskResult,
//                                             @RequestParam("schema") MultipartFile file) {
//        Map<String, String> response = new HashMap<>();
//
//        if (file.isEmpty()) {
//            response.put("message", "Выберите файл для загрузки!");
//            return new ResponseEntity<>(Map.of("error", "Ошибка загрузки файла"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        Optional<Person> personOpt = personRepository.findById(personId);
//        if (personOpt.isEmpty()) {
//            return new ResponseEntity<>(Map.of("error", "Не найден пользователь"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        Person person = personOpt.get();
//        Model model = modelRepository.save(
//                Model.builder()
//                        .isTaskResult(isTaskResult)
//                        .createdAt(new Date().toInstant())
//                        .updatedAt(new Date().toInstant())
//                        .description(description)
//                        .label(label)
//                        .topic(topic)
//                        .person(person)
//                        .build());
//        try {
//            File uploadDir = new File(UPLOAD_DIR + label);
//            if (!uploadDir.exists()) {
//                uploadDir.mkdirs(); // Создает директории, если они не существуют
//            }
//            // Сохранение файла
//            File dest = new File(UPLOAD_DIR + String.format("%s/%s", label, file.getOriginalFilename()));
//            file.transferTo(dest);
//            response.put("message", "Файл загружен успешно: " + label);
//            response.put("imageLabel", String.format("/imgs/%s/%s", label, file.getOriginalFilename())); // URL для доступа к изображению
//            response.put("modelId", model.getLabel());
//            return ResponseEntity.ok(response);
//        } catch (IOException e) {
//            response.put("message", "Ошибка при загрузке файла: " + e.getMessage());
//            return new ResponseEntity<>(Map.of("error", "Ошибка загрузки файла"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//
//    @Autowired
//    private ModelRepository modelRepository;
//    @Autowired
//    private ModelInfoRepository modelInfoRepository;
//
//    @GetMapping("/models/{id}")
//    public ResponseEntity<Object> getSchemaByCode(@PathVariable Long id) {
//        Optional<Model> modelOpt = modelRepository.findById(id);
//        if (modelOpt.isEmpty()) {
//            return new ResponseEntity<>(Map.of("error", "Такой модели не существует!"), HttpStatus.INTERNAL_SERVER_ERROR);
//        } else {
//            Model model = modelOpt.get();
//            Person person = model.getPerson();
//            HashMap<Object, Object> result = new HashMap<>();
//            result.put("label", model.getLabel());
//            result.put("description", model.getDescription());
//            result.put("topic", model.getTopic());
//            result.put("id", model.getId());
//            result.put("createdAt", model.getCreatedAt());
//            result.put("isTaskResult", model.getIsTaskResult());
//            result.put("firstName", person.getFirstName());
//            result.put("lastName", person.getLastName());
//            result.put("middleName", person.getMiddleName());
//            result.put("group", person.getGroup());
//            result.put("type", person.getType());
//
//            Optional<ModelInfo> byModel = modelInfoRepository.findByModel(model);
//            if (byModel.isPresent()) {
//                ModelInfo modelInfo = byModel.get();
//                result.put("tables", modelInfo.getTableCount());
//                result.put("relations", modelInfo.getRelationCount());
//                result.put("archetype", modelInfo.getArchetype());
//                result.put("normalization", modelInfo.getNormalizationLevel());
//            }
//            return ResponseEntity.ok(result);
//        }
//    }
//
//    @PostMapping("/update-model-info")
//    public ResponseEntity<Object> updateModelInfo(@RequestParam Long modelId,
//                                             @RequestParam Integer tables,
//                                             @RequestParam Integer relations,
//                                             @RequestParam String archetype,
//                                             @RequestParam Integer normalization) {
//        Optional<Model> modelOpt = modelRepository.findById(modelId);
//        if (modelOpt.isPresent()) {
//            ModelInfo save = modelInfoRepository.save(ModelInfo.builder()
//                    .archetype(archetype)
//                    .model(modelOpt.get())
//                    .relationCount(relations)
//                    .tableCount(tables)
//                    .normalizationLevel(normalization)
//                    .build());
//            return ResponseEntity.ok(Map.of("modelInfo", save.getId()));
//        }
//        return new ResponseEntity<>(Map.of("error", "Ошибка при сохранении доп информации по модели!"), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
