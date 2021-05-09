package cucumbermarket.cucumbermarketspring.service;

import cucumbermarket.cucumbermarketspring.domain.file.PhotoRepository;
import cucumbermarket.cucumbermarketspring.domain.file.dto.PhotoDto;
import cucumbermarket.cucumbermarketspring.domain.file.service.PhotoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PhotoServiceTest {
    @Autowired
    private PhotoRepository fileRepository;
    @Autowired
    private PhotoService fileService;

    @Test
    public void fileUploadTest() throws Exception {
        //given
        final StringBuffer filename = new StringBuffer("test.jpg");
        final byte[] content = "hello world".getBytes();

        MockMultipartFile file = new MockMultipartFile("content", filename.toString(), "multipart/mixed", content);

        String origFileName = file.getOriginalFilename();
        String savePath = System.getProperty("member.dir") + "\\files";

        if (!new File(savePath).exists()) {
            try{
                new File(savePath).mkdir();
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }

        String filePath = savePath + "\\" + filename;
        file.transferTo(new File(filePath));

        PhotoDto fileDto = PhotoDto.builder()
                .origFileName(origFileName)
                .filePath(filePath)
                .build();

        //when
        Long sFileId = fileService.savePhoto(fileDto);

        //then
        assertThat(fileRepository.findAll()).isNotNull();
    }
}
