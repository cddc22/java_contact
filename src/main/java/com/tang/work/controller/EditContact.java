package com.tang.work.controller;

import com.tang.work.dao.Contact;
import com.tang.work.dao.ContactDao;
import com.tang.work.dao.Group;
import com.tang.work.dao.GroupDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * @author tang
 */
@Controller
public class EditContact implements Initializable {
    @FXML
    private RadioButton sex2;
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
    @FXML
    private DatePicker birth1;
    @Autowired
    private ContactDao contactDao;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    Contact contact;
    @Autowired
    Index index;

    public EditContact() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reset();
           groupsListView.getItems().addAll(groupDao.selectAll());
    }

    public void addContact() {
        int selectedIndex = groupsListView.getSelectionModel().getSelectedItem().getGroupid();
        contact.setName(name.getText());
        contact.setTelephone(telephone.getText());
        contact.setWechat(wechat.getText());
        contact.setAddress(address.getText());
        contact.setEmail(email.getText());
        contact.setBirth(birth1.getValue().toString());
        boolean sex= sex1.isSelected();
        if(sex){
            contact.setSex("男");
        }else {
            contact.setSex("女");
        }
        contact.setGroupid(selectedIndex);
        System.out.println(contact.toString());
        int i = contactDao.updateByPrimaryKeySelective(contact);
        System.out.println(i+"fdfdsf");
        if (i == 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("修改成功");
            alert.setGraphic(null);
            alert.setHeaderText("");
            alert.setContentText("");
            alert.show();
            Stage stage = (Stage) name.getScene().getWindow();
            stage.close();
        }
    }

    public void reset() {

        System.out.println(index.selectUserId);
        name.clear();
        telephone.clear();
        wechat.clear();
        address.clear();
        email.clear();
        birth1.setValue(LocalDate.of(1998,9,9));
        Contact contact = contactDao.selectById(index.selectUserId);
        name.appendText(contact.getName());
        telephone.appendText(contact.getTelephone());
        wechat.appendText(contact.getWechat());
        address.appendText(contact.getAddress());
        email.appendText(contact.getEmail());
        if("男)".equals(contact.getSex())){
            sex1.setSelected(true);
        }else{
            sex2.setSelected(true);
        }
        groupsListView.getSelectionModel().select(0);
    }
}
