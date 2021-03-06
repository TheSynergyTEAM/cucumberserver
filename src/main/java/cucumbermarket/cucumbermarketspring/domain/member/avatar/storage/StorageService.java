package cucumbermarket.cucumbermarketspring.domain.member.avatar.storage;

import cucumbermarket.cucumbermarketspring.aws.BucketName;
import cucumbermarket.cucumbermarketspring.aws.S3Uploader;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.MemberRepository;
import cucumbermarket.cucumbermarketspring.domain.member.avatar.Avatar;
import cucumbermarket.cucumbermarketspring.domain.member.avatar.AvatarRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RequiredArgsConstructor
@Service
public class StorageService {
    private final S3Uploader s3Uploader;

//    private Path rootLocation;
    private final AvatarRepository avatarRepository;
    private final MemberRepository memberRepository;


    @Transactional

    public Avatar save(Long memberId, MultipartFile file) {
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file.");
        }
        String originalFileExtension = "";
        if (file.getContentType().equals("image/jpeg"))
            originalFileExtension = ".jpeg";
        else if (file.getContentType().equals("image/png"))
            originalFileExtension = ".png";
        else if (file.getContentType().equals("image/jpg"))
            originalFileExtension = ".jpg";
        else {
            throw new StorageExtensionException("File Uploaded is not an type of image");
        }
        // get file metadata
        HashMap<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        //Save Image in S3 and then save in the database
        String path = String.format("%s/%s/%s", BucketName.TODO_IMAGE.getBucketName(), "avatar", UUID.randomUUID());
        String newFileName = memberId.toString() + "_avatar" + originalFileExtension;
        try {
            s3Uploader.upload(path, newFileName, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }

        Member foundMember = memberRepository.findById(memberId).get();
        Avatar byName = avatarRepository.findByName(newFileName);
        if (byName != null) {
            avatarRepository.delete(byName);
        }
        Avatar avatar = Avatar.builder().
                member(foundMember).
                name(newFileName).
                path(path).
                build();
        foundMember.updateAvatar(avatar);
        avatarRepository.save(avatar);
        return avatarRepository.findByName(newFileName);
    }

    public byte[] download(Long memberId) {
        Member member = memberRepository.findById(memberId).get();
        Avatar avatar = member.getAvatar();
        if (avatar == null) {
            return new byte[0];
        }
        return s3Uploader.download(avatar.getPath(), avatar.getName());
    }

    @Transactional
    public void delete(Long memberId) {

        Member member = memberRepository.findById(memberId).get();
        Avatar avatar = member.getAvatar();
        if (avatar == null) {
            throw new NullPointerException("????????? ????????? ???????????? ????????????.");
        }
        member.deleteAvatar();
        avatarRepository.delete(avatar);
    }

    public String getAvatarPath(Long memberId) {
        try {
            Avatar avatar = avatarRepository.findByNameContaining(memberId.toString() + "_avatar");
            return avatar.getPath();
        } catch (NullPointerException e) {
            return "";
        }
    }
}
