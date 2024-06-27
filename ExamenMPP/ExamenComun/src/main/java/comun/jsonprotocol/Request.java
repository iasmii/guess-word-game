package comun.jsonprotocol;

import comun.dto.GameTableDTO;
import comun.dto.UserDTO;

public class Request {

    private RequestType type;

    private UserDTO user;

    private GameTableDTO gameTable;

    private String[] data;

    public Request() {
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public GameTableDTO getGameTable() {
        return gameTable;
    }

    public void setGameTable(GameTableDTO gameTable) {
        this.gameTable = gameTable;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }
}
