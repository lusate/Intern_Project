package com.omnilab.templatekotlin.domain.file;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class File {
    private Long id;
    private String fileName;
    private List<UploadFile> imageFiles;
}
