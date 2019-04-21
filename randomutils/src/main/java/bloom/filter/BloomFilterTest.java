package bloom.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BloomFilterTest {
    static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        long numberOfItems = 4 * 100 * 100 * 100;
        Set<String> compartmentSet = new HashSet<>();


        BloomFilter<String> filter = BloomFilter.create(
                Funnels.stringFunnel(StandardCharsets.UTF_8),
                numberOfItems,
                0.01);

        for (int i = 0; i < numberOfItems; i++) {
            String compartmentId = "ocid1.compartment.oc1.." + UUID.randomUUID().toString() + UUID.randomUUID().toString();
            filter.put(compartmentId);
            compartmentSet.add(compartmentId);
        }

        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);
        filter.writeTo(objectOutputStream);
        objectOutputStream.flush();
        objectOutputStream.close();

        System.out.println("Number of items : " + numberOfItems / 1000000 + " millions ");
        System.out.println("BloomFilter Size : " + byteOutputStream.size() / (1000 * 1000) + " MB");
        System.out.println("Regular Set Size : " + objectMapper.writeValueAsString(compartmentSet).getBytes(StandardCharsets.UTF_8).length / (1000 * 1000) + " MB");

    }
}
