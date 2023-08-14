import java.util.ArrayList;
import java.util.List;

public class Rule {
    private NonTerminal left;
    private List<Symb> right = new ArrayList<>();

    public Rule(NonTerminal gauch, List<Symb> driote) {
        this.left = gauch;
        this.right = driote;
    }

    public NonTerminal getLeft() {
        return left;
    }

    public void setLeft(NonTerminal left) {
        this.left = left;
    }

    public List<Symb> getRight() {
        return right;
    }

    public void setRight(List<Symb> right) {
        this.right = right;
    }


}
