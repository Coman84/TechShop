package org.example.cumparaturi;

import domain.cumparaturi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import repo.DuplicateIDException;
import repo.RepositoryException;
import service.serv;

public class HelloController {
    public VBox mainMenu;
    public VBox afisare;
    public ListView OrdonareListView;
    public VBox filtrate;
    public TextField minField;
    public TextField maxField;
    public ListView FiltrareListView;
    public VBox cerinta3;
    public TextField idField;
    public ListView pretfinal;
    public ListView produseListView;
    public VBox adaugare;
    public TextField marcaField,numeField,pretField,cantitateField;
    public Label mesaj;
    public VBox delete;
    public TextField idDeleteField;
    public Button Stergere_produs;
    public Label mesajDelete;
    public VBox filtrare2;
    public TextField cautaField;
    public ListView Filtrate2ListView;
    private serv serv=HelloApplication.serv;
    public HelloController(){}
    public void setService(serv service){
        this.serv=service;
    }
    int total=0;

    @FXML
    private Label welcomeText;

    private void toggleVisibility(VBox menuToShow) {
        mainMenu.setVisible(false);
        afisare.setVisible(false);
        filtrate.setVisible(false);
        cerinta3.setVisible(false);
        adaugare.setVisible(false);
        delete.setVisible(false);
        filtrare2.setVisible(false);

        menuToShow.setVisible(true);
    }
    @FXML
    public void openafisare(ActionEvent actionEvent) {
        toggleVisibility(afisare);}
    public void openFiltrare(ActionEvent actionEvent) {
        toggleVisibility(filtrate);}
    public void goBackMenu(ActionEvent actionEvent) {
        toggleVisibility(mainMenu);}
    public void openCerinta3(ActionEvent actionEvent) {
        toggleVisibility(cerinta3);}
    public void openAdd(ActionEvent actionEvent) {
        toggleVisibility(adaugare);
    }
    public void openDelete(ActionEvent actionEvent) {
        toggleVisibility(delete);
    }
    public void openFiltrare2(ActionEvent actionEvent) {
        toggleVisibility(filtrare2);
    }

    private void showError(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(s);
        alert.showAndWait();
    }

    public void handleaff(ActionEvent actionEvent) {
        if (serv == null) {
            showError("Serviciul nu este inițializat.");
            return;
        }
        try {
            String rezultat = serv.crescator();

            if (rezultat.isEmpty()) {
                showError("Lista este goală.");
                return;
            }
            ObservableList<String> data = FXCollections.observableArrayList(rezultat.split("\n"));
            OrdonareListView.setItems(data);

        } catch ( RepositoryException e) {
            showError("Eroare la calcularea cerinței 1: " + e.getMessage());
        }
    }



    public void handleFiltrare(ActionEvent actionEvent) {
        String min = minField.getText();
        String max = maxField.getText();
        if (serv == null) {
            showError("Serviciul nu este inițializat.");
            return;
        }
        try {
            String rezultat = serv.Filtrare(min,max);

            if (rezultat.isEmpty()) {
                showError("Lista este goală.");
                return;
            }

            ObservableList<String> data = FXCollections.observableArrayList(rezultat.split("\n"));
            FiltrareListView.setItems(data);

        } catch ( RepositoryException e) {
            showError("Eroare la calcularea cerinței 1: " + e.getMessage());
        }
    }

    public void refreshFiltrare(ActionEvent actionEvent) {
        FiltrareListView.getItems().clear();
        ObservableList<String>data=FXCollections.observableArrayList();
        FiltrareListView.setItems(data);
    }


    public void handelCumpara(ActionEvent actionEvent) {
        try {
            if (serv == null) {
                showError("Serviciul nu este inițializat.");
                return;
            }
            String rez= serv.aff();
            if (rez.isEmpty()) {
                showError("Lista este goală.");
                return;
            }
            ObservableList<String> data = FXCollections.observableArrayList(rez.split("\n"));
            produseListView.setItems(data);

            int id = Integer.parseInt(idField.getText());
            cumparaturi produs = serv.get(id);
            if (produs == null) {
                showError("ID-ul nu există.");
                return;
            }
            if (produs.getCantitate() <= 0) {
                showError("Cantitate insuficientă pentru produsul cu ID " + id);
                return;
            }
            serv.scadere(id);
            total += produs.getPret();

            ObservableList<String> preturi = pretfinal.getItems();
            preturi.clear();
            preturi.add("Total: " + total + " lei");
            pretfinal.setItems(preturi);
            idField.clear();

        } catch (NumberFormatException e) {
            showError("ID-ul trebuie să fie un număr valid.");
        } catch (RepositoryException e) {
            showError("Eroare la procesarea produsului: " + e.getMessage());
        }
    }

    public void refreshAff(ActionEvent actionEvent) throws RepositoryException {
        produseListView.getItems().clear();
        ObservableList<String> data = FXCollections.observableArrayList(serv.aff().split("\n"));
        produseListView.setItems(data);
    }

    public void handleAdaugare(ActionEvent actionEvent) {
        try{
            if(marcaField.getText().isEmpty() || numeField.getText().isEmpty() || pretField.getText().isEmpty() || cantitateField.getText().isEmpty()){
                showError("nu sunt completate toate campurile obligatorii.");
            }
            else {
                int id = serv.id_next() + 1;
                String marca = marcaField.getText();
                String nume = numeField.getText();
                int pret = Integer.parseInt(pretField.getText());
                int cantitate = Integer.parseInt(cantitateField.getText());

                cumparaturi c = new cumparaturi(id, marca, nume, pret, cantitate);
                serv.add(c);
                mesaj.setText("Produs adaugat cu succes");
                mesaj.setStyle("-fx-text-fill: green;");
                marcaField.clear();
                numeField.clear();
                pretField.clear();
                cantitateField.clear();
            }
        } catch (RepositoryException | DuplicateIDException e) {
            mesaj.setText("Eroare la adăugare.");
            mesaj.setStyle("-fx-text-fill: red;");
        }
    }

    public void handleRefresh(ActionEvent actionEvent) {
        marcaField.clear();
        numeField.clear();
        pretField.clear();
        cantitateField.clear();
        mesaj.setText("Formular resetat.");
        mesaj.setStyle("-fx-text-fill: blue;");
    }

    public void handlestegere(ActionEvent actionEvent) {
        try{
            String idtxt=idDeleteField.getText();
            if(idtxt==null){
                mesajDelete.setText("Id ul este obligatoriu");
                mesajDelete.setStyle("-fx-text-fill: red;");
                return;
            }
            int id = Integer.parseInt(idtxt);
            cumparaturi c = serv.get(id);
            if(c==null){
                mesajDelete.setText("Nu exista un produs cu acest id");
            }
            else {
                serv.remove(id);
                mesajDelete.setText("Produs sters cu succes");
            }
            idDeleteField.clear();
        } catch (RepositoryException e) {
            showError("Stergere failed");
        }
    }

    public void handleFiltrare2(ActionEvent actionEvent) {
        String search = cautaField.getText();
        if(search==null){
            showError("Serviciul nu este actualizat");
            return;
        }
        try{
            String rez=serv.filtrare2(search);
            if(rez==""){
                showError("Nu s-a gasit nici un produs de acest tip");
                return;
            }
            ObservableList<String> data = FXCollections.observableArrayList(rez.split("\n"));
            Filtrate2ListView.setItems(data);
        }catch (RepositoryException e){
            showError("Eroare la filtrare: " + e.getMessage());
        }
    }
}