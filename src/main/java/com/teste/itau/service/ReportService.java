package com.teste.itau.service;

import com.teste.itau.model.User;
import com.teste.itau.repository.UserRepository;
import com.teste.itau.util.PDFGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public class ReportService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private S3Service s3Service;

    public boolean generateUserReport(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return false;
        }

        ByteArrayInputStream pdf = PDFGenerator.generateUserReport(user);

        String fileName = "reports/user-" + userId + ".pdf";
        s3Service.uploadFile(fileName, pdf);

        return true;
    }
}
