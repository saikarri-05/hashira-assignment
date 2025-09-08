import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.math.BigInteger;
import java.util.*;

public class PolynomialConstant {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File("testcase.json"));

        int n = root.get("keys").get("n").asInt();
        int k = root.get("keys").get("k").asInt();

        List<BigInteger> roots = new ArrayList<>();
        Iterator<String> fieldNames = root.fieldNames();

        while (fieldNames.hasNext()) {
            String field = fieldNames.next();
            if (!field.equals("keys")) {
                JsonNode node = root.get(field);
                int base = node.get("base").asInt();
                String value = node.get("value").asText();
                BigInteger decimalValue = new BigInteger(value, base);
                roots.add(decimalValue);
            }
        }

        Collections.sort(roots);
        List<BigInteger> selectedRoots = roots.subList(0, k);

        BigInteger constant = BigInteger.ONE;
        for (BigInteger r : selectedRoots) {
            constant = constant.multiply(r);
        }

        int degree = selectedRoots.size();
        if (degree % 2 != 0) {
            constant = constant.negate();
        }

        System.out.println("Roots used: " + selectedRoots);
        System.out.println("Polynomial constant term: " + constant);
    }
}
