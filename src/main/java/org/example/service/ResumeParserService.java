package org.example.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ResumeParserService {

    public String extractTextFromFile(MultipartFile file) throws IOException {
        String contentType = file.getContentType();

        if (contentType != null && contentType.equals("application/pdf")) {
            return extractTextFromPDF(file.getInputStream());
        } else if (contentType != null && (contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                || contentType.equals("application/msword"))) {
            return extractTextFromWord(file.getInputStream());
        } else {
            throw new IllegalArgumentException("Unsupported file type: " + contentType);
        }
    }

    public String extractTextFromBytes(byte[] fileBytes, String mimeType) throws IOException {
        if (mimeType != null && mimeType.equals("application/pdf")) {
            return extractTextFromPDF(new ByteArrayInputStream(fileBytes));
        } else if (mimeType != null && (mimeType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                || mimeType.equals("application/msword"))) {
            return extractTextFromWord(new ByteArrayInputStream(fileBytes));
        } else {
            throw new IllegalArgumentException("Unsupported file type: " + mimeType);
        }
    }

    private String extractTextFromPDF(InputStream inputStream) throws IOException {
        try (PDDocument document = org.apache.pdfbox.Loader.loadPDF(inputStream.readAllBytes())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    private String extractTextFromWord(InputStream inputStream) throws IOException {
        try (XWPFDocument document = new XWPFDocument(inputStream)) {
            StringBuilder text = new StringBuilder();
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                text.append(paragraph.getText()).append("\n");
            }
            return text.toString();
        }
    }
}

