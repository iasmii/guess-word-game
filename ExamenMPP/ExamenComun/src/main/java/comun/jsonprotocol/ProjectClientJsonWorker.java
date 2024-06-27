package comun.jsonprotocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import comun.business.IObserver;
import comun.business.IService;
import comun.business.ExamenException;
import comun.domain.GameTable;
import comun.domain.User;
import comun.dto.DTOUtils;
import comun.dto.UserDTO;
import comun.utils.LocalDateTimeTypeAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;

public class ProjectClientJsonWorker implements Runnable, IObserver{

    private IService server;

    private Socket connection;

    private BufferedReader input;

    private PrintWriter output;

    private Gson gsonFormatter;

    private volatile boolean connected;

    public ProjectClientJsonWorker(IService server, Socket connection) {
        this.server = server;
        this.connection = connection;
        gsonFormatter = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .create();
        try {
            output = new PrintWriter(connection.getOutputStream());
            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        while(connected){
            try{
                String requestLine = input.readLine();
                Request request = gsonFormatter.fromJson(requestLine, Request.class);
                Response response = handleRequest(request);
                if(response!= null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{
                Thread.sleep(200);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
        }catch (IOException e){
            System.out.println("Error "+e);
        }
    }

    public void update() throws ExamenException{
        sendResponse(JsonProtocolUtils.createUpdateResponse());
    }

    private Response handleRequest(Request request) {
        Response response = null;
        if(request.getType() == RequestType.LOGIN){
            UserDTO userDTO = request.getUser();
            try {
                User user = server.login(userDTO.getUsername(), userDTO.getPassword(), this);
                response = JsonProtocolUtils.createLoginResponse(user);
            } catch (ExamenException e) {
                connected = false;
                response = JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if(request.getType() == RequestType.LOGOUT){
            UserDTO userDTO = request.getUser();
            User user = DTOUtils.getUserFromDTO(userDTO);
            try {
                server.logout(user, this);
                connected = false;
                response = JsonProtocolUtils.createOkResponse();
            } catch (ExamenException e) {
                response = JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if(request.getType() == RequestType.GET_GAME_TABLES){
            try{
                response = JsonProtocolUtils.createGetGameTablesResponse(server.getGameTables());
            } catch (ExamenException e) {
                response = JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if(request.getType() == RequestType.START_GAME){
            try{
                User user = DTOUtils.getUserFromDTO(request.getUser());
                response = JsonProtocolUtils.createStartGameResponse(server.startGame(user));
            } catch (ExamenException e) {
                response = JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.SAVE_GAME){
            try{
                GameTable gameTable = DTOUtils.getGameTableFromDTO(request.getGameTable());
                server.saveGameTable(gameTable);
                response = JsonProtocolUtils.createOkResponse();
            } catch (ExamenException e) {
                response = JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        return response;
    }

    private void sendResponse(Response response){
        String responseString = gsonFormatter.toJson(response);
        System.out.println("Sending response " + responseString);
        synchronized (output){
            output.println(responseString);
            output.flush();
        }
    }
}
