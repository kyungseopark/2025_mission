import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Random;

public class Application {
    private int[] ComNum;
    private int[] PlayerNum;

    public static void main(String[] args) {
        Application app = new Application();
        System.out.println("숫자 야구 게임을 시작합니다.");
        app.NumGeneration();
        app.Start();
    }

    public void Start() {
        while (true) {
            System.out.print("숫자를 입력해주세요 : ");
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

            String input;
            try {
                input = bf.readLine();
                ValidCheck(input);
            } catch (IllegalArgumentException | IOException e) {
                System.out.println(e.getMessage());
                continue;   // 입력이 잘못된 경우 다시 숫자 입력.
            }

            PlayerNum = new int[3];
            for (int i = 0; i < 3; i++) {
                PlayerNum[i] = Character.getNumericValue(input.charAt(i));
            }

            if (NumCompare()) {
                GameExit();
                break;
            }
        }
    }

    public void NumGeneration() {
        Random random = new Random();
        ComNum = new int[3];
        HashSet<Integer> num = new HashSet<>();

        while (num.size() < 3)
            num.add(random.nextInt(1,10));

        int index = 0;
        for (int number : num) {
            ComNum[index++] = number;
        }
    }

    public void ValidCheck(String input) {
        if (input.length() != 3) {
            throw new IllegalArgumentException("입력한 값이 3개가 아닙니다.");
        }

        HashSet<Character> check = new HashSet<>();
        for (char c : input.toCharArray()) {
            if (!check.add(c)) {
                throw new IllegalArgumentException("중복된 숫자가 있습니다. 서로 다른 3자리 숫자를 입력해주세요.");
            }

            if (!Character.isDigit(c)) {
                throw new IllegalArgumentException("입력한 값이 숫자가 아닙니다.");
            }
        }
    }

    public boolean NumCompare() {
        int strike = 0;
        int ball = 0;
        HashSet<Integer> temp = new HashSet<>();

        int index = 0;
        while (temp.size() < 3)
            temp.add(ComNum[index++]);

        for (int i = 0; i < ComNum.length; i++) {
            if (ComNum[i] == PlayerNum[i])
                strike++;
            if (!temp.add(PlayerNum[i]))
                ball++;
        }
        if (strike == 0 && ball == 0) {
            System.out.println("낫싱");
        } else {
            if (ball > 0) {
                System.out.print(ball + "볼 ");
            }
            if (strike > 0) {
                System.out.print(strike + "스트라이크");
            }
            System.out.println();
        }

        if (strike == 3) {
            System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
            return true;
        }
        return false;
    }

    public void GameExit() {
        System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String s;
        try {
            s = bf.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (s.equals("1")) {
            Start();
        }
        else {
            System.exit(0);
        }

    }
}