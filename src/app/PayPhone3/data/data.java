package app.PayPhone3.data;

import app.PayPhone3.dao.*;
import app.PayPhone3.dto.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;

public class data {
    static int people_count = 0;
    static int money = 100;
    static Random random = new Random();
    static int company_ran, storage_ran, speed_ran, amount_ran;
    static String customerRequest;

    static List<CompanyDTO> companies;
    static List<StorageDTO> storages;
    static List<PlanDTO> plans;
    static PhoneOrderDAO phoneOrderDAO;
    static SpeedDAO speedDAO;
    static AmountDAO amountDAO;

    static CompanyDTO selectedCompany = null;
    static StorageDTO selectedStorage = null;
    static PlanDTO selectedPlan = null;

    public static void start(List<CompanyDTO> companies, List<StorageDTO> storages, List<PlanDTO> plans,
                             PhoneOrderDAO phoneOrderDAO, SpeedDAO speedDAO, AmountDAO amountDAO) throws IOException {
        data.companies = companies;
        data.storages = storages;
        data.plans = plans;
        data.phoneOrderDAO = phoneOrderDAO;
        data.speedDAO = speedDAO;
        data.amountDAO = amountDAO;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("============================================");
        System.out.println("               \uD83D\uDCF1 PayPhone 3");
        System.out.println("============================================");
        System.out.println("고객의 주문에 맞추어 휴대폰을 제작하고 판매하세요!");
        System.out.println(" \uD83D\uDCB0 목표: 1억(10,000만원)을 모아 은퇴하기! \uD83D\uDCB0");
        System.out.println();
        System.out.println();
        System.out.println("1. 시작하기");
        System.out.println("2. 종료하기");
        System.out.print("입력 : ");

        while (true) {
            int n = Integer.parseInt(br.readLine());
            if (n == 1) {
                game();
                break;
            } else if (n == 2) {
                System.out.println("게임 종료");
                break;
            } else {
                System.out.println("다시 입력해주세요.");
                System.out.print("입력 : ");
            }
        }
    }

    public static void game() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            people_count++;
            String[] storageOptions = {"적", "적당하", "넉넉하"};
            String[] speedOptions = {"2G", "3G", "4G", "5G"};
            String[] amountOptions = {"조금만 있어도 괜찮아", "적당한게 좋을것 같아", "넉넉하면 좋겠어"};

            company_ran = random.nextInt(companies.size());
            storage_ran = random.nextInt(storageOptions.length);
            speed_ran = random.nextInt(speedOptions.length);
            amount_ran = random.nextInt(amountOptions.length);

            String randomStorage = storageOptions[storage_ran];
            String randomSpeed = speedOptions[speed_ran];
            String randomAmount = amountOptions[amount_ran];

            // 초반 5명은 150만원 예산, 이후는 랜덤 예산
            int customerBudget = (people_count <= 5) ? 150 : random.nextInt(150) + 100;
            int budgetTolerance = (int) (customerBudget * (random.nextDouble() * 0.2));

            // 손님 요구사항 저장
            customerRequest = "'"+companies.get(company_ran).getName() + " 휴대폰을 사고싶어요." +
                    "\n용량은 " + randomStorage + "게 좋을것 같아요.\n" +
                    "요금제는 " + randomSpeed + "에다가 데이터 양은 " + randomAmount + "요.\n" +
                    customerBudget + "만원 정도를 생각하고 있어요!'";

            System.out.println();
            System.out.println(people_count + "번째 손님이 가게에 들어왔습니다.");
            System.out.print("\n> '어서오세요'(↵)");
            br.readLine();
            System.out.println("\n=================================================");
            System.out.println(customerRequest);
//            System.out.println("\n손님 : " + companies.get(random.nextInt(companies.size())).getName() + " 휴대폰을 저장공간 " + randomStorage + "게 사고 싶어요.");
//            System.out.println("요금제는 " + randomSpeed + "에다가 데이터는 " + randomAmount + "요.");
//            System.out.println(customerBudget + "만원 정도를 생각하고 있어요.");
            System.out.println("=================================================");
            System.out.print("\n> '그러시다면..'(↵)");
            br.readLine();
            System.out.println("\n현재 보유 금액 : " + money + "만원");
            System.out.println("-----------------------");
            System.out.println("1. 주문 접수하기");
            System.out.println("2. 주문 거부하기");
            System.out.println("3. 영업 종료하기");
            System.out.print("입력 : ");

            int n = Integer.parseInt(br.readLine());
            if (n == 1) {
                System.out.println("\n주문을 접수했습니다.");
                makePhone();

                // 휴대폰 제작 후 판매 로직
                int phonePrice = calculatePrice();

                // 조건 중 2개 이상 같아야 한다.
                int test = 0;

                if(selectedCompany.getName().compareTo(companies.get(company_ran).getName()) == 0){
                    test++;
                }

                if(storage_ran == 2){
                    if(selectedStorage.getStorageId() >= 4) test++;
                }else if(storage_ran == 1){
                    if(selectedStorage.getStorageId() >= 2) test++;
                }else if(storage_ran == 0){
                    test++;
                }

                if(speed_ran == 3){
                    if(selectedPlan.getSpeedId() >= 4) test++;
                }else if(speed_ran == 2){
                    if(selectedPlan.getSpeedId() >= 3) test++;
                }else if(speed_ran == 1){
                    if(selectedPlan.getSpeedId() >= 2) test++;
                }else if(speed_ran == 0){
                    test++;
                }

                if(amount_ran == 2){
                    if(selectedPlan.getAmountId() >= 4) test++;
                }else if(amount_ran == 1) {
                    if(selectedPlan.getAmountId() >= 2) test++;
                }else if(amount_ran == 0) {
                    test++;
                }

                if(test < 2) {
                    System.out.println("손님 : 제가 원하는 휴대폰이 아닌데요.. 다른 곳에서 살게요");
                    money -= phonePrice;
                    System.out.println("\n휴대폰 제작 비용 " + phonePrice + "만원이 차감되었습니다.");
                    System.out.println("현재 보유 금액: " + money + "만원");
                }else if (phonePrice >= (customerBudget - budgetTolerance) && phonePrice <= (customerBudget + budgetTolerance)) {
                    System.out.println("손님 : 제가 원하던 휴대폰이에요! 구매하겠습니다.");
                    money += phonePrice;
                    System.out.println("\n휴대폰을 판매하여 "+phonePrice+"만원을 얻었습니다.");
                    System.out.println("현재 보유 금액: " + money + "만원");
                } else {
                    System.out.println("손님 : 제가 원하는 가격대가 아닌걸요... 다른 곳에서 살게요.");
                    money -= phonePrice;
                    System.out.println("\n휴대폰 제작 비용 " + phonePrice + "만원이 차감되었습니다.");
                    System.out.println("현재 보유 금액: " + money + "만원");
                }
                if (money <= 0) {
                    System.out.println("\n===========");
                    System.out.println("파산했습니다.");
                    System.out.println("===========");
                    return;
                }
                System.out.print("\n> '다음 손님을 맞이한다'(↵)");
                br.readLine();

                selectedCompany = null;
                selectedStorage = null;
                selectedPlan = null;
            } else if (n == 2) {
                System.out.print("\n> '죄송합니다. 맞춰드리기 어려울것 같아요..'(↵)");
                br.readLine();
                System.out.println("\n손님이 떠납니다");
                System.out.print("\n> 다음 손님을 맞이한다(↵)");
                br.readLine();
            } else if (n == 3) {
                ending();
                break;
            } else {
                System.out.println("잘못된 입력입니다. 다시 시도하세요.");
                people_count--;
            }
        }
    }

    public static void makePhone() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("\n------------------------------");
            System.out.println("         휴대폰 제작하기        ");
            System.out.println("------------------------------");
            System.out.println("1. 제조사" + " [" + (selectedCompany != null ? selectedCompany.getName() : "선택 안됨") + "]");
            System.out.println("2. 용량" + " [" + (selectedStorage != null ? selectedStorage.getGb() + "GB" : "선택 안됨") + "]");
            System.out.println("3. 요금제(속도)" + " [" + (selectedPlan != null && selectedPlan.getSpeedId() > 0 ? speedDAO.getSpeedById(selectedPlan.getSpeedId()).getSpeed()+"G" : "선택 안됨") + "]");
            System.out.println("4. 요금제(데이터)" + " [" + (selectedPlan != null && selectedPlan.getAmountId() > 0 ? amountDAO.getAmountById(selectedPlan.getAmountId()).getAmount()+"GB" : "선택 안됨") + "]");
            System.out.println("------------------------------");
            System.out.println("견적 : " + calculatePrice() + "만원");
            System.out.println("------------------------------");
            System.out.println("9. 주문 다시보기");
            System.out.println("0. 제작하기");
            System.out.println("------------------------------");
            System.out.print("입력 : ");

            int n = Integer.parseInt(br.readLine());
            switch (n) {
                case 0:
                    sell();
                    return;
                case 1:
                    company();
                    break;
                case 2:
                    storage();
                    break;
                case 3:
                    speed();
                    break;
                case 4:
                    amount();
                    break;
                case 9 :
                    System.out.println();
                    System.out.println(customerRequest);
                    System.out.print("\n> '맞다 이거였었지'(↵)");
                    br.readLine();
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 다시 시도하세요.");
            }
        }
    }

    public static void company() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("\n------------------------------");
        System.out.println("제조사 목록");
        System.out.println("------------------------------");
        for (int i = 0; i < companies.size(); i++) {
            System.out.println((i + 1) + ". " + companies.get(i).getName() + " (" + companies.get(i).getPrice() + "만원)");
        }
        System.out.println("------------------------------");
        System.out.print("선택 : ");
        int choice = Integer.parseInt(br.readLine());
        if (choice > 0 && choice <= companies.size()) {
            selectedCompany = companies.get(choice - 1);
        } else {
            System.out.println("잘못된 입력입니다.");
        }
    }

    public static void storage() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("\n------------------------------");
        System.out.println("용량 목록");
        System.out.println("------------------------------");
        for (int i = 0; i < storages.size(); i++) {
            System.out.println((i + 1) + ". " + storages.get(i).getGb() + "GB" + " (" + storages.get(i).getPrice() + "만원)");
        }
        System.out.println("------------------------------");
        System.out.print("선택 : ");
        int choice = Integer.parseInt(br.readLine());
        if (choice > 0 && choice <= storages.size()) {
            selectedStorage = storages.get(choice - 1);
        } else {
            System.out.println("잘못된 입력입니다.");
        }
    }

    public static void speed() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("\n------------------------------");
        System.out.println("요금제(속도) 목록");
        System.out.println("------------------------------");
        List<SpeedDTO> speeds = speedDAO.getAllSpeeds();
        for (int i = 0; i < speeds.size(); i++) {
            System.out.println((i + 1) + ". " + speeds.get(i).getSpeed() + "G (" + speeds.get(i).getPrice() + "만원)");
        }
        System.out.println("------------------------------");
        System.out.print("선택 : ");
        int choice = Integer.parseInt(br.readLine());
        if (choice > 0 && choice <= speeds.size()) {
            selectedPlan = new PlanDTO();
            selectedPlan.setSpeedId(speeds.get(choice - 1).getSpeedId());
        } else {
            System.out.println("잘못된 입력입니다.");
        }
    }

    public static void amount() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\n------------------------------");
        System.out.println("요금제(데이터) 목록");
        System.out.println("------------------------------");
        List<AmountDTO> amounts = amountDAO.getAllAmounts();
        for (int i = 0; i < amounts.size(); i++) {
            System.out.println((i + 1) + ". " + amounts.get(i).getAmount() + "GB (" + amounts.get(i).getPrice() + "만원)");
        }
        System.out.println("------------------------------");
        System.out.print("선택 : ");
        int choice = Integer.parseInt(br.readLine());
        if (choice > 0 && choice <= amounts.size()) {
            if (selectedPlan == null) {
                selectedPlan = new PlanDTO();
            }
            selectedPlan.setAmountId(amounts.get(choice - 1).getAmountId());
        } else {
            System.out.println("잘못된 입력입니다.");
        }
    }

    public static int calculatePrice() {
        int price = 0;

        // 회사별 가격
        if (selectedCompany != null) {
            price += selectedCompany.getPrice();
        }

        // 용량 가격
        if (selectedStorage != null) {
            price += selectedStorage.getPrice();
        }

        // 속도 가격
        if (selectedPlan != null && selectedPlan.getSpeedId() > 0) {
            SpeedDTO speed = speedDAO.getSpeedById(selectedPlan.getSpeedId());
            if (speed != null) {
                price += speed.getPrice();
            }
        }

        // 데이터 가격
        if (selectedPlan != null && selectedPlan.getAmountId() > 0) {
            AmountDTO amount = amountDAO.getAmountById(selectedPlan.getAmountId());
            if (amount != null) {
                price += amount.getPrice();
            }
        }
        return price;
    }

    public static void sell() throws IOException {
        if (selectedCompany == null || selectedStorage == null || selectedPlan == null) {
            System.out.println("\n휴대폰이 완성되지 않아 손님이 실망하며 떠납니다.");
            game();
            return;
        }
        System.out.println("\n휴대폰이 완성되었습니다.\n");
    }

    public static void ending() throws IOException {
        double result = ((double)money/10000)*100;
        System.out.println("\n============================================");
        System.out.println("[게임 종료]");
        System.out.println("최종 자본 : " + money + "만원");
        System.out.println("목표 달성률 : "+ result +"%");
        if(result >= 100){
            System.out.println("'장사의 신'");
        }else if(result >= 80){
            System.out.println("'엄청나요'");
        }else if(result >= 60){
            System.out.println("'대단해요'");
        }else if(result >= 40){
            System.out.println("'좋은걸요'");
        }else if(result >= 20){
            System.out.println("'잘하고있어요'");
        }else {
            System.out.println("'조금 더 노력하세요'");
        }
        System.out.println("=============================================");
    }
}