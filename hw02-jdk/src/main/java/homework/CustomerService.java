package homework;

import static java.util.Comparator.comparingLong;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {
    private final NavigableMap<Customer, String> customersMap = new TreeMap<>(comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        return getEntryOrNull(customersMap.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return getEntryOrNull(customersMap.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        customersMap.put(customer, data);
    }

    private Map.Entry<Customer, String> getEntryOrNull(Map.Entry<Customer, String> entry) {
        return entry == null ?
                null :
                new CustomerEntry(entry.getKey(), entry.getValue());
    }

    private static final class CustomerEntry implements Map.Entry<Customer, String> {
        private final Customer key;
        private String value;

        public CustomerEntry(Customer key, String value) {
            this.key = copyOf(key);
            this.value = value;
        }

        @Override
        public Customer getKey() {
            return key;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public String setValue(String value) {
            this.value = value;
            return value;
        }
    }

    private static Customer copyOf(Customer customer) {
        return new Customer(customer.getId(), customer.getName(), customer.getScores());
    }
}