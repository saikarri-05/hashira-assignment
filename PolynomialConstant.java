import java.math.BigInteger;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class PolynomialConstant {
    public static void main(String[] args) throws Exception {
        String json = new String(Files.readAllBytes(Paths.get("testcase.json")));
        int n = extractInt(json, "\"n\"\\s*:\\s*(\\d+)");
        int k = extractInt(json, "\"k\"\\s*:\\s*(\\d+)");
        Pattern pattern = Pattern.compile("\\{\\s*\"base\"\\s*:\\s*\"(\\d+)\",\\s*\"value\"\\s*:\\s*\"([^\"]+)\"\\s*\\}");
        Matcher matcher = pattern.matcher(json);
        List<BigInteger> roots = new ArrayList<>();
        while (matcher.find()) {
            int base = Integer.parseInt(matcher.group(1));
            String valueStr = matcher.group(2);
            roots.add(new BigInteger(valueStr, base));
        }
        List<BigInteger> selectedRoots = roots.subList(0, k);
        BigInteger constant = BigInteger.ONE;
        for (BigInteger r : selectedRoots) {
            constant = constant.multiply(r);
        }
        if (k % 2 == 1) {
            constant = constant.negate();
        }
        System.out.println("Roots used: " + selectedRoots);
        System.out.println("Polynomial constant term: " + constant);
    }

    private static int extractInt(String text, String regex) {
        Matcher m = Pattern.compile(regex).matcher(text);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }
        return 0;
    }
}
