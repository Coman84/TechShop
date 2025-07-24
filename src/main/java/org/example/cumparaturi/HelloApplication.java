package org.example.cumparaturi;

import domain.cumparaturi;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repo.*;
import service.serv;

import java.io.IOException;

public class HelloApplication extends Application {
    private static MemoryRepository<cumparaturi> repo;
    protected static serv serv;

    public void start(Stage stage) throws IOException {
        try {
            repo=new SQLcumparaturi();
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        } catch (DuplicateIDException e) {
            throw new RuntimeException(e);
        }
        serv=new serv(repo);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("gestionare reteta");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
//public class HelloApplication extends Application {
//    private static MemoryRepository<cumparaturi> repo;
//    protected static serv serv;
//
//    public void start(Stage stage) throws IOException {
//        loadRepository();
//        serv = new serv(repo);
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
//        stage.setTitle("gestionare reteta");
//        stage.setScene(scene);
//        stage.show();
//    }
//    private void loadRepository() {
//        Properties prop = loadProperties("settings.properties");
//        try {
//            String repository = prop.getProperty("repository");
//            if (repository != null && repository.contains("sql")) {
//                repo = new SQLcumparaturi();
//            } else {
//                if (repository != null && repository.contains("txt")) {
//                    EntityConverter<cumparaturi> cumpConv = new CumparaturiConverter();
//                    repo = new TextFileRepository<>(prop.getProperty("Cumparaturi"), cumpConv);
//                } else {
//                    if (repository != null && repository.contains("bin")) {
//                        repo = new BinaryFileRepository<>(prop.getProperty("Cumparaturi"));
//                    } else {
//                        repo = new MemoryRepository<>();
//                    }
//                }
//            }
//        } catch (DuplicateIDException e) {
//            throw new RuntimeException(e);
//        } catch (RepositoryException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    private static Properties loadProperties(String fileNema){
//        Properties prop=new Properties();
//        try(FileInputStream fis=new FileInputStream(fileNema)){
//            prop.load(fis);
//        }
//        catch (FileNotFoundException e) {
//            throw new RuntimeException("Fișierul de setări nu a fost găsit: " + fileNema, e);
//        } catch (IOException e) {
//            throw new RuntimeException("Eroare la citirea fișierului de setări: " + fileNema, e);
//        }
//        return prop;
//    }
//    public static void main(String[] args) {
//        launch();
//    }
//}