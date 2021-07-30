package cucumbermarket.cucumbermarketspring.domain.member.avatar.storage;

import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.MemberRepository;
import cucumbermarket.cucumbermarketspring.domain.member.avatar.Avatar;
import cucumbermarket.cucumbermarketspring.domain.member.avatar.AvatarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
@RequiredArgsConstructor
public class StorageService {

    private Path rootLocation;
    private AvatarRepository avatarRepository;
    private MemberRepository memberRepository;

    @Autowired
    public StorageService(StorageProperties properties, AvatarRepository avatarRepository, MemberRepository memberRepository) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.avatarRepository = avatarRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void store(Long memberId, MultipartFile file) {
        try {
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
                throw new StorageExtensionException("잘못된 형식의 파일");
            }
            String newFileName = memberId.toString() + "_avatar" + originalFileExtension;
            Path destinationFile = this.rootLocation.resolve(
                    Paths.get(newFileName))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
            Member foundMember = memberRepository.findById(memberId).get();
            Avatar byName = avatarRepository.findByName(memberId.toString() + "_avatar");
            if (byName != null) {
                avatarRepository.delete(byName);
            }
            Avatar avatar = Avatar.builder().
                    member(foundMember).
                    name(memberId.toString() + "_avatar").
                    path(destinationFile.toString()).
                    build();
            foundMember.updateAvatar(avatar);

            avatarRepository.save(avatar);
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Transactional
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Transactional
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
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
//        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }
    // uncompress the image bytes before returning it to the angular application
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

    public Path load(Long memberId) {
        Member member = memberRepository.findById(memberId).get();
        try {
            Avatar avatar = member.getAvatar();
            Path destinationFile = this.rootLocation.resolve(
                    Paths.get(avatar.getPath()))
                    .normalize().toAbsolutePath();

            return destinationFile;
        } catch (NullPointerException e) {
            throw new NullPointerException("프로필 사진이 존재하지 않습니다.");
        }
    }

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

    public String getAvatarPath(Long memberId) {
        try {
            Avatar avatar = avatarRepository.findByName(memberId.toString() + "_avatar");
            return avatar.getPath();
        } catch (NullPointerException e) {
            return "";
        }
    }
}
