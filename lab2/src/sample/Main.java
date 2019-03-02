import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Skeleton extends JPanel {
    public static void main(String[] args) {
        // Створюємо нове графічне вікно (формочка). Параметр конструктора - заголовок вікна.
        JFrame frame = new JFrame("Привіт, Java 2D!");
        // Визначаємо поведінку програми при закритті вікна (ЛКМ на "хрестик")
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Визначаємо розмір вікна
        frame.setSize(500, 500);
        // Якщо позиція прив'язана до null, вікно з'явиться в центрі екрану
        frame.setLocationRelativeTo(null);
        // Забороняємо змінювати розміри вікна
        frame.setResizable(false);
        // Додаємо до вікна панель, що і описується даним класом
        // Зауважте, що точка входу в програму - метод main, може бути й в іншому класі
        frame.add(new Skeleton());
        // Показуємо форму. Важливо додати всі компоненти на форму до того, як зробити її видимою.
        frame.setVisible(true);
    }

    // Всі дії, пов'язані з малюванням, виконуються в цьому методі
    public void paint(Graphics g) {
        // Оскільки Java2D є надбудовою над старішою бібліотекою, необхідно робити це приведення
        Graphics2D g2d = (Graphics2D) g;
        // Далі йде безпосередньо малювання. Для прикладу намалюємо такий рядок
        g2d.drawString("Привіт, Java 2D!", 50, 50);
    }
}