package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {

    private Logger logger = LoggerFactory.getLogger(AvatarService.class);
    @Value("${path.to.avatars.folder}")
    private String avatarsDir;
    private final StudentService studentService;
    private final AvatarRepository avatarRepository;
    public AvatarService(StudentService studentService, AvatarRepository avatarRepository){
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.debug("Requesting uploadAvatar studentId id={} avatarFile name={}", studentId, avatarFile.getName());
        Student student = studentService.getStudentById(studentId);

        Path filePath = Path.of(avatarsDir, student + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = findAvatar(studentId);

        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        //avatar.setData(avatarFile.getBytes());
        avatar.setData(generateDataForDB(filePath));
        avatarRepository.save(avatar);

        logger.debug("The request of uploadAvatar is successful for studentId={} and filepath={}", studentId, filePath.toString());
    }
    private byte[] generateDataForDB(Path filePath) throws IOException{
        logger.debug("Requesting generateDataForDB filePath={}", filePath.toString());
        try (
                InputStream is = Files.newInputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(is, 3092);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics2D = preview.createGraphics();
            graphics2D.drawImage(image, 0, 0, 100, height, null);
            graphics2D.dispose();

            ImageIO.write(preview, getExtensions(filePath.getFileName().toString()), baos);
            logger.debug("The request of generateDataForDB is successful for filepath={}", filePath.toString());

            return baos.toByteArray();
        }
    }
    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public Avatar findAvatar(Long id){
        logger.debug("Requesting findAvatar id={}", id);
        Avatar findAvatar = avatarRepository.findByStudentId(id).orElse(new Avatar());
        logger.debug("The request of findAvatar is successful and avatar id={}", findAvatar.getId());

        return findAvatar;
    }

    public List<Avatar> getAllAvatars(Integer pageNumber, Integer pageSize){
        logger.debug("Requesting getAllAvatars pageNumber={} and pageSize={}", pageNumber, pageSize);
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        List<Avatar> avatars = avatarRepository.findAll(pageRequest).getContent();
        logger.debug("The request of getAllAvatars is successful and numbers={}", avatars.size());

        return avatars;
    }


}
