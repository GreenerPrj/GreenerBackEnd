package BitProject.Greener.domain.boards;

public enum BoardType {

    COMMUNITY("커뮤니티 게시판")
    , SHOP("분양게시판")
    ;

    private final String label;

    BoardType(String label){this.label = label;}
}
