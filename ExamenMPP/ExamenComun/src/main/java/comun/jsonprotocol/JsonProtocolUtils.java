package comun.jsonprotocol;

import comun.domain.GameTable;
import comun.domain.User;
import comun.dto.DTOUtils;

import java.util.List;

public class JsonProtocolUtils {

    public static Response createOkResponse(){
        Response resp = new Response();
        resp.setType(ResponseType.OK);
        return resp;
    }

    public static Response createErrorResponse(String errorMessage){
        Response resp = new Response();
        resp.setType(ResponseType.ERROR);
        resp.setErrorMessage(errorMessage);
        return resp;
    }

    public static  Response createUpdateResponse(){
        Response resp = new Response();
        resp.setType(ResponseType.UPDATE);
        return resp;
    }

    public static Request createLoginRequest(String username, String password) {
        Request req = new Request();
        req.setType(RequestType.LOGIN);
        req.setUser(DTOUtils.getDTOFromUsernameAndPassword(username, password));
        return req;
    }

    public static Response createLoginResponse(User user) {
        Response resp = new Response();
        resp.setType(ResponseType.OK);
        resp.setUser(DTOUtils.getDTOFromUser(user));
        return resp;
    }

    public static Request createLogoutRequest(User user) {
        Request req = new Request();
        req.setType(RequestType.LOGOUT);
        req.setUser(DTOUtils.getDTOFromUser(user));
        return req;
    }

    public static Request createGetGameTablesRequest() {
        Request req = new Request();
        req.setType(RequestType.GET_GAME_TABLES);
        return req;
    }

    public static Response createGetGameTablesResponse(List<GameTable> gameTables) {
        Response resp = new Response();
        resp.setType(ResponseType.OK);
        resp.setGameTables(DTOUtils.getDTOFromGameTables(gameTables));
        return resp;
    }

    public static Request createStartGameRequest(User user) {
        Request req = new Request();
        req.setType(RequestType.START_GAME);
        req.setUser(DTOUtils.getDTOFromUser(user));
        return req;
    }

    public static Response createStartGameResponse(GameTable gameTable) {
        Response resp = new Response();
        resp.setType(ResponseType.OK);
        resp.setGameTables(DTOUtils.getDTOFromGameTable(gameTable));
        return resp;
    }

    public static Request createSaveGameTableRequest(GameTable gameTable) {
        Request req = new Request();
        req.setType(RequestType.SAVE_GAME);
        req.setGameTable(DTOUtils.getGameTableDTOFromGameTable(gameTable));
        return req;
    }
}
