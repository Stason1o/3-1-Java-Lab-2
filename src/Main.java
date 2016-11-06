import java.awt.EventQueue;
import javax.swing.*;

public class Main {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyJPanel panel = new MyJPanel();
					JFrame frame = new JFrame();
					frame.add(panel);
					frame.setSize(600, 600);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
