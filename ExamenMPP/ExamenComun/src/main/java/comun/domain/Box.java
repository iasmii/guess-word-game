package comun.domain;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "Boxes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"gameTable_id", "row", "column"})
})
public class Box implements comun.domain.Entity<Long>{

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gameTable_id")
    private GameTable gameTable;

    private Integer row;

    private Integer column;

    private String value;

    public Box(){}

    public Box(GameTable gameTable, Integer row, Integer column) {
        this.gameTable = gameTable;
        this.row = row;
        this.column = column;
    }

    public Box(GameTable gameTable, Integer row, Integer column, String value) {
        this.gameTable = gameTable;
        this.row = row;
        this.column = column;
        this.value = value;
    }

    public Box(Long id, GameTable gameTable, Integer row, Integer column) {
        this.id = id;
        this.gameTable = gameTable;
        this.row = row;
        this.column = column;
    }

    public Box(Long id, GameTable gameTable, Integer row, Integer column, String value) {
        this.id = id;
        this.gameTable = gameTable;
        this.row = row;
        this.column = column;
        this.value = value;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public GameTable getGameTable() {
        return gameTable;
    }

    public void setGameTable(GameTable gameTable) {
        this.gameTable = gameTable;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Box{" +
                "row=" + row +
                ", column=" + column +
                ", value='" + value + '\'' +
                '}';
    }
}
