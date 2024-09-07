package com.et.controller;


import com.et.bean.DownLoadFileInfo;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.concurrent.*;


@RestController
public class DownloadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadController.class);
    private final static long PER_PAGE = 1024L * 1024L * 50L;
    private final static String DOWN_PATH = "D:\\tmp";
    private static final String UTF8 = "UTF-8";
    ExecutorService taskExecutor = Executors.newFixedThreadPool(10);
    @RequestMapping("/download")
    public void downLoadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        File file = new File("D:\\SoftWare\\oss-browser-win32-ia32.zip");
        response.setCharacterEncoding(UTF8);
        InputStream is = null;
        OutputStream os = null;
        try {
            // chunk download Range  bytes=100-1000  100-
            long fSize = file.length();
            response.setContentType("application/x-download");
            String fileName = URLEncoder.encode(file.getName(), UTF8);
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            // Accept-Range
            response.setHeader("Accept-Range", "bytes");
            response.setHeader("fSize", String.valueOf(fSize));
            response.setHeader("fName", fileName);

            long pos = 0, last = fSize - 1, sum = 0;
            if (null != request.getHeader("Range")) {
                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                String numberRange = request.getHeader("Range").replaceAll("bytes=", "");
                String[] strRange = numberRange.split("-");
                if (strRange.length == 2) {
                    pos = Long.parseLong(strRange[0].trim());
                    last = Long.parseLong(strRange[1].trim());
                    if (last > fSize-1) {
                        last = fSize - 1;
                    }
                } else {
                    pos = Long.parseLong(numberRange.replaceAll("-", "").trim());
                }
            }
            long rangeLength = last - pos + 1;
            String contentRange = new StringBuffer("bytes").append(pos).append("-").append(last).append("/").append(fSize).toString();
            response.setHeader("Content-Range", contentRange);
            response.setHeader("Content-Length", String.valueOf(rangeLength));

            os = new BufferedOutputStream(response.getOutputStream());
            is = new BufferedInputStream(new FileInputStream(file));
            is.skip(pos);
            byte[] buffer = new byte[1024];
            int length = 0;
            while (sum < rangeLength) {
                int readLength = (int) (rangeLength - sum);
                length = is.read(buffer, 0, (rangeLength - sum) <= buffer.length ? readLength : buffer.length);
                sum += length;
                os.write(buffer,0, length);
            }
            System.out.println("download finish");
        }finally {
            if (is != null){
                is.close();
            }
            if (os != null){
                os.close();
            }
        }
    }

    @RequestMapping("/downloadFile")
    public String downloadFile() {

        DownLoadFileInfo fileInfo = download(0, 10, -1, null);
        if (fileInfo != null) {
            long pages =  fileInfo.fSize / PER_PAGE;
            for (long i = 0; i <= pages; i++) {
                Future<DownLoadFileInfo> future = taskExecutor.submit(new DownloadThread(i * PER_PAGE, (i + 1) * PER_PAGE - 1, i, fileInfo.fName));
                if (!future.isCancelled()) {
                    try {
                        fileInfo = future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
            return System.getProperty("user.home") + "\\Downloads\\" + fileInfo.fName;
        }
        return null;
    }


    private DownLoadFileInfo download(long start, long end, long page, String fName) {
        File dir = new File(DOWN_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(DOWN_PATH, page + "-" + fName);
        if (file.exists() && page != -1 && file.length() == PER_PAGE) {
            return null;
        }
        try {
            HttpClient client = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet("http://127.0.0.1:8080/download");
            httpGet.setHeader("Range", "bytes=" + start + "-" + end);
            HttpResponse response = client.execute(httpGet);
            String fSize = response.getFirstHeader("fSize").getValue();
            fName = URLDecoder.decode(response.getFirstHeader("fName").getValue(), "UTF-8");
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int ch;
            while ((ch = is.read(buffer)) != -1) {
                fos.write(buffer, 0, ch);
            }
            is.close();
            fos.flush();
            fos.close();
            // last part
            if (end - Long.parseLong(fSize) > 0) {
                // merge file
                mergeFile(fName, page);
            }

            return new DownLoadFileInfo(Long.parseLong(fSize), fName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void mergeFile(String fName, long page) {
        File file = new File(DOWN_PATH, fName);
        try {
            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(file));
            for (long i = 0; i <= page; i++) {
                File tempFile = new File(DOWN_PATH, i + "-" + fName);
                while (!file.exists() || (i != page && tempFile.length() < PER_PAGE)) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                byte[] bytes = FileUtils.readFileToByteArray(tempFile);
                os.write(bytes);
                os.flush();
                tempFile.delete();
            }
            File testFile = new File(DOWN_PATH, -1 + "-null");
            testFile.delete();
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * get file size
     */
    private long getRemoteFileSize(String remoteFileUrl) throws IOException {
        long fileSize = 0;
        HttpURLConnection httpConnection = (HttpURLConnection) new URL(remoteFileUrl).openConnection();
        //使用HEAD方法
        httpConnection.setRequestMethod("HEAD");
        int responseCode = httpConnection.getResponseCode();
        if (responseCode >= 400) {
            LOGGER.debug("Web responsible fail!");
            return 0;
        }
        String sHeader;
        for (int i = 1;; i++) {
            sHeader = httpConnection.getHeaderFieldKey(i);
            if (sHeader != null && sHeader.equals("Content-Length")) {
                LOGGER.debug("file size ContentLength:" + httpConnection.getContentLength());
                fileSize = Long.parseLong(httpConnection.getHeaderField(sHeader));
                break;
            }
        }
        return fileSize;
    }

    class DownloadThread implements Callable<DownLoadFileInfo> {
        long start;
        long end;
        long page;
        String fName;

        public DownloadThread(long start, long end, long page, String fName) {
            this.start = start;
            this.end = end;
            this.page = page;
            this.fName = fName;
        }

        @Override
        public DownLoadFileInfo call() {
            return download(start, end, page, fName);
        }
    }
}
