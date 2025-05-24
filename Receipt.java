// Импортираме нужните библиотеки:
// - java.io.* – за запис и четене от файлове, сериализация
// - java.time.LocalDateTime – за дата и час на бележката
// - java.util.Map – за списък от продукти и количества
import java.io.*;
import java.time.LocalDateTime;
import java.util.Map;

// Класът Receipt представлява една КАСОВА БЕЛЕЖКА
// Той също така е Serializable – можем да я запишем като обект във .ser файл
public class Receipt implements Serializable {

    // Статична променлива, която брои кой е следващият номер на бележката
    private static int nextId = 1;

    // Уникален номер на тази конкретна касова бележка
    private int receiptId;

    // Касиерът, който издава бележката
    private Cashier cashier;

    // Дата и час на издаване
    private LocalDateTime timestamp;

    // Списък със стоките, които са продадени (Продукт -> количество)
    private Map<Product, Integer> products;

    // Обща сума, която клиентът е платил
    private double totalAmount;

    // Конструктор – създава нова касова бележка с касиер, продукти и сума
    public Receipt(Cashier cashier, Map<Product, Integer> products, double totalAmount) {
        this.receiptId = nextId++;                    // задаваме уникален номер
        this.cashier = cashier;                       // запазваме касиера
        this.timestamp = LocalDateTime.now();         // записваме текуща дата и час
        this.products = products;                     // запазваме продуктите
        this.totalAmount = totalAmount;               // записваме общата сума
    }

    // Метод за отпечатване и запазване на касовата бележка в два файла:
    // - четим текстов файл (.txt)
    // - сериализиран файл (.ser)
    public void printAndSave() throws IOException {
        // Име на текстовия файл: receipt_1.txt, receipt_2.txt и т.н.
        String filename = "receipt_" + receiptId + ".txt";

        // Отваряме файл с UTF-8 кодиране (за да се вижда кирилица)
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"))) {
            writer.println("Касова бележка №" + receiptId);
            writer.println("Касиер: " + cashier.getName());
            writer.println("Дата и час: " + timestamp);
            writer.println("Продукти:");

            // Обхождаме всички продукти и записваме името и броя им
            for (Map.Entry<Product, Integer> entry : products.entrySet()) {
                writer.println("- " + entry.getKey().getName() + " x " + entry.getValue());
            }

            // Показваме крайната сума
            writer.println("Обща сума: " + String.format("%.2f", totalAmount) + " лв.");
        }

        // Сериализация: записваме същата бележка и като Java обект (.ser файл)
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("receipt_" + receiptId + ".ser"))) {
            out.writeObject(this);
        }
    }

    // Метод за взимане на общата сума (ползва се при статистика)
    public double getTotalAmount() {
        return totalAmount;
    }
}
