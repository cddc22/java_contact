package com.tang.work.controller;

import com.tang.work.ContactApplication;
import com.tang.work.dao.User;
import com.tang.work.dao.UserDao;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;

/**
 * @author tang
 */
@Controller
@Slf4j
public class Login implements Initializable{

    public TextField password;
    public TextField username;
    @Autowired
    private UserDao userDao;
    @Autowired
    private User user;

    public static void main(String[] args) {
        launch(args);
    }
    public void submit() {

        String namestr = username.getText().trim();
        String passwordstr = password.getText().trim();

         if("".equals(namestr)|| "".equals(passwordstr)){
             Alert alert = new Alert(AlertType.ERROR);
             alert.setContentText("用户名或密码必填");
             alert.showAndWait();
         }else {
             user = userDao.selectByPrimaryKey(namestr, passwordstr);
             if (user.getUserid().equals(1)) {
                 ContactApplication.ROOT = true;
             }

             if (user != null) {
                 openMainWindows();
                 ContactApplication.USERID = user.getUserid();
             } else {
                 alertLoginFail();
             }
         }
    }

    private void alertLoginFail() {

        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("用户名或密码错误");
        alert.showAndWait();
    }

    @SneakyThrows
    private void openMainWindows() {
        Stage mainStage = new Stage();
        mainStage.setScene(new Scene(ContactApplication.loadFxml("/index.fxml").load()));
        mainStage.show();

        mainStage.getIcons().add(new Image(Objects.requireNonNull(ContactApplication.class.getResourceAsStream("/images/icon.png"))));
        mainStage.setOnCloseRequest(event -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("退出确认框");
            //设置对话框的 icon 图标
            //alert.setGraphic(new ImageView(new Image(Objects.requireNonNull(ContactApplication.class.getResourceAsStream("/images/alert1.png")))));
            alert.initOwner(mainStage);
            alert.setHeaderText("您确定要现在退出程序？");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.CANCEL) {
                event.consume();
            }

        });

        Window window = username.getScene().getWindow();
        if(window instanceof Stage){
            ((Stage) window).close();
        }


    }
    @SneakyThrows
    public void register() {
        Stage regStage = new Stage();
        regStage.setScene(new Scene(ContactApplication.loadFxml("/register.fxml").load()));
        regStage.show();
        Window window = username.getScene().getWindow();
        if(window instanceof Stage){
            ((Stage) window).close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
