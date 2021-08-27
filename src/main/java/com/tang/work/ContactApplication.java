package com.tang.work;

import com.tang.work.dao.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Objects;

/**
 * @author tang
 */
@Slf4j
@SpringBootApplication
@MapperScan("com.tang.work.dao")

public class ContactApplication extends Application implements ApplicationRunner {
     public static boolean ROOT = false;
     public static int UserId = 0;
     public static User user;
        private static ApplicationContext applicationContext;
        public static FXMLLoader loadFxml(String fxmlPath){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(ContactApplication.class.getResource(fxmlPath));
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            return fxmlLoader;
        }
    public static void main(String[] args){
        applicationContext = SpringApplication.run(ContactApplication.class,args);
        ContactApplication.launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
            stage.setScene(new Scene(loadFxml("/fxml/login.fxml").load()));
            stage.getIcons().add(new Image(Objects.requireNonNull(ContactApplication.class.getResourceAsStream("/images/icon.png"))));
            stage.show();

    }
    @Override
    public void stop() throws Exception {
        log.info("stop");

        super.stop();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("ApplicationRunner.run...");
        Thread.sleep(1000);
    }
}
