package ssm9;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.FileAppender;
import org.apache.log4j.HTMLLayout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Log4JTest {
	 
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Log4JTest.class);
        BasicConfigurator.configure();
        HTMLLayout layout = new HTMLLayout();
        // SimpleLayout layout = new SimpleLayout();
        try {
            FileAppender appender = new FileAppender(layout, "C:\\TP\\guo\\out.html", false);
            logger.addAppender(appender);
            
            
            //设置日志输出级别为info，这将覆盖配置文件中设置的级别，只有日志级别高于WARN的日志才输出
            logger.setLevel(Level.DEBUG);
            logger.debug("这是debug");
            logger.info("这是info");
            logger.warn("这是warn");
            logger.error("这是error");
            logger.fatal("这是fatal");
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
}
