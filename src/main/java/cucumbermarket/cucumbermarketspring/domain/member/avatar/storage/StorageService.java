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

//    @Autowired
//    public StorageService(StorageProperties properties, AvatarRepository avatarRepository, MemberRepository memberRepository) {
//        this.rootLocation = Paths.get(properties.getLocation());
//        this.avatarRepository = avatarRepository;
//        this.memberRepository = memberRepository;
//    }

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
        else{
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
//        Path destinationFile = this.rootLocation.resolve(
//                Paths.get(avatar.getPath()))
//                .normalize().toAbsolutePath();
        return s3Uploader.download(avatar.getPath(), avatar.getName());
    }


    //Todo
//    @Transactional
//    public void init() {
//        try {
//            Files.createDirectories(rootLocation);
//        }
//        catch (IOException e) {
//            throw new StorageException("Could not initialize storage", e);
//        }
//    }
//
//    @Transactional
//    public void deleteAll() {
//        FileSystemUtils.deleteRecursively(rootLocation.toFile());
//    }


    @Transactional
    public void delete(Long memberId) {

        Member member = memberRepository.findById(memberId).get();
        Avatar avatar = member.getAvatar();
        if (avatar == null) {
            throw new NullPointerException("프로필 사진이 존재하지 않습니다.");
        }
        member.deleteAvatar();
        avatarRepository.delete(avatar);
    }

    // compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }
}
