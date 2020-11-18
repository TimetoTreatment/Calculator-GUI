import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class JavaHW4 extends JFrame {

	enum Operation {
		clear, add, sub, result
	}

	private int mLastNum = 0;
	private int mCurrentNum = 0;
	JLabel mNumberLabel = new JLabel(Integer.toString(mLastNum), SwingConstants.RIGHT);

	class Display extends JPanel {

		Display() {

			setLayout(new BorderLayout());
			setBorder(new MatteBorder(2, 2, 1, 2, Color.black));

			addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent e) {

					int displayHeight = e.getComponent().getHeight();

					mNumberLabel.setFont(new Font(Font.DIALOG, Font.BOLD, displayHeight * 2 / 5));
					mNumberLabel.setBorder(new CompoundBorder(new LineBorder(Color.darkGray, displayHeight / 10),
							new EmptyBorder(0, 0, 0, displayHeight / 8)));
				}
			});

			add(mNumberLabel, BorderLayout.CENTER);
		}

		@Override
		public void paintComponent(Graphics g) {

			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;

			int srcX = mNumberLabel.getX();
			int srcY = mNumberLabel.getY();
			int dstX = srcX + mNumberLabel.getWidth();
			int dstY = srcY + mNumberLabel.getHeight();

			g2.setPaint(new GradientPaint(srcX, srcY, new Color(100, 100, 100), dstX, dstY, Color.white));
			g2.fillRect(srcX, srcY, dstX - srcX, dstY - srcY);
		}
	}

	class Calculator extends JPanel {

		Operation mOperation = Operation.clear;

		Calculator() {

			setLayout(new GridLayout(4, 4, 4, 4));
			setBackground(new Color(210, 210, 210));
			setBorder(new MatteBorder(0, 2, 2, 2, Color.black));

			JButton[] allButtons = new JButton[16];
			JButton[] numbers = new JButton[10];
			JButton[] operations = new JButton[4];
			JButton clear = new JButton("C");
			JButton add = new JButton("+");
			JButton sub = new JButton("-");
			JButton result = new JButton("=");
			JButton dummyButton1 = new JButton();
			JButton dummyButton2 = new JButton();

			for (int i = 0; i < 10; i++)
				numbers[i] = new JButton(Integer.toString(i));

			allButtons[0] = numbers[7];
			allButtons[1] = numbers[8];
			allButtons[2] = numbers[9];
			allButtons[3] = operations[0] = clear;
			allButtons[4] = numbers[4];
			allButtons[5] = numbers[5];
			allButtons[6] = numbers[6];
			allButtons[7] = operations[1] = add;
			allButtons[8] = numbers[1];
			allButtons[9] = numbers[2];
			allButtons[10] = numbers[3];
			allButtons[11] = operations[2] = sub;
			allButtons[12] = numbers[0];
			allButtons[13] = dummyButton1;
			allButtons[14] = dummyButton2;
			allButtons[15] = operations[3] = result;

			for (var button : allButtons) {

				button.setBorder(new EmptyBorder(0, 0, 0, 0));
				button.setForeground(Color.darkGray);
				button.setBackground(new Color(250, 250, 250));
				button.setFont(new Font(Font.DIALOG, Font.BOLD, 32));
				button.setFocusPainted(false);
			}

			clear.setForeground(new Color(180, 80, 40));

			for (var button : operations)
				button.setBackground(new Color(230, 230, 230));

			for (var button : numbers) {
				button.addActionListener((e) -> {

					if (mOperation == Operation.result) {
						mCurrentNum = Integer.parseInt(((JButton) e.getSource()).getText());
						mNumberLabel.setText(Integer.toString(mCurrentNum));
						mLastNum = 0;
						mOperation = Operation.add;
					} else if (mCurrentNum < 1000000) {
						mCurrentNum *= 10;
						mCurrentNum += Integer.parseInt(((JButton) e.getSource()).getText());
						mNumberLabel.setText(Integer.toString(mCurrentNum));
					}
				});
			}

			clear.addActionListener((e) -> {

				mCurrentNum = 0;
				mLastNum = 0;
				mNumberLabel.setText(Integer.toString(mLastNum));
				mOperation = Operation.clear;
			});

			add.addActionListener((e) -> {
				if (mOperation == Operation.sub)
					mLastNum -= mCurrentNum;
				else
					mLastNum += mCurrentNum;

				mNumberLabel.setText(Integer.toString(mLastNum));
				mCurrentNum = 0;
				mOperation = Operation.add;
			});

			sub.addActionListener((e) -> {

				if (mOperation == Operation.sub)
					mLastNum -= mCurrentNum;
				else
					mLastNum += mCurrentNum;

				mNumberLabel.setText(Integer.toString(mLastNum));
				mCurrentNum = 0;
				mOperation = Operation.sub;
			});

			result.addActionListener((e) -> {

				if (mOperation == Operation.sub)
					mLastNum -= mCurrentNum;
				else
					mLastNum += mCurrentNum;

				mNumberLabel.setText(Integer.toString(mLastNum));
				mCurrentNum = 0;
				mOperation = Operation.result;
			});

			addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent e) {

					int numpadHeight = e.getComponent().getHeight();

					for (var button : allButtons)
						button.setFont(new Font(Font.DIALOG, Font.BOLD, numpadHeight / 10));
				}
			});

			for (var button : allButtons)
				add(button);
		}
	}

	class MainPanel extends JPanel {

		MainPanel(Container contentPane) {

			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setBackground(Color.darkGray);

			Display display = new Display();
			Calculator calculator = new Calculator();

			display.setMaximumSize(new Dimension(480, 640 / 3));
			calculator.setMaximumSize(new Dimension(480, 640 * 2 / 3));

			addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent e) {

					int calWidth = e.getComponent().getWidth();
					int calHeight = e.getComponent().getHeight();

					if (calHeight >= calWidth * 4 / 3)
						calHeight = calWidth * 4 / 3;
					else
						calWidth = calHeight * 3 / 4;

					display.setMaximumSize(new Dimension(calWidth, calHeight / 3));
					calculator.setMaximumSize(new Dimension(calWidth, calHeight * 2 / 3));

					setBorder(new EmptyBorder((contentPane.getHeight() - calHeight) / 2 + 3, 0, 0, 0));
				}
			});

			add(display);
			add(calculator);
		}
	}

	JavaHW4() {

		setTitle("Calculator");
		setSize(600, 640);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		add(new MainPanel(getContentPane()));

		setVisible(true);
	}

	public static void main(String[] args) {
		new JavaHW4();
	}
}
