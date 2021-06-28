package cucumbermarket.cucumbermarketspring.domain.member.controller;

import cucumbermarket.cucumbermarketspring.domain.member.avatar.storage.StorageFileNotFoundException;
import cucumbermarket.cucumbermarketspring.domain.member.avatar.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Path;

@RestController
@RequiredArgsConstructor
public class AvatarUploadController {

    private final StorageService storageService;

    @PostMapping("/member/{id}/avatar")
    public ResponseEntity<?> handleAvatarUpload(@PathVariable("id") Long id, @RequestParam("file") MultipartFile file) {

        storageService.store(id, file);
        return ResponseEntity.ok().body("ok");
    }

    @GetMapping(value = "/member/{id}/avatar", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<?> handleAvatarRequest(@PathVariable("id") Long id) {
        // Receive avatar file path
        try {
            Path filePath = storageService.load(id);
            byte[] bytes = IOUtils.toByteArray(filePath.toUri());
            return ResponseEntity.ok().body(bytes);
        } catch (NullPointerException | IOException e) {
            return ResponseEntity.badRequest().body("프로필이 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/member/{id}/avatar")
    public ResponseEntity<?> handleAvatarUpdate(@PathVariable("id") Long id) {
        try {
            storageService.delete(id);
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body("프로필 사진이 존재하지 않습니다.");
        }
        return ResponseEntity.ok().body("ok");
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
