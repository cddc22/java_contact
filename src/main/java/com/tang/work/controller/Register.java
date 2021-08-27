package com.tang.work.controller;

import com.tang.work.ContactApplication;
import com.tang.work.dao.UserDao;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author tang
 */
@Slf4j
@Component
public class Register implements Initializable {
    public PasswordField pwd1;
    public PasswordField pwd2;
    public TextField name;
    public Button backbtn;
    @Autowired
    private UserDao userDao;

    public void register() {
        String username = name.getText().trim();
        String pw1 = pwd1.getText().trim();
        String pw2 = pwd2.getText().trim();

        int user = userDao.selectByName(username);
        if (user != 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("警告！");
            alert.setHeaderText("该用户名已经被注册！");
            alert.setContentText("");
            alert.setGraphic(new ImageView(new Image(Objects.requireNonNull(ContactApplication.class.getResourceAsStream("/images/alert2.png")))));
            alert.showAndWait();
        } else {
            if (!passwordCheck(pw1)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("警告！");
                alert.setHeaderText("密码不符合要求");
                alert.setContentText("密码格式大于6小于30！！");
                alert.setGraphic(new ImageView(new Image(Objects.requireNonNull(ContactApplication.class.getResourceAsStream("/images/alert2.png")))));
                alert.showAndWait();
            } else if (!pw1.equals(pw2)) {
                fail();
            } else {
                int i = userDao.insert(username, pw1);
                if (i != 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("恭喜");
                    alert.setHeaderText("注册成功");
                    alert.setContentText("");
                    alert.setGraphic(new ImageView(new Image(Objects.requireNonNull(ContactApplication.class.getResourceAsStream("/images/alert1.png")))));
                    alert.showAndWait();
                    //回到登录页面
                    backlogin();
                }
            }


        }
    }

    private void fail() {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("密码不一样呢");
        alert.showAndWait();
    }

    public void reset() {
        name.clear();
        pwd1.clear();
        pwd2.clear();
    }

    @SneakyThrows
    public void backlogin() {
        Stage stage = new Stage();
        stage.setScene(new Scene(ContactApplication.loadFxml("/fxml/login.fxml").load()));
        stage.show();
        Window window = name.getScene().getWindow();
        if (window instanceof Stage) {
            ((Stage) window).close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backbtn.setGraphic(new ImageView(new Image(ContactApplication.class.getResourceAsStream("/images/back.png"))));
    }

    public static boolean passwordCheck(String s) {
        if (s.length() > 30 || s.length() < 6 || ObjectUtils.isEmpty(s) || "".equals(s.replace(" ", ""))) {
            return false;
        } else {

            return true;
        }
    }
}
