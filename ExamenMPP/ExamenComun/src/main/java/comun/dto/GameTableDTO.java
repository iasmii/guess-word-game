package comun.dto;

public class GameTableDTO {
    private Long id;

    private UserDTO user;

    private Integer n;

    private Integer m;

    private BoxDTO[] boxes;

    private Integer score;

    private Integer time;

    private String litere, cuvinte;

    public GameTableDTO() {
    }

    public GameTableDTO(Long id, UserDTO user, Integer n, Integer m, BoxDTO[] boxes, Integer score) {
        this.id = id;
        this.user = user;
        this.n = n;
        this.m = m;
        this.boxes = boxes;
        this.score = score;
    }

    public GameTableDTO(Long id, UserDTO userDTO, Integer n, Integer m, BoxDTO[] boxes, Integer score, Integer time) {
        this.id = id;
        this.user = userDTO;
        this.n = n;
        this.m = m;
        this.boxes = boxes;
        this.score = score;
        this.time = time;
    }

    public GameTableDTO(Long id, UserDTO user, Integer n, Integer m, BoxDTO[] boxes, Integer score, String litere, String cuvinte) {
        this.id = id;
        this.user = user;
        this.n = n;
        this.m = m;
        this.boxes = boxes;
        this.score = score;
        this.litere=litere;
        this.cuvinte=cuvinte;
    }

    public GameTableDTO(Long id, UserDTO userDTO, Integer n, Integer m, BoxDTO[] boxes, Integer score, Integer time,String litere, String cuvinte) {
        this.id = id;
        this.user = userDTO;
        this.n = n;
        this.m = m;
        this.boxes = boxes;
        this.score = score;
        this.time = time;
        this.litere=litere;
        this.cuvinte=cuvinte;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public Integer getM() {
        return m;
    }

    public void setM(Integer m) {
        this.m = m;
    }

    public BoxDTO[] getBoxes() {
        return boxes;
    }

    public void setBoxes(BoxDTO[] boxes) {
        this.boxes = boxes;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getLitere() {
        return litere;
    }

    public void setLitere(String litere) {
        this.litere = litere;
    }

    public String getCuvinte() {
        return cuvinte;
    }

    public void setCuvinte(String cuvinte) {
        this.cuvinte = cuvinte;
    }
}
