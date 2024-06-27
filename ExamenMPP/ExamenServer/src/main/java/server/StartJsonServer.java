package server;

import com.sun.jdi.LongValue;
import comun.business.ExamenException;
import comun.business.IService;
import comun.utils.AbstractServer;
import comun.utils.ProjectJsonConcurrentServer;
import comun.utils.ServerException;
import server.business.Service;
import server.repository.*;
import comun.domain.User;

import java.io.IOException;
import java.util.Properties;

public class StartJsonServer {
    private static int dafaultPort = 30053;
    public static void main(String[] args) throws ExamenException {
        Properties serverProps = new Properties();
        try{
            serverProps.load(StartJsonServer.class.getResourceAsStream("/examenserver.properties"));
            System.out.println("Server properties set.");
            serverProps.list(System.out);
        } catch (IOException e){
            System.err.println("Cannot find examenserver.properties " + e);
            return;
        }
        IUserRepository userRepo = new UserHibernateRepository();
        GameTableHibernateRepository gameRepo = new GameTableHibernateRepository();
        ICuvantRepository cuvantRepository=new CuvantHibernateRepository();
        userRepo.getAll().forEach(user -> System.out.println(user.toString()));

        //test
//        User a = userRepo.findByUsername("a").get();
//        User b = userRepo.findByUsername("b").get();
//        GameTable gameTable = new GameTable(a, 3, 3);
//        GameTable gameTable1 = new GameTable(a, 3, 3);
//        GameTable gameTable2 = new GameTable(b, 3, 3);
//        gameTable1.setScore(10);
//        gameTable2.setScore(5);
//        gameRepo.save(gameTable);
//        gameRepo.save(gameTable1);
//        gameRepo.save(gameTable2);


        IService projectServerImpl = new Service(userRepo, gameRepo,cuvantRepository);
        //projectServerImpl.startGame(new User(Long.valueOf(1),"a","a"));
        int projectServerPort = dafaultPort;
        try{
            projectServerPort = Integer.parseInt(serverProps.getProperty("examen.server.port"));
        }catch(NumberFormatException nef){
            System.err.println("Wrong Port Number" + nef.getMessage());
            System.err.println("Using default port " + dafaultPort);
        }
        AbstractServer server = new ProjectJsonConcurrentServer(projectServerPort, projectServerImpl);
        try{
            server.start();
        }catch (ServerException e){
            System.err.println("Error starting the server" + e.getMessage());
        }

    }
}
