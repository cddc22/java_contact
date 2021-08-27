package com.tang.work.controller;

import com.tang.work.ContactApplication;
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
    private int contactid;
    private int groupid;

    public EditContact() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reset();
        groupsListView.getItems().addAll(groupDao.selectAll(null,null));
    }

    public void addContact() {
        try {
            groupid= groupsListView.getSelectionModel().getSelectedItem().getGroupid();
        }catch (NullPointerException e)
        {
            //e.printStackTrace();
        }
        System.out.println(groupid);
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
        contact.setGroupid(groupid);
        contact.setId(contactid);
        contact.setUserid(ContactApplication.UserId);
        int i = contactDao.updateByPrimaryKeySelective(contact);
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
        contactid=contact.getId();
        groupid=contact.getGroupid();
        groupsListView.scrollTo(1);

       //groupsListView.getSelectionModel().select(1);

    }
}
