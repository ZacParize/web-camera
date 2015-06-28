package com.mycompany.format;

import org.apache.commons.io.FilenameUtils;
import java.io.File;

public class Picture implements PictureInfo {

    private File file;

    public Picture(File file) {
        this.setFile(file);
    }

    @Override
    public String getFormat() {
        return FilenameUtils.getExtension(file.getName()).toUpperCase();
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public void setFile(File file) {
        this.file = file;
    }
}
