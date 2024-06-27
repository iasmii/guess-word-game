package comun.jsonprotocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import comun.business.*;
import comun.domain.GameTable;
import comun.domain.User;
import comun.dto.*;
import comun.dto.DTOUtils;
import comun.utils.LocalDateTimeTypeAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class ProjectServicesJsonProxy implements IService{

    private String host;

    private int port;

    private IObserver client;

    private BufferedReader input;

    private PrintWriter output;

    private Gson gsonFormatter;

    private Socket connection;

    private BlockingQueue<Response> qresponses;

    private volatile boolean finished;

    public ProjectServicesJsonProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<Response>();
    }

    private void initializeConnection() throws ExamenException {
        try {
            gsonFormatter = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                    .create();
            connection = new Socket(host, port);
            output = new PrintWriter(connection.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            finished = false;
            startReader();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            System.out.println("Error " + e);
        }
    }

    private void startReader(){
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private void sendRequest(Request request) throws ExamenException{
        String reqLine = gsonFormatter.toJson(request);
        try{
            output.println(reqLine);
            output.flush();
        }catch (Exception e){
            throw new ExamenException("Error sending object " + e);
        }
    }

    private Response readResponse() throws ExamenException{
        Response response = null;
        try{
            response = qresponses.take();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public User login(String username, String password, IObserver client) throws ExamenException {
        initializeConnection();
        Request req = JsonProtocolUtils.createLoginRequest(username, password);
        sendRequest(req);
        Response response = readResponse();
        if(response.getType() == ResponseType.OK){
            User user = DTOUtils.getUserFromDTO(response.getUser());
            this.client = client;
            return user;
        }
        if(response.getType() == ResponseType.ERROR){
            closeConnection();
            String err = response.getErrorMessage();
            throw new ExamenException(err);
        }
        return null;
    }

    @Override
    public void logout(User user, IObserver client) throws ExamenException {
        Request req = JsonProtocolUtils.createLogoutRequest(user);
        sendRequest(req);
        Response response = readResponse();
        if(response.getType() == ResponseType.ERROR){
            String err = response.getErrorMessage();
            throw new ExamenException(err);
        }
        closeConnection();
    }

    @Override
    public List<GameTable> getGameTables() throws ExamenException {
        Request req = JsonProtocolUtils.createGetGameTablesRequest();
        sendRequest(req);
        Response response = readResponse();
        if(response.getType() == ResponseType.OK){
            return DTOUtils.getGameTablesFromDTO(response.getGameTables());
        }
        if(response.getType() == ResponseType.ERROR){
            String err = response.getErrorMessage();
            throw new ExamenException(err);
        }
        return null;
    }

    @Override
    public GameTable startGame(User user) throws ExamenException {
        Request req = JsonProtocolUtils.createStartGameRequest(user);
        sendRequest(req);
        Response response = readResponse();
        if(response.getType() == ResponseType.OK){
            return DTOUtils.getGameTableFromDTO(response.getGameTables()[0]);
        }
        if(response.getType() == ResponseType.ERROR){
            String err = response.getErrorMessage();
            throw new ExamenException(err);
        }
        return null;
    }

    @Override
    public void saveGameTable(GameTable gameTable) throws ExamenException {
        Request req = JsonProtocolUtils.createSaveGameTableRequest(gameTable);
        sendRequest(req);
        Response response = readResponse();
        if(response.getType() == ResponseType.ERROR){
            String err = response.getErrorMessage();
            throw new ExamenException(err);
        }
    }

    private boolean isUpdate(Response response){
        return response.getType() == ResponseType.UPDATE;
    }

    private void handleUpdate(Response response) {
        if(response.getType() == ResponseType.UPDATE){
            System.out.println("Update received");
            try{
                client.update();
            }catch (ExamenException e){
                e.printStackTrace();
            }
        }
    }

    private class ReaderThread implements Runnable{
        public void run(){
            while(!finished){
                try{
                    String responseLine = input.readLine();
                    System.out.println("response received " + responseLine);
                    Response response = gsonFormatter.fromJson(responseLine, Response.class);
                    if(isUpdate(response)){
                        handleUpdate(response);
                    }else{
                        try{
                            qresponses.put(response);
                            if(response.getType() == ResponseType.ERROR){
                                Thread.sleep(10);
                            }
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }catch (IOException e){
                    System.out.println("Reading error " + e);
                }
            }
        }
    }
}
