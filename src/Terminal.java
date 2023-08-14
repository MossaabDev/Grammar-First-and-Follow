import java.util.ArrayList;
import java.util.List;

public class Terminal extends Symb{
    List<NonTerminal> first = new ArrayList<>();
    List<NonTerminal> follow = new ArrayList<>();
    public Terminal(String name) {
        super(name);
    }

    public List<NonTerminal> getFirst() {
        return first;
    }

    public void setFirst(List<NonTerminal> first) {
        this.first = first;
    }

    public List<NonTerminal> getFollow() {
        return follow;
    }

    public void setFollow(List<NonTerminal> follow) {
        this.follow = follow;
    }



}
