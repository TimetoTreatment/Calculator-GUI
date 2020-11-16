import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class JavaHW4 extends JFrame {

	Integer mLastNum = 0;

	class Display extends JPanel {
		Display() {

			JLabel label = new JLabel(mLastNum.toString());

			setBackground(Color.cyan);
			add(label);

		}
	}

	class Numpad extends JPanel {

		Numpad() {

			setLayout(new GridLayout(4, 4, 2, 2));

			JButton[] numbers = new JButton[10];

			for (int i = 0; i < 10; i++) {
				numbers[i] = new JButton("i");

			}

			for (int i = 0; i < 10; i++)
				add(numbers[i]);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D g2 = (Graphics2D) g;

			g2.setStroke(new BasicStroke(1));

		}
	}

	class MainPenel extends JPanel {

		JPanel display;
		JPanel numpad;

		MainPenel(Container contentPane) {

			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setBackground(Color.darkGray);

			display = new Display();
			numpad = new Numpad();

			display.setMaximumSize(new Dimension(480, 640 / 3));
			numpad.setMaximumSize(new Dimension(480, 640 * 2 / 3));

			addComponentListener(new ComponentAdapter() {

				@Override
				public void componentResized(ComponentEvent e) {

					int mCalWidth = e.getComponent().getWidth();
					int mCalHeight = e.getComponent().getHeight();
					int mContentPaneWidth = contentPane.getWidth();
					int mContentPaneHeight = contentPane.getHeight();

					if (mCalHeight >= mCalWidth * 4 / 3)
						mCalHeight = mCalWidth * 4 / 3;
					else
						mCalWidth = mCalHeight * 3 / 4;

					display.setMaximumSize(new Dimension(mCalWidth, mCalHeight / 3));
					numpad.setMaximumSize(new Dimension(mCalWidth, mCalHeight * 2 / 3));

					setBorder(new EmptyBorder((contentPane.getHeight() - mCalHeight) / 2 + 3, 3, 3, 3));
				}
			});

			add(display);
			add(numpad);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D draw = (Graphics2D) g;

			draw.setColor(Color.red);
			draw.setStroke(new BasicStroke(5));
			draw.drawRect(display.getX(), display.getY(), display.getWidth(), display.getHeight() + numpad.getHeight());
		}

	}

	JavaHW4() {

		setTitle("Calculator");
		setSize(480, 640);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		add(new MainPenel(getContentPane()));

		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		new JavaHW4();
	}
}
