package cucumbermarket.cucumbermarketspring.domain.member.controller;

import cucumbermarket.cucumbermarketspring.domain.member.avatar.storage.StorageFileNotFoundException;
import cucumbermarket.cucumbermarketspring.domain.member.avatar.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Path;

@RestController
@RequiredArgsConstructor
public class AvatarUploadController {

    private final StorageService storageService;

    @PostMapping("/member/avatar")
    public ResponseEntity<?> handleAvatarUpload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        long memberId = Long.parseLong(request.getAttribute("memberId").toString());
        storageService.save(memberId, file);
        return ResponseEntity.ok().body("ok");
    }

    @GetMapping(value = "/member/{id}/avatar", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<?> handleAvatarRequest(@PathVariable("id") Long id) {
        // Receive avatar file path
        try {
            byte[] downloaded = storageService.download(id);
//            Path filePath = storageService.download(id);
//            byte[] bytes = IOUtils.toByteArray(filePath.toUri());
            return ResponseEntity.ok().body(downloaded);
        } catch (NullPointerException e) {
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
