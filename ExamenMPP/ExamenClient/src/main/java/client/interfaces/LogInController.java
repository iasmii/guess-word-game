package client.interfaces;

import comun.business.IObserver;
import comun.business.IService;
import comun.business.ExamenException;
import comun.domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInController {

    private IService service;

    @FXML
    private Button logInButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    void LogIn(ActionEvent event) {
        if(usernameField.getText().isEmpty() || passwordField.getText().isEmpty()){
            System.out.println("Please fill in all fields");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainPage.fxml"));
            Parent root = loader.load();

            MainPageController controller = loader.getController();

            User user = service.login(usernameField.getText(), passwordField.getText(), controller);
            System.out.println("Logged in as " + user.getUsername());

            controller.setController(service, user);

            Stage stage = (Stage) logInButton.getScene().getWindow();
            stage.close();

            Scene scene = new Scene(root, root.prefWidth(1), root.prefHeight(1));
            stage.setTitle(user.getUsername());
            stage.setScene(scene);
            stage.show();
        } catch(ExamenException e) {
            System.out.println("Error while logging in: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error while logging in");
            e.printStackTrace();
        }
    }

    public void setService(IService service) {
        this.service = service;
    }
}
