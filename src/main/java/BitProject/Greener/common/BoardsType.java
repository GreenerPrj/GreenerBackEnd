package BitProject.Greener.common;

public enum BoardsType {

    COMMUNITY("커뮤니티 게시판")
    , SHOP("분양게시판")
    ;

    private final String label;

    BoardsType(String label){this.label = label;}
}
