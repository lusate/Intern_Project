package com.omnilab.templatekotlin.domain.file;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class FileForm {
    private Long id;
    private String fileName;
    private List<MultipartFile> imageFiles;
}
