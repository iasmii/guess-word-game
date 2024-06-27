package comun.dto;

import comun.domain.*;

import java.util.ArrayList;
import java.util.List;

public class DTOUtils {

    public static User getUserFromDTO(UserDTO userDTO){
        return new User(userDTO.getId(), userDTO.getUsername(), userDTO.getPassword());
    }

    public  static UserDTO getDTOFromUser(User user){
        return new UserDTO(user.getId(), user.getUsername(), user.getPassword());
    }

    public static UserDTO getDTOFromUsernameAndPassword(String username, String password){
        return new UserDTO(username, password);
    }

    public static List<GameTable> getGameTablesFromDTO(GameTableDTO[] gameTableDTOS){
        List<GameTable> gameTables = new ArrayList<>();
        for(GameTableDTO gameTableDTO : gameTableDTOS){
            User user = getUserFromDTO(gameTableDTO.getUser());
            GameTable gameTable = new GameTable(gameTableDTO.getId(), user, gameTableDTO.getN(), gameTableDTO.getM(), gameTableDTO.getScore(), gameTableDTO.getTime(), gameTableDTO.getLitere(),gameTableDTO.getCuvinte());
            List<Box> boxes = new ArrayList<>();
            for(BoxDTO boxDTO : gameTableDTO.getBoxes()){
                boxes.add(new Box(boxDTO.getId(), gameTable, boxDTO.getRow(), boxDTO.getColumn(), boxDTO.getValue()));
            }
            gameTable.setBoxes(boxes);
            gameTables.add(gameTable);
        }
        return gameTables;
    }

    public static GameTableDTO[] getDTOFromGameTables(List<GameTable> gameTables){
        GameTableDTO[] gameTableDTOS = new GameTableDTO[gameTables.size()];
        for(int i = 0; i < gameTables.size(); i++){
            GameTable gameTable = gameTables.get(i);
            UserDTO userDTO = getDTOFromUser(gameTable.getUser());
            BoxDTO[] boxes = new BoxDTO[gameTable.getBoxes().size()];
            for(int j = 0; j < gameTable.getBoxes().size(); j++){
                Box b = gameTable.getBoxes().get(j);
                boxes[j] = new BoxDTO(b.getId(), b.getRow(), b.getColumn(), b.getValue());
            }
            gameTableDTOS[i] = new GameTableDTO(gameTable.getId(), userDTO, gameTable.getN(), gameTable.getM(), boxes, gameTable.getScore(), gameTable.getTime(),gameTable.getLitere(),gameTable.getCuvinte());
        }
        return gameTableDTOS;
    }

    public static GameTableDTO[] getDTOFromGameTable(GameTable gameTable) {
        GameTableDTO[] gameTableDTOS = new GameTableDTO[1];
        UserDTO userDTO = getDTOFromUser(gameTable.getUser());
        BoxDTO[] boxes = new BoxDTO[gameTable.getBoxes().size()];
        for(int j = 0; j < gameTable.getBoxes().size(); j++){
            Box b = gameTable.getBoxes().get(j);
            boxes[j] = new BoxDTO(b.getId(), b.getRow(), b.getColumn(), b.getValue());
        }
        gameTableDTOS[0] = new GameTableDTO(gameTable.getId(), userDTO, gameTable.getN(), gameTable.getM(), boxes, gameTable.getScore(),gameTable.getLitere(),gameTable.getCuvinte());
        return gameTableDTOS;
    }

    public static GameTable getGameTableFromDTO(GameTableDTO gameTable) {
        User user = getUserFromDTO(gameTable.getUser());
        GameTable gt = new GameTable(gameTable.getId(), user, gameTable.getN(), gameTable.getM(), gameTable.getScore(), gameTable.getTime(),gameTable.getLitere(),gameTable.getCuvinte());
        List<Box> boxes = new ArrayList<>();
        for(BoxDTO boxDTO : gameTable.getBoxes()){
            boxes.add(new Box(boxDTO.getId(), gt, boxDTO.getRow(), boxDTO.getColumn(), boxDTO.getValue()));
        }
        gt.setBoxes(boxes);
        return gt;
    }

    public static GameTableDTO getGameTableDTOFromGameTable(GameTable gameTable) {
        UserDTO userDTO = getDTOFromUser(gameTable.getUser());
        BoxDTO[] boxes = new BoxDTO[gameTable.getBoxes().size()];
        for(int j = 0; j < gameTable.getBoxes().size(); j++){
            Box b = gameTable.getBoxes().get(j);
            boxes[j] = new BoxDTO(b.getId(), b.getRow(), b.getColumn(), b.getValue());
        }
        return new GameTableDTO(gameTable.getId(), userDTO, gameTable.getN(), gameTable.getM(), boxes, gameTable.getScore(), gameTable.getTime(),gameTable.getLitere(),gameTable.getCuvinte());
    }
}
