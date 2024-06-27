package comun.business;

import comun.domain.*;

import java.util.List;

public interface IService {

    User login(String username, String password, IObserver client) throws ExamenException;

    void logout(User user,IObserver client) throws ExamenException;

    List<GameTable> getGameTables() throws ExamenException;

    GameTable startGame(User user) throws ExamenException;

    void saveGameTable(GameTable gameTable) throws ExamenException;
}
