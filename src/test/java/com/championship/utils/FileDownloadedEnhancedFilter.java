package com.championship.utils;

import com.codeborne.selenide.Config;
import com.codeborne.selenide.impl.Downloader;
import com.codeborne.selenide.proxy.FileDownloadFilter;
import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Default selenide download implementation ignore files with missing Content-Disposition header.
 * The custom filter process requests without Content-Disposition.
 * It also filters out requests which don`t have a file with .txt extension like any libraries
 * which might be loaded during the test run.
 *
 * It needs to be injected in the test as:
 * getSelenideProxy().addResponseFilter("proxy-usages.response", filter);
 *
 */
public class FileDownloadedEnhancedFilter extends FileDownloadFilter {

    private static final Logger log = Logger.getLogger(FileDownloadFilter.class.getName());
    private final Downloader downloader = new Downloader();
    private final List<File> downloadedFiles = new ArrayList<>();
    private final Config config;

    public FileDownloadedEnhancedFilter(Config config) {
        super(config);
        this.config = config;
    }

    @Override
    public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {

        if (response.getStatus().code() < 200 || response.getStatus().code() >= 300) return;

        if (!messageInfo.getUrl().contains(".txt")) return;
        String[] urlParts = messageInfo.getUrl().split("/");
        String fileName = urlParts[urlParts.length - 1];
        File file = downloader.prepareTargetFile(config, fileName);

        try {
            FileUtils.writeByteArrayToFile(file, contents.getBinaryContents());
            downloadedFiles.add(file);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Failed to save downloaded file to " + file.getAbsolutePath() +
                    " for url " + messageInfo.getUrl(), e);
        }
    }

    @Override
    public List<File> getDownloadedFiles() {
        return downloadedFiles;
    }
}
