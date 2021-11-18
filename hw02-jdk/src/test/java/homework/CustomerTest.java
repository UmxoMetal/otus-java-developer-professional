package homework;

import java.util.HashMap;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomerTest {
    @Test
    @DisplayName("Объект Customer как ключ в карте")
    void customerAsKeyTest() {
        final long customerId = 1L;

        var customerMap = new HashMap<Customer, String>();
        var customer = new Customer(customerId, "Ivan", 233);
        var expectedData = "data";
        customerMap.put(customer, expectedData);

        long newScore = customer.getScores() + 10;
        String factData = customerMap.get(new Customer(customerId, "IvanChangedName", newScore));

        assertThat(factData).isEqualTo(expectedData);

        long newScoreSecond = customer.getScores() + 20;
        customer.setScores(newScoreSecond);
        String factDataSecond = customerMap.get(customer);

        assertThat(factDataSecond).isEqualTo(expectedData);
    }

    @Test
    @DisplayName("Сортировка по полю score, итерация по возрастанию")
    void scoreSortingTest() {
        var customer1 = new Customer(1, "Ivan", 233);
        var customer2 = new Customer(2, "Petr", 11);
        var customer3 = new Customer(3, "Pavel", 888);

        var customerService = new CustomerService();
        customerService.add(customer1, "Data1");
        customerService.add(customer2, "Data2");
        customerService.add(customer3, "Data3");

        var smallestScoreEntry = customerService.getSmallest();
        assertThat(smallestScoreEntry.getKey()).isEqualTo(customer2);

        var middleScoreEntry = customerService.getNext(new Customer(10, "Key", 20));
        assertThat(middleScoreEntry.getKey()).isEqualTo(customer1);
        middleScoreEntry.getKey().setScores(10000);
        middleScoreEntry.getKey().setName("Vasy");


        var biggestScoreEntry = customerService.getNext(customer1);
        assertThat(biggestScoreEntry.getKey()).isEqualTo(customer3);

        var notExists = customerService.getNext(new Customer(100, "Not exists", 20000));
        assertThat(notExists).isNull();
    }

    @Test
    @DisplayName("Модификация коллекции")
    void mutationTest() {
        var customer1 = new Customer(1, "Ivan", 233);
        var customer2 = new Customer(2, "Petr", 11);
        var customer3 = new Customer(3, "Pavel", 888);

        var customerService = new CustomerService();
        customerService.add(customer1, "Data1");
        customerService.add(new Customer(customer2.getId(), customer2.getName(), customer2.getScores()), "Data2");
        customerService.add(customer3, "Data3");

        var smallestScore = customerService.getSmallest();
        smallestScore.getKey().setName("Vasyl");

        assertThat(customerService.getSmallest().getKey().getName()).isEqualTo(customer2.getName());
    }

    @Test
    @DisplayName("Возвращение в обратном порядке")
    void reverseOrderTest() {
        var customer1 = new Customer(1, "Ivan", 233);
        var customer2 = new Customer(3, "Petr", 11);
        var customer3 = new Customer(2, "Pavel", 888);

        var customerReverseOrder = new CustomerReverseOrder();
        customerReverseOrder.add(customer1);
        customerReverseOrder.add(customer2);
        customerReverseOrder.add(customer3);

        var customerLast = customerReverseOrder.take();
        assertThat(customerLast).usingRecursiveComparison().isEqualTo(customer3);

        var customerMiddle = customerReverseOrder.take();
        assertThat(customerMiddle).usingRecursiveComparison().isEqualTo(customer2);

        var customerFirst = customerReverseOrder.take();
        assertThat(customerFirst).usingRecursiveComparison().isEqualTo(customer1);
    }
}