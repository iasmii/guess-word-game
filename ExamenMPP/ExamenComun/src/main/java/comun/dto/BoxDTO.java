package comun.dto;

public class BoxDTO {

    private Long id;

    private Integer row;

    private Integer column;

    private String value;

    public BoxDTO() {
    }

    public BoxDTO(Integer row, Integer column, String value) {
        this.row = row;
        this.column = column;
        this.value = value;
    }

    public BoxDTO(Long id, Integer row, Integer column, String value) {
        this.id = id;
        this.row = row;
        this.column = column;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
