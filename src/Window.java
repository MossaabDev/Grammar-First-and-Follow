import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Window extends JFrame{
    private JPanel mainPanel;
    private JPanel symbsPanel;
    private JPanel rulesPanel;
    Box vBox = Box.createVerticalBox();
    Box hBox = Box.createHorizontalBox();
    Box vBoxR = Box.createVerticalBox();
    Box hBoxR = Box.createHorizontalBox();
    JTextField charField = new JTextField();
    JTextField rulesField1 = new JTextField();
    JTextField rulesField2 = new JTextField();
    List<JTextField> textFields1 = new ArrayList<>();
    List<JTextField> textFields2 = new ArrayList<>();
    JLabel charsLabel = new JLabel();
    JButton addBtnSymb = new JButton("add");
    JButton addBtnRule = new JButton("add");
    JButton exeBtn = new JButton("execute");
    private String[] symbType = {"t", "n_t"};
    private JComboBox symbCombo = new JComboBox(symbType);
    String string1;
    String string2;
    List<JTextField> textFields = new ArrayList<>();
    List<JComboBox> comboBoxes = new ArrayList<>();
    Analyser analyser = new Analyser();
    int and = 1;


    public Window() throws HeadlessException {
        symbsPanel.setPreferredSize(new Dimension(200, JFrame.MAXIMIZED_VERT));
        symbsPanel.setBackground(Color.GRAY);
        rulesPanel.setBackground(Color.WHITE);
        vBox.add(new JLabel("symbls"));
        vBoxR.add(new JLabel("Rules"));
        vBox.add(Box.createVerticalStrut(20));
        vBoxR.add(Box.createVerticalStrut(20));
        symbsPanel.add(vBox);
        rulesPanel.add(vBoxR);
        addSymbLayout();
        addRulesLayout();

        addBtnSymb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSymbLayout();
                addBtnSymb.setText("ura");
                addBtnSymb.setText("add");
            }
        });

        addBtnRule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRulesLayout();
                addBtnRule.setText("ura");
                addBtnRule.setText("add");
            }
        });

        exeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i= 0; i < textFields2.size(); i++){
                    textFields2.get(i).setText(textFields2.get(i).getText() + " ");
                }
                analyser.symbList = initSymbs();
                analyser.ruleList = initRules();
                String reslut = analyser.getFirstsAndFollows();
                JOptionPane.showConfirmDialog(null, reslut, "result", JOptionPane.DEFAULT_OPTION);

            }
        });


        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void addRulesLayout() {
        JTextField rulesField1 = new JTextField();
        JTextField rulesField2 = new JTextField();
        JLabel sahm = new JLabel("->");
        rulesField1.setPreferredSize(new Dimension(60, 20));
        rulesField2.setPreferredSize(new Dimension(60, 20));
        hBoxR = Box.createHorizontalBox();
        hBoxR = Box.createHorizontalBox();
        Box hBoxR2 = Box.createHorizontalBox();
        hBoxR.add(rulesField1);
        textFields1.add(rulesField1);
        hBoxR.add(Box.createHorizontalStrut(10));
        hBoxR.add(sahm);
        hBoxR.add(Box.createHorizontalStrut(10));
        hBoxR.add(rulesField2);
        textFields2.add(rulesField2);
        vBoxR.add(hBoxR);
        vBoxR.add(Box.createVerticalStrut(20));
        hBoxR2.add(addBtnRule);
        hBoxR2.add(Box.createHorizontalStrut(10));
        hBoxR2.add(exeBtn);
        vBoxR.add(hBoxR2);
        vBoxR.add(Box.createVerticalStrut(20));
        rulesPanel.add(vBoxR);

    }

    private void addSymbLayout() {
        JTextField charField = new JTextField();
        charField.setPreferredSize(new Dimension(60, 20));
        JComboBox symbCombo = new JComboBox(symbType);
        symbCombo.setPreferredSize(new Dimension(60, 20));
        hBox = Box.createHorizontalBox();
        hBox = Box.createHorizontalBox();
        hBox.add(symbCombo);
        comboBoxes.add(symbCombo);
        hBox.add(Box.createHorizontalStrut(10));
        hBox.add(charField);
        textFields.add(charField);
        vBox.add(hBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(addBtnSymb);
        vBox.add(Box.createVerticalStrut(20));
        symbsPanel.add(vBox);
    }

    public List<Symb> initSymbs(){
        List<Symb> symbList = new ArrayList<>();
        for (int i = 0; i<textFields.size(); i++){
            if (testSymb(symbList, textFields.get(i).getText())){
                err("overwrite");
            }else {
                if (comboBoxes.get(i).getSelectedItem().equals("t")){
                    symbList.add(new Terminal(textFields.get(i).getText()));
                }else{
                    symbList.add(new NonTerminal(textFields.get(i).getText()));
                }
            }
        }
        return symbList;
    }

    private void err(String s) {
        System.out.println(s);
        int input = JOptionPane.showOptionDialog(null, s, "err", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null,null);
        if (input == JOptionPane.OK_OPTION){
            this.dispose();
            new Window();
        }
    }

    public List<Rule> initRules(){
        List<Rule> rulesList = new ArrayList<>();
        for (int i = 0; i<textFields1.size(); i++){
            if (textFields1.get(i).getText().equals("") || textFields2.get(i).getText().equals("")){
                err("empty");
            }
            else{
                string1 = textFields1.get(i).getText();
                string2 = textFields2.get(i).getText();
                while(and == 1) {

                    rulesList.add(parcourer(i));
                }
                textFields1.get(i).setText(string1);
                textFields2.get(i).setText(string2);
                and = 1;

            }


        }

        return rulesList;
    }

    public boolean testSymb(List<Symb> symbList, String s){
        for (int i = 0; i<symbList.size(); i++){
            if (symbList.get(i).getName().equals(s)){
                return true;
            }
        }
        return false;
    }

    private Rule parcourer(int n) {
        String s1 = textFields1.get(n).getText();
        String s2 = textFields2.get(n).getText();
        char[] c = s2.toCharArray();
        NonTerminal left = new NonTerminal("");
        List<Symb> right = new ArrayList<>();
        if (testNonTerminal(textFields1.get(n).getText())){
            left.setName(textFields1.get(n).getText());
            String symb = "";
            for (int j = 0; j< c.length; j++){
                if (c[j] != ' ' && c[j] != '|' && c[j] != '.'){
                    symb = symb + c[j];
                    System.out.println("symb" + symb);
                    System.out.println(c.length);
                }
                else {
                    if (!symb.equals("")){

                        if (!testSymb(initSymbs(), symb)){
                            if(symb.equals("eps")){
                                right.add(new Terminal(symb));
                            }else{
                                err("not a symb");
                            }

                        }else {

                            if (testNonTerminal(symb)){
                                right.add(new NonTerminal(symb));
                            }
                            else {
                                right.add(new Terminal(symb));

                            }
                        }
                        if (testAnd(c[j])){
                            symb = "";
                        }
                        if (testOr(c[j])){
                            and = 1;
                            String word = "";
                            for (int i = j+1; i<c.length; i++){
                                word = word + c[i];
                            }
                            textFields2.get(n).setText(word);
                            break;
                        }
                        else{
                            and = 0;
                        }
                    }
                }

            }
        }

        return new Rule(left, right);
    }

    public boolean testNonTerminal(String s){
        for (int i = 0; i<initSymbs().size(); i++){
            if (initSymbs().get(i).getName().equals(s)){
                if (analyser.symbList.get(i) instanceof NonTerminal){

                    return true;
                }
            }
        }
        return false;
    }

    private boolean testAnd(char c) {
        if (c == '.')
            return true;
        return false;
    }

    private boolean testOr(char c) {
        if (c =='|')
            return true;
        return false;
    }


}
