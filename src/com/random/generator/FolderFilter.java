package com.random.generator;

import java.io.File;


class FolderFilter extends javax.swing.filechooser.FileFilter {
        @Override
        public boolean accept( File file ) {
            return file.isDirectory();
        }

        @Override
        public String getDescription() {
            return "Solo directory";
        }
}

