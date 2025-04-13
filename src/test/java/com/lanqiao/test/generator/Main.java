package com.lanqiao.test.generator;

import java.io.PrintStream;
import java.util.*;

/**
 * @Author zzq
 * @Date 2025/4/4 12:32
 */
public class Main implements SolveAbility<AlgorithmInputData, AlgorithmOutputData> {
    public long solve(String s, String specialChars) {
        Set<Character> specialCharSet = new HashSet<>();
        for (char ch : specialChars.toCharArray()) {
            specialCharSet.add(ch);
        }
        // 用于记录每个字符上一次出现的位置
        int[] lastPos = new int[26];
        Arrays.fill(lastPos, -1); // 初始位置设为 -1

        long total = 0;
        long res = 0;

        for (int i = 0; i < s.length(); i++) {
            int ch = s.charAt(i) - 'a';

            // 当前字符的贡献值：i - 上一次出现的位置
            total += i - lastPos[ch];

            // 更新上一次出现位置
            lastPos[ch] = i;

            // 所有以 s[i] 结尾的子串的总引力累加
            res += total;

            if (specialCharSet.contains(s.charAt(i))) {
                // 如果当前字符是特殊字符，增加额外值
                //包含i的所有子字符串
                res += ((long) (i + 1)) * (s.length() - i);
            }
        }

        return res;
    }

    @Override
    public AlgorithmOutputData solve(AlgorithmInputData input) {
        AlgorithmOutputData output = new AlgorithmOutputData();
        output.ans = solve(input.s, "lqb");
        return output;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PrintStream out = System.out;

        AlgorithmInputData input = new AlgorithmInputData();
        input.read(scanner);

        Main main = new Main();
        AlgorithmOutputData output = main.solve(input);

        output.write(out);
    }
}


class AlgorithmInputData implements AlgorithmInput {
    public String s;

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    @Override
    public AlgorithmInput read(Scanner scanner) {
        s = scanner.nextLine();
        return this;
    }

    @Override
    public void write(PrintStream out) {
        out.println(s);
        out.flush();
    }

    @Override
    public String toString() {
        return "AlgorithmInputData{s='" + s + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlgorithmInputData that = (AlgorithmInputData) o;
        return Objects.equals(s, that.s);
    }

    @Override
    public int hashCode() {
        return Objects.hash(s);
    }
}

class AlgorithmOutputData implements AlgorithmOutput {
    public long ans;

    @Override
    public void write(PrintStream out) {
        out.println(ans);
        out.flush();
    }

    @Override
    public AlgorithmOutput read(Scanner scanner) {
        ans = scanner.nextLong();
        return this;
    }

    @Override
    public String toString() {
        return "AlgorithmOutputData{ans=" + ans + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlgorithmOutputData that = (AlgorithmOutputData) o;
        return ans == that.ans;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ans);
    }
}


interface AlgorithmInput {
    AlgorithmInput read(Scanner scanner);

    void write(PrintStream out);
}

interface AlgorithmOutput {
    void write(PrintStream out);

    AlgorithmOutput read(Scanner scanner);
}

interface SolveAbility<I extends AlgorithmInput, O extends AlgorithmOutput> {
    O solve(I input);
}