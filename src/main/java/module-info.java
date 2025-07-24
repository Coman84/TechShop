module org.example.cumparaturi {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.xerial.sqlitejdbc;


    opens org.example.cumparaturi to javafx.fxml;
    exports org.example.cumparaturi;
}