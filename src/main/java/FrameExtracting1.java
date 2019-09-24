
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FrameExtracting1 {
    public static void videoIntercept(String inputFile, String filePath) {
        Frame frame = null;
        List<File> files = new ArrayList();
        FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber(inputFile);
        fFmpegFrameGrabber.setOption("rtsp_transport", "tcp");
        String fileTargetName = "movie";
        Long i = 0L;
        System.out.println("开始视频提取帧");
        try {
            while (true) {
                fFmpegFrameGrabber.start();
                i = System.currentTimeMillis();
                int ftp = fFmpegFrameGrabber.getLengthInFrames();
                int FrameRate = (int) fFmpegFrameGrabber.getFrameRate();
                frame = fFmpegFrameGrabber.grabImage();
                doExecuteFrame(frame, filePath, fileTargetName, i, files);
            }


        } catch (IOException E) {
            E.printStackTrace();
        }
    }

    public static void doExecuteFrame(Frame frame, String targetFilePath, String targetFileName, Long index, List<File> files) {
        if (frame == null || frame.image == null) {
            return;
        }
        try {
            Java2DFrameConverter converter = new Java2DFrameConverter();
            String imageMat = "jpg";
            Date date = new Date(index);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
            String fileName = targetFilePath + targetFileName + "_" + format.format(date) + "." + imageMat;
            BufferedImage bi = converter.getBufferedImage(frame);
            File output = new File(fileName);
            files.add(output);
            try {
                ImageIO.write(bi, imageMat, output);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Thread.sleep(2000);

        } catch (InterruptedException E) {
            E.printStackTrace();

        }
    }

    public static void main(String[] args) {
        videoIntercept("rtmp://58.200.131.2:1935/livetv/hunantv","C://video//images//");
    }

}
