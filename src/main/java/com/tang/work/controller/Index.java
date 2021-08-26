package com.tang.work.controller;

import com.tang.work.ContactApplication;
import com.tang.work.dao.Contact;
import com.tang.work.dao.ContactDao;
import com.tang.work.dao.Group;
import com.tang.work.dao.GroupDao;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author tang
 */
@Slf4j
@Controller
public class Index extends AbstractJavaFxApplicationSupport implements Initializable {
    public ListView<Group> groupsListView;
    public Button admin;
    public Button searchBtn;
    int selectUserId;
    @FXML
    private TextField keyword;
    @FXML
    private TableColumn name;
    @FXML
    private TableColumn telephone;
    @FXML
    private TableColumn wechat;
    @FXML
    private TableColumn email;
    @FXML
    private TableColumn sex;
    @FXML
    private TableColumn birth;
    @FXML
    private TableColumn address;
    @FXML
    private TableView contactTable;
    @Resource
    private GroupDao groupDao;
    @Autowired
    private ContactDao contactDao;
    List<Contact> contacts;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        load(0);
        bindProperty();
        if(!ContactApplication.ROOT){
            admin.setVisible(false);
        }
        //searchBtn.setGraphic(new ImageView(new Image(ContactApplication.class.getResourceAsStream("/images/search.png"))));

    }
    @SneakyThrows
    public void delGroup() {
        int selectedIndex = groupsListView.getSelectionModel().getSelectedItem().getGroupid();
        if(contactDao.countByGroup(selectedIndex)==0) {
            Group group = groupDao.selectById(selectedIndex);
            groupDao.deleteByPrimaryKey(selectedIndex);
            groupsListView.getItems().removeAll(group);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("删除成功");
            alert.setGraphic(null);
            alert.setHeaderText("");
            alert.setContentText("");
            alert.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("组内联系人不为空");
            alert.setGraphic(null);
            alert.setHeaderText("");
            alert.setContentText("");
            alert.show();
        }

   }

    @SneakyThrows
    public void addGroup() {
        Stage mainStage = new Stage();
        mainStage.setScene(new Scene(ContactApplication.loadFxml("/addGroup.fxml").load()));
        mainStage.getIcons().add(new Image(ContactApplication.class.getResourceAsStream("/images/icon.png")));
        mainStage.show();
    }
    @SneakyThrows
    public void addPerson() {
        Stage mainStage = new Stage();
        mainStage.setScene(new Scene(ContactApplication.loadFxml("/addContact.fxml").load()));
        mainStage.getIcons().add(new Image(ContactApplication.class.getResourceAsStream("/images/icon.png")));
        mainStage.show();
    }

    @SneakyThrows
    public void editPerson() {
        Contact con2 = (Contact) contactTable.getSelectionModel().getSelectedItem();
        if(con2!=null){
            selectUserId = con2.getId();
            System.out.println(selectUserId);
            Stage mainStage = new Stage();
            mainStage.setScene(new Scene(ContactApplication.loadFxml("/editcontact.fxml").load()));
            mainStage.getIcons().add(new Image(ContactApplication.class.getResourceAsStream("/images/icon.png")));
            mainStage.show();
        }
    }

    public void collect() {
    }

    public void delPerson() {
        Contact con1 = (Contact) contactTable.getSelectionModel().getSelectedItem();
        contactDao.deleteByPrimaryKey(con1.getId());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("删除成功");
        alert.setGraphic(null);
        alert.setHeaderText("");
        alert.setContentText("");
        alert.show();
        load();
    }

    public void search() {
        contacts= contactDao.selectByPrimaryKey(keyword.getText());
        ObservableList<Contact> items = FXCollections.observableArrayList(contacts);
        contactTable.setItems(items);
        bindProperty();
    }

    private void bindProperty() {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        birth.setCellValueFactory(new PropertyValueFactory<>("birth"));
        telephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        wechat.setCellValueFactory(new PropertyValueFactory<>("wechat"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));

    }

    private void load(int groupid) {
        List<Group> groups = groupDao.selectAll();
        groupsListView.getItems().removeAll(groups);
        groupsListView.getItems().addAll(groups);

        System.out.println(groupid);
        if (groupid==0) {
            contacts = contactDao.selectAll();
        } else {
            contacts = contactDao.selectByGroup(groupid);
        }

        //向表格中填充结果
        ObservableList<Contact> items = FXCollections.observableArrayList(contacts);
       contactTable.setItems(items);

    }

    public void load() {
        load(0);
        bindProperty();

    }

    public void selectByGroup(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount()==2){
            load(groupsListView.getSelectionModel().getSelectedItem().getGroupid());
            bindProperty(); }
    }
    @SneakyThrows
    public void admin(ActionEvent actionEvent) {
        Stage mainStage = new Stage();
        mainStage.setScene(new Scene(ContactApplication.loadFxml("/admin.fxml").load()));
        mainStage.show();
    }
}
