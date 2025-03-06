package app.PayPhone2;

import app.PayPhone2.data.Event;
import app.PayPhone2.data.EventDAO;
import app.PayPhone2.data.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

public class Play extends JFrame {
    private Player player;
    private List<Event> events;
    private Random random;
    private Event currentEvent;
    private int eventCount = 0;

    private JPanel buttonPanel;
    private JLabel nameLabel;
    private JLabel healthLabel;
    private JLabel moneyLabel;
    private JLabel staminaLabel;
    private JTextArea eventArea;
    private JButton option1Button;
    private JButton option2Button;
    private JButton startButton;
    private JButton exitButton;
    private JButton restartButton;

    public Play() {
        setTitle("PayPhone");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        random = new Random();
        EventDAO eventDAO = new EventDAO();
        events = eventDAO.getAllEvents();

        initUI();
        initPlayer();
        showStory();
    }

    //=========================================================UI
    private void initUI() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(1, 4));
        nameLabel = new JLabel("이름: ");
        healthLabel = new JLabel("체력 : ");
        staminaLabel = new JLabel("멘탈 : ");
        moneyLabel = new JLabel("돈 : ");
        topPanel.add(nameLabel);
        topPanel.add(healthLabel);
        topPanel.add(staminaLabel);
        topPanel.add(moneyLabel);
        add(topPanel, BorderLayout.NORTH);

        eventArea = new JTextArea();
        eventArea.setEditable(false);
        eventArea.setLineWrap(true);
        eventArea.setWrapStyleWord(true);
        add(new JScrollPane(eventArea), BorderLayout.CENTER);

        //Button
        buttonPanel = new JPanel(new GridLayout(1, 2));
        startButton = new JButton("오픈하기");
        exitButton = new JButton("오늘은 쉬기");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
                showOptionButtons();
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttonPanel.add(startButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        //선택지
        option1Button = new JButton();
        option2Button = new JButton();
        option1Button.addActionListener(this::handleEventChoice);
        option2Button.addActionListener(this::handleEventChoice);

        //다시하기 버튼
        restartButton = new JButton("다시하기");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
    }

    private void showOptionButtons() {
        buttonPanel.removeAll();
        buttonPanel.add(option1Button);
        buttonPanel.add(option2Button);
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    private void restartGame() {
        initPlayer();
        showStory();
        buttonPanel.removeAll();
        buttonPanel.add(startButton);
        buttonPanel.add(exitButton);
        buttonPanel.revalidate();
        buttonPanel.repaint();
        eventArea.setText("");
    }

    //=========================================================플레이어
    private void initPlayer() {
        String name = JOptionPane.showInputDialog(this, "이름을 입력하세요 (미입력시 기본 값)");
        if (name == null || name.trim().isEmpty()) {
            name = "태신"; // 이름이 없을 경우 기본값 설정
        }

        player = new Player();
        player.setName(name);
        player.setHealth(100);
        player.setStamina(100);
        player.setMoney(100);

        updateStatus();
    }

    //=========================================================인게임
    private void showStory() {
        String story =
                "유레카 교육과정을 열심히 수료한 " + player.getName()+".\n"+
                "이후 LG U+까지 취직하게 된다.\n\n" +
                "당신에게 주어진 프로젝트는 LG 휴대폰을 만들어 판매하는 일!\n" +
                "(LG의 기술력이면 얼마나 멋진 휴대폰을 만들수있을까)\n\n" +
                "당신의 프로젝트는 대성공하여 '"+player.getName()+"의 휴대폰 대리점'은 날이 갈수록 사람들이 모여든다.\n" +
                "그런데 오늘따라 빌런이 많을듯한 느낌...\n\n" +
                "'그래도 오픈해야겠지?'\n\n\n\n\n\n" +
                "제작 : 문태신 \n 아이디어 : 임민서";
        eventArea.setText(story);
    }

    private void startGame() {
        nextEvent();
    }

    //=============================================엔딩 스포 주의==========================================================
    private void nextEvent() {
        if (player.getHealth() <= 0) {
            endGame("[배드 엔딩] 사망\n\n무슨 일이죠?\n당신은 가게에서 숨을 거둔채로 발견되었습니다.");
            return;
        }
        if (player.getMoney() < 0) {
            endGame("[배드 엔딩] 파산\n\n무슨 일이죠?\n그 많은 돈은 다 어디에다가 쓴건가요?\n" +
                    "더 이상 회사도, 손님도 당신의 능력을 믿지 못합니다.\n" +
                    "국민취업제도의 도움을 받으며 다시 공부해야겠네요.");
            return;
        }
        if (player.getStamina() <= 0) {
            if (player.getHealth() >= 80 && player.getMoney() >= 10000) {
                endGame("[진 엔딩] 영앤리치\n\n'오늘은 여기까지!'\n" +
                        "셔터를 내리며 퇴근하는 당신. 오늘은 꽤 번것같습니다.\n'일십백천..억..??? 억이 넘는다고?'\n" +
                        "그 동안의 노력에 보답이라도 받는걸까요? 당신은 믿기지 않는 액수와 함께 뿌듯하게 퇴근합니다.");
            } else if (player.getHealth() >= 40 && player.getMoney() >= 10000) {
                endGame("[해피 엔딩] 억만장자\n\n'오늘은 여기까지..'\n" +
                        "힘겹게 셔터를 내리며 퇴근하는 당신. 오늘은 꽤 번것같습니다.\n'일십백천..억..??? 억이 넘는다고?'\n" +
                        "몸이 아프지 않은 곳이 없지만 뿌듯합니다. 오늘은 맛있는거라도 사먹어야겠네요.\n");
            } else if (player.getHealth() >= 10 && player.getMoney() >= 10000) {
                endGame("[굿 엔딩] 돈과 건강을 바꾼 사람\n\n'힘들다.. 오늘은 여기까지..'\n" +
                        "힘겹게 셔터를 내리며 퇴근하는 당신. 오늘은 꽤 번것같습니다.\n'일십백천..억..??? 억이 넘는다고?'\n" +
                        "하지만 기쁨보다는 몸의 고통이 더 큽니다. 일단 내일은 병원을 가봐야겠네요\n");
            } else if (player.getHealth() >= 80 && player.getMoney() >= 1000) {
            endGame("[굿 엔딩] 영업의 신\n\n'힘들다.. 오늘은 여기까지..'\n" +
                    "힘겹게 셔터를 내리며 퇴근하는 당신. 오늘은 꽤 번것같습니다.\n'일십백천..억..??? 억이 넘는다고?'\n" +
                    "하지만 기쁨보다는 몸의 고통이 더 큽니다. 일단 내일은 병원을 가봐야겠네요\n");
            } else if (player.getHealth() >= 40 && player.getMoney() >= 1000) {
                endGame("[굿 엔딩] 베스트 셀러\n\n'힘들다.. 오늘은 여기까지..'\n" +
                        "힘겹게 셔터를 내리며 퇴근하는 당신. 오늘은 꽤 번것같습니다.\n'일십백천..억..??? 억이 넘는다고?'\n" +
                        "하지만 기쁨보다는 몸의 고통이 더 큽니다. 일단 내일은 병원을 가봐야겠네요\n");
            } else if (player.getHealth() >= 10 && player.getMoney() >= 1000) {
                endGame("[평범 엔딩] 발품고수\n\n'힘들다.. 오늘은 여기까지..'\n" +
                        "힘겹게 셔터를 내리며 퇴근하는 당신. 오늘은 꽤 번것같습니다.\n'일십백천..억..??? 억이 넘는다고?'\n" +
                        "하지만 기쁨보다는 몸의 고통이 더 큽니다. 일단 내일은 병원을 가봐야겠네요\n");
            } else if (player.getHealth() >= 80 && player.getMoney() >= 100) {
                endGame("[평범 엔딩] 건강하면 되지\n\n'힘들다.. 오늘은 여기까지..'\n" +
                        "힘겹게 셔터를 내리며 퇴근하는 당신. 오늘은 꽤 번것같습니다.\n'일십백천..억..??? 억이 넘는다고?'\n" +
                        "하지만 기쁨보다는 몸의 고통이 더 큽니다. 일단 내일은 병원을 가봐야겠네요\n");
            } else if (player.getHealth() >= 40 && player.getMoney() >= 100) {
                endGame("[평범 엔딩] 음 평범하네\n\n'힘들다.. 오늘은 여기까지..'\n" +
                        "힘겹게 셔터를 내리며 퇴근하는 당신. 오늘은 꽤 번것같습니다.\n'일십백천..억..??? 억이 넘는다고?'\n" +
                        "하지만 기쁨보다는 몸의 고통이 더 큽니다. 일단 내일은 병원을 가봐야겠네요\n");
            } else if (player.getHealth() >= 10 && player.getMoney() >= 100) {
                endGame("[평범 엔딩] 무의미\n\n'힘들다.. 오늘은 여기까지..'\n" +
                        "힘겹게 셔터를 내리며 퇴근하는 당신. 오늘은 꽤 번것같습니다.\n'일십백천..억..??? 억이 넘는다고?'\n" +
                        "하지만 기쁨보다는 몸의 고통이 더 큽니다. 일단 내일은 병원을 가봐야겠네요\n");
            }
            return;
        }

        currentEvent = getRandomEvent(); // 현재 이벤트 설정
        if (currentEvent == null) {
            endGame("Error : Event is null");
            return;
        }

        eventCount++; // 이벤트 카운트 증가

        eventArea.setText(" - " + currentEvent.getTitle() + "\n\n");
        eventArea.append(currentEvent.getText());

        option1Button.setText(currentEvent.getOption1());
        option2Button.setText(currentEvent.getOption2());
    }

    private void handleEventChoice(ActionEvent e) {
        if (currentEvent == null) {
            return;
        }

        String resultMessage;
        if (e.getSource() == option1Button) {
            player.setHealth(player.getHealth() + currentEvent.getResult1_h());
            player.setStamina(player.getStamina() + currentEvent.getResult1_s());
            player.setMoney(player.getMoney() + currentEvent.getResult1_m());
            resultMessage = currentEvent.getResult1_text() + "\n\n체력 : " + formatNumber(currentEvent.getResult1_h()) + "  |  멘탈 : " + formatNumber(currentEvent.getResult1_s()) + "  |  돈 : " + formatNumber(currentEvent.getResult1_m())+"만원\n";
        } else {
            player.setHealth(player.getHealth() + currentEvent.getResult2_h());
            player.setStamina(player.getStamina() + currentEvent.getResult2_s());
            player.setMoney(player.getMoney() + currentEvent.getResult2_m());
            resultMessage = currentEvent.getResult2_text() + "\n\n체력 : " + formatNumber(currentEvent.getResult2_h()) + "  |  멘탈 : " + formatNumber(currentEvent.getResult2_s()) + "  |  돈 : " + formatNumber(currentEvent.getResult2_m())+"만원\n";
        }
        JOptionPane.showMessageDialog(this, resultMessage, "결과", JOptionPane.INFORMATION_MESSAGE);
        updateStatus();
        nextEvent();
    }

    private String formatNumber(int value) {
        if (value > 0) return "+" + value;
        else return String.valueOf(value);
    }

    private void updateStatus() {
        nameLabel.setText(player.getName()+" 점장님");
        healthLabel.setText("체력 : " + player.getHealth());
        staminaLabel.setText("멘탈 : " + player.getStamina());
        moneyLabel.setText("돈 : " + player.getMoney()+"만원");
    }

    private void endGame(String message) {
        String endingMessage = message + "\n\n해결한 이벤트 : " + eventCount + "개";
        eventArea.setText(endingMessage);
        buttonPanel.removeAll();
        buttonPanel.add(restartButton);
        buttonPanel.add(exitButton);
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    private Event getRandomEvent() {
        if (events.isEmpty()) {
            return null;
        }
        return events.get(random.nextInt(events.size()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Play().setVisible(true));
    }
}