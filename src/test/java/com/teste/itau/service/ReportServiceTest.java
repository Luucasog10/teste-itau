package com.teste.itau.service;

import com.teste.itau.model.User;
import com.teste.itau.repository.UserRepository;
import com.teste.itau.service.ReportService;
import com.teste.itau.service.S3Service;
import com.teste.itau.util.PDFGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ReportServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private S3Service s3Service;

    @InjectMocks
    private ReportService reportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGenerateUserReport_UserExists() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setDescription("Sample Description");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        ByteArrayInputStream pdfInputStream = new ByteArrayInputStream("sample pdf content".getBytes());
        mockStatic(PDFGenerator.class).when(() -> PDFGenerator.generateUserReport(user)).thenReturn(pdfInputStream);

        boolean result = reportService.generateUserReport(userId);

        assertTrue(result);
        verify(s3Service, times(1)).uploadFile(eq("reports/user-" + userId + ".pdf"), eq(pdfInputStream));
    }

    @Test
    public void testGenerateUserReport_UserNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        boolean result = reportService.generateUserReport(userId);

        assertFalse(result);
        verify(s3Service, times(0)).uploadFile(anyString(), any(ByteArrayInputStream.class));
    }
}