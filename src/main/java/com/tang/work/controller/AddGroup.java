package com.tang.work.controller;

import com.tang.work.ContactApplication;
import com.tang.work.dao.GroupDao;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Objects;

@Controller
public class AddGroup {


    public TextField groupname;
    @Autowired
    private GroupDao groupDao;

    public void addGro() {
        int i = groupDao.selectByName(groupname.getText());

        if((i==0)){
            groupDao.insert(groupname.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("添加成功");
            alert.setHeaderText("");
            alert.setGraphic(new ImageView(new Image(Objects.requireNonNull(ContactApplication.class.getResourceAsStream("/images/alert1.png")))));
            alert.setContentText("返回主页中");
            alert.show();
            back();

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("添加失败");
            alert.setHeaderText("");
            alert.setGraphic(new ImageView(new Image(Objects.requireNonNull(ContactApplication.class.getResourceAsStream("/images/alert2.png")))));
            alert.setContentText("请勿重复添加");
            alert.show();
        }
    }

    public void back() {
        Window window = groupname.getScene().getWindow();
        if(window instanceof Stage){
            ((Stage) window).close();
        }

    }
}
