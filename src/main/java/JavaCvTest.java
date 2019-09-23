import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JavaCvTest {
    public static void videoIntercept(String inputfile) {
        Frame frame = null;
        List<File> files = new ArrayList();
        FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber(inputfile);
        fFmpegFrameGrabber.setOption("rtsp_transport","tcp");
        String filePath = "C://video//images//";
        String fileTargetName = "movie";
        try {
            fFmpegFrameGrabber.start();
            int ftp = fFmpegFrameGrabber.getLengthInFrames();
            double FrameRate=fFmpegFrameGrabber.getFrameRate();
            System.out.println("开始视频提取帧");
            for (int i = 0; i < ftp; i=i+(int)FrameRate) {
                if (i <ftp) {
                    frame = fFmpegFrameGrabber.grabImage();
                    doExecuteFrame(frame, filePath, fileTargetName, i, files);
                }
            }
            System.out.println("============运行结束============");
            fFmpegFrameGrabber.stop();
        } catch (IOException E) {
            E.printStackTrace();
        }
    }

    public static void doExecuteFrame(Frame frame, String targetFilePath, String targetFileName, int index, List<File> files) {
        if (frame == null || frame.image == null) {
            return;
        }
        Java2DFrameConverter converter = new Java2DFrameConverter();
        String imageMat = "jpg";
        String fileName = targetFilePath + targetFileName + "_" + index + "." + imageMat;
        BufferedImage bi = converter.getBufferedImage(frame);
        File output = new File(fileName);
        files.add(output);
        try {
            ImageIO.write(bi, imageMat, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        videoIntercept("rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov");

    }
}



