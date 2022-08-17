package BitProject.Greener.common;

public enum BoardsType {
    MARKET("분양 게시판"),
    TALK("소통 게시판");

    private final String value;
    BoardsType(String value){
        this.value=value;
    }
    public String getValue() {
        return value;
    }
}
