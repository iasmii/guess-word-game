package server.business;

import comun.business.IObserver;
import comun.business.IService;
import comun.business.ExamenException;
import comun.domain.*;
import server.repository.ICuvantRepository;
import server.repository.IGameTableRepository;
import server.repository.IUserRepository;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.StreamSupport;

public class Service implements IService{

    IUserRepository userRepository;

    IGameTableRepository gameTableRepository;

    ICuvantRepository cuvantRepository;

    private Map<Long, IObserver> loggedClients = new HashMap<>();

    public Service(IUserRepository userRepository, IGameTableRepository gameTableRepository,ICuvantRepository cuvantRepository){
        this.userRepository = userRepository;
        this.gameTableRepository = gameTableRepository;
        this.cuvantRepository=cuvantRepository;
    }

    @Override
    public User login(String username, String password, IObserver client) throws ExamenException {
        Optional<User> opUser = userRepository.findByUsername(username);
        if (opUser.isEmpty())
            throw new ExamenException("No user with that username");
        User user = opUser.get();
        if (Objects.equals(user.getPassword(), password)) {
            if(loggedClients.get(user.getId())!=null)
                throw new ExamenException("User already logged in.");
            loggedClients.put(user.getId(), client);
            return user;
        }
        else{
            throw new ExamenException("Incorrect password");
        }
    }

    @Override
    public void logout(User user, IObserver client) throws ExamenException {
        IObserver localClient = loggedClients.remove(user.getId());
        if (localClient == null)
            throw new ExamenException("User " + user.getUsername() +" not logged in.");
    }

    @Override
    public List<GameTable> getGameTables() throws ExamenException {
        return StreamSupport.stream(gameTableRepository.getAll().spliterator(), false)
                .toList();
    }

    @Override
    public GameTable startGame(User user) throws ExamenException {
        Random rand = new Random();
        int size=1;
        for(Cuvant c: cuvantRepository.getAll()) size++;
        Long idCuvant= rand.nextLong(size);
        if(idCuvant==0) idCuvant++;
        Optional<Cuvant> c=cuvantRepository.findOne(idCuvant);

        Integer n = 4, m = 1;
        GameTable gameTable = new GameTable(user, n, m,c.get().getLitere(), c.get().getCuvinte());

        String[] valoare = c.get().getCuvinte().split(",");

        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++) {
                gameTable.getBoxes().add(new Box(gameTable, i, j, valoare[i]));
            }
        }
        return gameTable;
    }

    @Override
    public void saveGameTable(GameTable gameTable) throws ExamenException {
        gameTableRepository.save(gameTable);
        notifyUser();
    }


    private final int defaultThreadsNo = 5;
    private void notifyUser() throws ExamenException{
        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);
                for(IObserver client : loggedClients.values()){
                    executor.execute(() -> {
                        try {
                            client.update();
                        } catch (ExamenException e) {
                            System.err.println("Error notifying agent " + e);
                        }
                    });
                }
                executor.shutdown();
    }
}
