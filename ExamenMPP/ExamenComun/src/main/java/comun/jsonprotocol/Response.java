package comun.jsonprotocol;

import comun.dto.GameTableDTO;
import comun.dto.UserDTO;

public class Response {

    private ResponseType type;

    private UserDTO user;

    private GameTableDTO[] gameTables;

    private String errorMessage;

    public Response() {
    }

    public ResponseType getType() {
        return type;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

    public GameTableDTO[] getGameTables() {
        return gameTables;
    }

    public void setGameTables(GameTableDTO[] gameTables) {
        this.gameTables = gameTables;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
