import java.util.ArrayList;
import java.util.List;

public class NonTerminal extends Symb{
    private List<Terminal> firsts = new ArrayList<>();
    private List<Terminal> follows = new ArrayList<>();
    private boolean following = false;
    public NonTerminal(String name) {
        super(name);
    }

    public List<Terminal> getFirsts() {
        return firsts;
    }

    public void setFirsts(List<Terminal> firsts) {
        this.firsts = firsts;
    }

    public List<Terminal> getFollows() {
        return follows;
    }

    public void setFollows(List<Terminal> follows) {
        this.follows = follows;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    public String firstsToString(){
        String s = "";
        for (int i = 0; i<firsts.size(); i++){
            s = s+ firsts.get(i).getName() + ",";

        }
        return s;
    }

    public String followsToString(){
        String s = "";
        for (int i = 0; i<follows.size(); i++){
            s = s+ follows.get(i).getName() + ",";

        }
        return s;
    }
    //checks if list of firsts of this symbol contains a symbol s
    public boolean firstContains(String s){
        for (int i = 0; i< firsts.size(); i++){
            if (firsts.get(i).getName().equals(s)){
                return true;
            }
        }
        return false;
    }
    //checks if list of follows of this symbol contains a symbol s
    public boolean followContains(String s){
        for (int i = 0; i< follows.size(); i++){
            if (follows.get(i).getName().equals(s)){
                return true;
            }
        }
        return false;
    }

    public boolean firstAddAll(List<Terminal> symbs){
        int a = 0;
        for (int i = 0; i<symbs.size(); i++){
            if (firstContains(symbs.get(i).getName())){
                a++;
            }else{
                firsts.add((Terminal)symbs.get(i));
            }
        }
        if (a == symbs.size()){
            return true;
        }
        return false;
    }
    //add all symballs in firsts list except for epselon
    public boolean firstAddAllNoEps(List<Terminal> symbs){
        int a = 0;
        for (int i = 0; i<symbs.size(); i++){
            if (firstContains(symbs.get(i).getName())){
                a++;
            }else{
                if (!symbs.get(i).getName().equals("eps")){
                    firsts.add((Terminal)symbs.get(i));
                }

            }
        }
        if (a == symbs.size()){
            return true;
        }
        return false;
    }
    //add all symballs in follows list except for epselon
    public boolean followAddAllNoEps(List<Terminal> symbs){
        int a = 0;
        for (int i = 0; i<symbs.size(); i++){
            if (followContains(symbs.get(i).getName())){
                a++;
            }else{
                if (!symbs.get(i).getName().equals("eps")){
                    follows.add(symbs.get(i));
                }

            }
        }
        if (a == symbs.size()){
            return true;
        }
        return false;
    }
}
