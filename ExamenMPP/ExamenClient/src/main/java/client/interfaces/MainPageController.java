package client.interfaces;

import comun.business.IService;
import comun.business.ExamenException;
import comun.domain.Box;
import comun.domain.GameTable;
import comun.domain.User;
import comun.utils.Observable;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MainPageController implements Initializable, comun.business.IObserver {

    private IService service;

    private User user;

    private GameTable myGameTable;

    private List<GameTable> gameTables;

    private ObservableList<GameTable> listGT = FXCollections.observableArrayList();

    @FXML
    private ListView<GameTable> scoreboardList;

    @FXML
    private Button logOutButton;

    @FXML
    private Button ghicesteButton;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private VBox gameVBox;

    @FXML
    private Label scoreLabel;
    @FXML
    private Label litereLabel;
    @FXML
    private TextField cuvantText;

    private final long startTimer = System.currentTimeMillis();

    private int tries;

    private String solutie;
    public void setController(IService service, User user){
        this.service = service;
        this.user = user;
        welcomeLabel.setText("Welcome, " + user.getUsername());
        try{
            update();
            myGameTable = service.startGame(user);

            litereLabel.setText(myGameTable.getLitere());
            tries=0;
            solutie=myGameTable.getCuvinte();

            myGameTable.getBoxes().forEach(System.out::println);
            gameVBox.getChildren().clear();
            scoreLabel.setText("Score: " + myGameTable.getScore());
            gameVBox.getChildren().add(createGameGrid(myGameTable.getN(), myGameTable.getM()));
            VBox.setVgrow(gameVBox.getChildren().get(0), javafx.scene.layout.Priority.ALWAYS);
            VBox.setMargin(gameVBox.getChildren().get(0), new javafx.geometry.Insets(20, 20, 20, 20));
        }catch (Exception e) {
            System.out.println("Error while setting controller");
            e.printStackTrace();
        }
    }

    @Override
    public void update() throws ExamenException{
        Platform.runLater(()->{
            try{
                gameTables =  service.getGameTables();
                List<GameTable> sorted = StreamSupport.stream(gameTables.spliterator(), false)
                        .sorted((g1, g2) -> g2.getScore() - g1.getScore())
                        .toList();
                listGT = FXCollections.observableArrayList(sorted);
                scoreboardList.setItems(listGT);
            }catch (Exception e){
                System.out.println("Error while updating");
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scoreboardList.setItems(listGT);
    }


    @FXML
    void gameLogic(ActionEvent event){
        if(tries<4){
            tries++;
            String cuvant=cuvantText.getText();
            boolean updateScor=false;
            int scorMax=-1;
            for(Box box:myGameTable.getBoxes()){
                if(Objects.equals(box.getValue(), cuvant)){
                    myGameTable.setScore(myGameTable.getScore() + cuvant.length());
                    scoreLabel.setText("Score: " + myGameTable.getScore());
                    box.setValue("");
                    updateScor=true;
                }
                if(box.getValue()!=""){
                    int scor=0;
                    //StringBuilder newValue= new StringBuilder();
                    for(int i=0; i<box.getValue().length(); i++){
                        if(i<cuvant.length()){
                            if(box.getValue().charAt(i)==cuvant.charAt(i)){
                                scor++;
                                //newValue.append(" ");
                            }
                        }
                    }
                    if(scor>scorMax){
                        scorMax=scor;
                    }
                    //box.setValue(newValue.toString());
                }
                if(updateScor){
                    break;
                }
            }
            if(!updateScor){
                myGameTable.setScore(myGameTable.getScore() + scorMax);
                scoreLabel.setText("Score: " + myGameTable.getScore());
            }
        }
        else{
            Long seconds = (System.currentTimeMillis() - startTimer) / 1000;
            System.out.println("Game over! You scored: " + myGameTable.getScore() + " in " + seconds + " seconds");
            myGameTable.setTime(seconds.intValue());
            showMessage(solutie);
            try {
                service.saveGameTable(myGameTable);
            } catch (Exception e) {
                System.out.println("Error while saving game table");
            }
        }
    }

    void showMessage(String text){
        Alert message=new Alert(Alert.AlertType.INFORMATION);
        message.setTitle("Gata jocul!");
        message.setContentText("Cuvintele erau: "+text);
        message.showAndWait();
    }

    private GridPane createGameGrid(Integer rows, Integer cols){
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: #000000;");
        for(Box box: myGameTable.getBoxes()){
            int i = box.getRow();
            int j = box.getColumn();
            Button button = new Button();
            button.setMaxHeight(Double.MAX_VALUE);
            button.setMaxWidth(Double.MAX_VALUE);
            button.setUserData(i + "," + j);
            //button.setOnMouseClicked(event -> gameLogic(event));
            GridPane.setConstraints(button, j, i);
            gridPane.add(button, j, i);
            GridPane.setVgrow(button, javafx.scene.layout.Priority.ALWAYS);
            GridPane.setHgrow(button, javafx.scene.layout.Priority.ALWAYS);
        };
        return gridPane;
    }

    @FXML
    void logOut(ActionEvent event) {
        try {
            service.logout(user, this);
            System.out.println("Logged out");
            Stage stage = (Stage) logOutButton.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogIn.fxml"));
            Parent root = loader.load();

            LogInController controller = loader.getController();
            controller.setService(service);

            Scene scene = new Scene(root);
            stage.setTitle("Log In");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.out.println("Error while logging out");
            e.printStackTrace();
        }
    }
}