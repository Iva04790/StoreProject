import java.io.Serializable;

public class Cashier implements Serializable {

    // Тук запазваме номера на касиера. Пример: 1
    private int id;

    // Това е името на касиера. Пример: "Иван"
    private String name;

    // Това е заплатата на касиера. Пример: 1200.00 (в лева)
    private double salary;

    // Това е специален метод, който се извиква, когато създаваш нов касиер.
    // Той задава началните стойности за ID, име и заплата.
    // Пример за използване: new Cashier(1, "Иван", 1200.00);
    public Cashier(int id, String name, double salary) {
        this.id = id;               // Записваме подаденото ID
        this.name = name;           // Записваме подаденото име
        this.salary = salary;       // Записваме подадената заплата
    }

    // Този метод връща ID-то на касиера, когато ни трябва
    public int getId() {
        return id;
    }

    // Този метод връща името на касиера
    public String getName() {
        return name;
    }

    // Този метод връща заплатата на касиера
    public double getSalary() {
        return salary;
    }

    // Ако искаме да сменим името на касиера по-късно, използваме този метод
    public void setName(String name) {
        this.name = name;
    }

    // Ако искаме да променим заплатата на касиера по-късно, използваме този метод
    public void setSalary(double salary) {
        this.salary = salary;
    }

    // Това е специален метод, който се използва когато искаме да покажем
    // информацията за касиера на екрана (System.out.println)
    @Override
    public String toString() {
        return "Касиер ID: " + id + ", Име: " + name + ", Заплата: " + salary + " лв.";
    }
}
