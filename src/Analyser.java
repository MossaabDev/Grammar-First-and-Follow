import java.util.ArrayList;
import java.util.List;

public class Analyser {
    List<Symb> symbList = new ArrayList<>();
    List<Rule> ruleList = new ArrayList<>();
    public String getFirstsAndFollows(){
        String result = "";
        for (int i = 0; i< symbList.size(); i++){
            if (symbList.get(i) instanceof NonTerminal){
                first((NonTerminal) symbList.get(i));
            }
        }

        for (int i = 0; i< symbList.size(); i++){
            if (symbList.get(i) instanceof NonTerminal){

                if (ruleList.get(0).getLeft().getName().equals(symbList.get(i).getName())){
                    ((NonTerminal) symbList.get(i)).getFollows().add(new Terminal("$"));
                }

                follow((NonTerminal) symbList.get(i));

            }
        }
        result = getAllFirsts() + getAllFollows();
        return result;
    }

    private void first(NonTerminal symb) {
        for (int i = 0; i< ruleList.size(); i++){
            //System.out.println("rule"+i+" " + ruleList.get(i).getGauch().getName() + " -> " +ruleList.get(i).getDriote().get(0).getName());
            if (symb.getName().equals(ruleList.get(i).getLeft().getName())){
                for (int j = 0; j<ruleList.get(i).getRight().size(); j++){
                    if (ruleList.get(i).getRight().get(j) instanceof Terminal){
                        if (!symb.firstContains(ruleList.get(i).getRight().get(j).getName())){
                            symb.getFirsts().add(new Terminal(ruleList.get(i).getRight().get(j).getName()));

                        }
                        break;
                    }
                    else{
                        NonTerminal symb2 = new NonTerminal("");
                        symb2 = (NonTerminal) ruleList.get(i).getRight().get(j);
                        if (symb != symb2) {
                            first(symb2);
                            if (!symb2.getFirsts().isEmpty()) {
                                if (symb2.firstContains("eps") && j + 1 < ruleList.get(i).getRight().size()) {
                                    symb.firstAddAllNoEps(symb2.getFirsts());
                                } else {
                                    symb.firstAddAll(symb2.getFirsts());
                                    break;
                                }


                            }
                        }

                    }
                }
            }
        }

    }
    private void follow(NonTerminal symb) {


        for (int i = 0; i< ruleList.size(); i++){

            for (int j = 0; j<ruleList.get(i).getRight().size(); j++){
                if (symb.getName().equals(ruleList.get(i).getRight().get(j).getName())){
                    if (j + 1 < ruleList.get(i).getRight().size()){
                        for (int k = j +1; k < ruleList.get(i).getRight().size(); k++){
                            if (ruleList.get(i).getRight().get(k) instanceof Terminal ){
                                if (!symb.followContains(ruleList.get(i).getRight().get(k).getName())){
                                    symb.getFollows().add((Terminal) ruleList.get(i).getRight().get(k));
                                }
                                break;
                            }
                            else{
                                if (((NonTerminal)symbList.get(findSymb(ruleList.get(i).getRight().get(k).getName()))).firstContains("eps")){
                                    symb.followAddAllNoEps(((NonTerminal)symbList.get(findSymb(ruleList.get(i).getRight().get(k).getName()))).getFirsts());
                                }else {
                                    symb.followAddAllNoEps(((NonTerminal)symbList.get(findSymb(ruleList.get(i).getRight().get(k).getName()))).getFirsts());
                                    System.out.println("ura " + symb.getName() + " : " + ruleList.get(i).getRight().get(k).getName() + " : " + ((NonTerminal)symbList.get(findSymb(ruleList.get(i).getRight().get(k).getName()))).firstsToString());
                                    break;
                                }

                            }
                        }
                    }
                    if (j + 1 == ruleList.get(i).getRight().size() && !ruleList.get(i).getLeft().getName().equals(symb.getName())){
                        //symb.setFollowing(true);
                        int index = j;
                        while (symb.firstContains("eps") && j > 0){
                            System.out.println("ura" + symb.getName());
                            //if (((NonTerminal)symbList.get(findSymb(ruleList.get(i).getGauch().getName()))).isFollowing()){
                            follow((NonTerminal) symbList.get(findSymb(ruleList.get(i).getLeft().getName())));
                            //}

                            symb.followAddAllNoEps(((NonTerminal)symbList.get(findSymb(ruleList.get(i).getLeft().getName()))).getFollows());
                            j--;
                            if (symbList.get(findSymb(ruleList.get(i).getRight().get(j).getName())) instanceof Terminal)
                                break;
                            symb = (NonTerminal) symbList.get(findSymb(ruleList.get(i).getRight().get(j).getName()));



                        }
                        //if (((NonTerminal)symbList.get(findSymb(ruleList.get(i).getGauch().getName()))).isFollowing()){
                        follow((NonTerminal) symbList.get(findSymb(ruleList.get(i).getLeft().getName())));
                        //}
                        symb.followAddAllNoEps(((NonTerminal)symbList.get(findSymb(ruleList.get(i).getLeft().getName()))).getFollows());
                        j = index;
                        symb = (NonTerminal) symbList.get(findSymb(ruleList.get(i).getRight().get(j).getName()));
                    }
                }
            }
        }
        symb.setFollowing(false);
    }

    public int findSymb(String name){
        for (int i = 0; i < symbList.size(); i++){
            if (name.equals(symbList.get(i).getName())){
                return i;
            }
        }
        return -1;
    }

    public String getAllFollows(){
        String s = "";
        NonTerminal tempo = new NonTerminal("");
        for (int i =0; i< symbList.size(); i++){
            if (symbList.get(i) instanceof NonTerminal){
                s = s + "FOLLOW(" + symbList.get(i).getName() + ") : "+ ((NonTerminal) symbList.get(i)).followsToString();
                tempo = (NonTerminal) symbList.get(i);

                s = s + "\n";
            }
        }
        return s;
    }

    public String getAllFirsts() {
        String s = "";
        NonTerminal tempo = new NonTerminal("");
        for (int i =0; i< symbList.size(); i++){
            if (symbList.get(i) instanceof NonTerminal){
                s = s + "FIRST(" + symbList.get(i).getName() + ") : "+ ((NonTerminal) symbList.get(i)).firstsToString();
                tempo = (NonTerminal) symbList.get(i);

                s = s + "\n";
            }
        }
        return s;
    }
}
