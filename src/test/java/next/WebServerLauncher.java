package next;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi.BColor;
import com.diogonunes.jcdp.color.api.Ansi.FColor;

public class WebServerLauncher {
    private static final Logger logger = LoggerFactory.getLogger(WebServerLauncher.class);
    private static final int PORT_NUMBER = 8080;

    public static void main(String[] args) throws Exception {
    		printBanner();
        String webappDirLocation = "webapp/";
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(PORT_NUMBER);

        tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
        logger.info("configuring app with basedir: {}", new File("./" + webappDirLocation).getAbsolutePath());

        tomcat.start();
        tomcat.getServer().await();
    }
    
    private static void printBanner() {
    	RandomAccessFile file = null;
    	
		try {
			file = new RandomAccessFile("src/main/resources/banner.txt", "r");
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());

		}

		try {
			FileChannel channel = file.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate((int) channel.size());
			channel.read(buffer);
			buffer.flip();

			for (int i = 0; i < channel.size(); i++) {
				System.out.print((char) buffer.get());
			}
			System.out.println();
			System.out.println(ColorCodes.ANSI_GREEN.getCode() + "::::::::::::::::Pobi Framework::::::::::::::::" + ColorCodes.ANSI_RESET.getCode());
			System.out.println(ColorCodes.ANSI_CYAN.getCode() + "A Simple Web Framework, built by Javajigi" + ColorCodes.ANSI_RESET.getCode());
			logger.info("Pobi Framework Running at Port {}", PORT_NUMBER);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
    }
}
