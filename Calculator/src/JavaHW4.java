import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

public class JavaHW4 extends JFrame {

	int mWidth = 480;
	int mHeight = 640;

	Integer mLastNum = 0;

	class Display extends JPanel {
		Display() {

			JLabel label = new JLabel(mLastNum.toString());

			setBackground(Color.cyan);
			add(label);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			g.setColor(Color.red);
			// g.drawRect(getX(), getY(), WIDTH, HEIGHT);

		}
	}

	class Numpad extends JPanel {

		Numpad() {

			setLayout(new GridLayout(4, 4));

			JButton[] numbers = new JButton[10];

			for (int i = 0; i < 10; i++) {
				numbers[i] = new JButton("i");

			}

			for (int i = 0; i < 10; i++)
				add(numbers[i]);
		}
	}

	class Calculator extends JPanel {

		Calculator() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

			JPanel display = new Display();
			JPanel numpad = new Numpad();

			display.setMaximumSize(new Dimension(mWidth, mHeight / 3));
			numpad.setMaximumSize(new Dimension(mWidth, mHeight * 2 / 3));

			addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent e) {
					mHeight = e.getComponent().getHeight();
					mWidth = e.getComponent().getWidth();

					System.out.println("height : " + mHeight);
					System.out.println("width : " + mWidth);

					if (mHeight >= mWidth * 4 / 3) {

						mHeight = mWidth * 4 / 3;

						display.setMaximumSize(new Dimension(mWidth, mHeight / 3));
						numpad.setMaximumSize(new Dimension(mWidth, mHeight * 2 / 3));
					} else {

						mWidth = mHeight * 3 / 4;

						display.setMaximumSize(new Dimension(mWidth, mHeight / 3));
						numpad.setMaximumSize(new Dimension(mWidth, mHeight * 2 / 3));
					}
				}
			});

			add(display);
			add(numpad);
		}
		
		@Override
		public void paintComponent(Graphics g)
		{

			
		}
	}

	JavaHW4() {

		setTitle("Calculator");
		setSize(480, 640);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setBackground(Color.darkGray);

		add(new Calculator());

		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		new JavaHW4();
	}
}
