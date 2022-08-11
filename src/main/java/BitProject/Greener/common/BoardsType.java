package BitProject.Greener.common;

public enum BoardsType {
    SHOP("분양 게시판"),
    COMMUNITY("소통 게시판");

    private final String value;
    BoardsType(String value){
        this.value=value;
    }
    public String getValue() {
        return value;
    }
}
