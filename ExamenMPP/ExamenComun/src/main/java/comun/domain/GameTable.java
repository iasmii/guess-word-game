package comun.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "GameTables")
public class GameTable implements comun.domain.Entity<Long>{

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Integer n;

    private Integer m;

    @OneToMany(mappedBy = "gameTable", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Box> boxes;

    private Integer score;

    private Integer time;

    private String litere;
    private String cuvinte;

    public GameTable() {
    }

    public GameTable(User user, Integer n, Integer m) {
        this.user = user;
        this.score = 0;
        this.n = n;
        this.m = m;
        this.boxes = new ArrayList<>();
    }

    public GameTable(Long id, User user, Integer n, Integer m, Integer score) {
        this.id = id;
        this.user = user;
        this.n = n;
        this.m = m;
        this.score = score;
    }

    public GameTable(User user) {
        this.user = user;
    }

    public GameTable(Long id, User user, Integer n, Integer m, Integer score, Integer time) {
        this.id = id;
        this.user = user;
        this.n = n;
        this.m = m;
        this.score = score;
        this.time = time;
    }

    public GameTable(User user, Integer n, Integer m, String litere, String cuvinte) {
        this.user = user;
        this.score = 0;
        this.n = n;
        this.m = m;
        this.boxes = new ArrayList<>();
        this.litere=litere;
        this.cuvinte=cuvinte;
    }

    public GameTable(Long id, User user, Integer n, Integer m, Integer score, String litere, String cuvinte) {
        this.id = id;
        this.user = user;
        this.n = n;
        this.m = m;
        this.score = score;
        this.litere=litere;
        this.cuvinte=cuvinte;
    }

    public GameTable(Long id, User user, Integer n, Integer m, Integer score, Integer time, String litere, String cuvinte) {
        this.id = id;
        this.user = user;
        this.n = n;
        this.m = m;
        this.score = score;
        this.time = time;
        this.litere=litere;
        this.cuvinte=cuvinte;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
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

    @Override
    public String toString(){
        return "User: " + user.getUsername() + " " + "Score: " + score + " " + "Time: " + time;
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

    public void getCuvinte(String solutie) {
        this.cuvinte = solutie;
    }

//    @Override
//    public String toString() {
//        return "GameTable{" +
//                "id=" + id +
//                ", user=" + user +
//                ", n=" + n +
//                ", m=" + m +
//                ", boxes=" + boxes +
//                ", score=" + score +
//                '}';
//    }
}
