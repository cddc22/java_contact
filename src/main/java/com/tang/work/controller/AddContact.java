package com.tang.work.controller;

import com.tang.work.ContactApplication;
import com.tang.work.dao.Contact;
import com.tang.work.dao.ContactDao;
import com.tang.work.dao.Group;
import com.tang.work.dao.GroupDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author tang
 */
@Slf4j
@Controller
public class AddContact implements Initializable {
    public DatePicker birth;
    @FXML
    private TextField name;
    @FXML
    private TextField telephone;
    @FXML
    private TextField wechat;

    @FXML
    private TextField email;
    @FXML
    private TextField address;
    public ListView<Group> groupsListView;
    public RadioButton sex1;

    @Autowired
    private ContactDao contactDao;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    Index index;

    public void addContact(ActionEvent actionEvent) {
        Contact contact = new Contact();
        boolean sex= sex1.isSelected();
        if(sex){
            contact.setSex("男");
        }else {
            contact.setSex("女");
        }
        int selectedIndex = groupsListView.getSelectionModel().getSelectedItem().getGroupid();
        contact.setName(name.getText());
        contact.setTelephone(telephone.getText());
        contact.setWechat(wechat.getText());
        contact.setAddress(address.getText());
        contact.setEmail(email.getText());
        String date = birth.getValue().toString();
        System.out.println(date);
        contact.setBirth(date);
        contact.setGroupid(selectedIndex);
        contact.setUserid(ContactApplication.UserId);
        System.out.println(contact.toString());
        int i = contactDao.insertSelective(contact);
        if(i==1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("添加成功");
            alert.setHeaderText("");
            alert.setGraphic(new ImageView(new Image(Objects.requireNonNull(ContactApplication.class.getResourceAsStream("/images/alert1.png")))));
            alert.setContentText("返回主页中");
            alert.show();
            //back();
            index.load();
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
        Window window = name.getScene().getWindow();
        if(window instanceof Stage){
            ((Stage) window).close();
        }

    }
    public void reset() {
        back();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //List<Group> groups = groupDao.selectAll();
        groupsListView.getItems().addAll(groupDao.selectAll(null,ContactApplication.UserId));
        birth.setValue(LocalDate.of(1999,9,9));
    }
}
