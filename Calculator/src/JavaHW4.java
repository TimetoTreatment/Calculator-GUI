import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class JavaHW4 extends JFrame {

	enum Operation {
		clear, add, sub, result
	}

	class Display extends JPanel {

		private JLabel mLabel = new JLabel("0", SwingConstants.RIGHT);

		Display() {

			setLayout(new BorderLayout());
			setBorder(new MatteBorder(2, 2, 1, 2, Color.black));

			addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent e) {

					int displayHeight = e.getComponent().getHeight();

					mLabel.setFont(new Font(Font.DIALOG, Font.BOLD, displayHeight * 2 / 5));
					mLabel.setBorder(new CompoundBorder(new LineBorder(Color.darkGray, displayHeight / 10),
							new EmptyBorder(0, 0, 0, displayHeight / 8)));
				}
			});

			add(mLabel, BorderLayout.CENTER);
		}
		
		public void SetText(String str) {
			mLabel.setText(str);
		}

		@Override
		public void paintComponent(Graphics g) {

			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;

			int srcX = mLabel.getX();
			int srcY = mLabel.getY();
			int dstX = srcX + mLabel.getWidth();
			int dstY = srcY + mLabel.getHeight();

			g2.setPaint(new GradientPaint(srcX, srcY, new Color(100, 100, 100), dstX, dstY, Color.white));
			g2.fillRect(srcX, srcY, dstX - srcX, dstY - srcY);
		}
	}

	class Numpad extends JPanel {

		JButton[] mAllButtons = new JButton[16];
		JButton[] mNumbers = new JButton[10];
		JButton[] mOperations = new JButton[4];
		JButton mClear = new JButton("C");
		JButton mAdd = new JButton("+");
		JButton mSub = new JButton("-");
		JButton mResult = new JButton("=");
		JButton mDummyButton1 = new JButton();
		JButton mDummyButton2 = new JButton();

		Numpad() {

			setLayout(new GridLayout(4, 4, 4, 4));
			setBackground(new Color(210, 210, 210));
			setBorder(new MatteBorder(0, 2, 2, 2, Color.black));

			for (int i = 0; i < 10; i++)
				mNumbers[i] = new JButton(Integer.toString(i));

			mAllButtons[0] = mNumbers[7];
			mAllButtons[1] = mNumbers[8];
			mAllButtons[2] = mNumbers[9];
			mAllButtons[3] = mOperations[0] = mClear;
			mAllButtons[4] = mNumbers[4];
			mAllButtons[5] = mNumbers[5];
			mAllButtons[6] = mNumbers[6];
			mAllButtons[7] = mOperations[1] = mAdd;
			mAllButtons[8] = mNumbers[1];
			mAllButtons[9] = mNumbers[2];
			mAllButtons[10] = mNumbers[3];
			mAllButtons[11] = mOperations[2] = mSub;
			mAllButtons[12] = mNumbers[0];
			mAllButtons[13] = mDummyButton1;
			mAllButtons[14] = mDummyButton2;
			mAllButtons[15] = mOperations[3] = mResult;

			for (var button : mAllButtons) {
				button.setBorder(new EmptyBorder(0, 0, 0, 0));
				button.setForeground(Color.darkGray);
				button.setBackground(new Color(250, 250, 250));
				button.setFont(new Font(Font.DIALOG, Font.BOLD, 32));
				button.setFocusPainted(false);
			}
			
			for (var button : mOperations)
				button.setBackground(new Color(230, 230, 230));

			mClear.setForeground(new Color(180, 80, 40));

			addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent e) {
					for (var button : mAllButtons)
						button.setFont(new Font(Font.DIALOG, Font.BOLD, e.getComponent().getHeight() / 10));
				}
			});

			for (var button : mAllButtons)
				add(button);
		}
	}

	class Calculator extends JPanel {

		private int mLastNum = 0;
		private int mCurrentNum = 0;
		String mOperation = "clear";

		Calculator(Container contentPane) {

			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setBackground(Color.darkGray);

			Display display = new Display();
			Numpad numpad = new Numpad();

			display.setMaximumSize(new Dimension(480, 640 / 3));
			numpad.setMaximumSize(new Dimension(480, 640 * 2 / 3));

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
					numpad.setMaximumSize(new Dimension(calWidth, calHeight * 2 / 3));

					setBorder(new EmptyBorder((contentPane.getHeight() - calHeight) / 2 + 3, 0, 0, 0));
				}
			});

			for (var button : numpad.mNumbers) {
				button.addActionListener((e) -> {

					if (mOperation == "result") {
						mCurrentNum = Integer.parseInt(((JButton) e.getSource()).getText());
						display.SetText(Integer.toString(mCurrentNum));
						mLastNum = 0;
						mOperation = "clear";
					} else if (mCurrentNum < 1000000) {
						mCurrentNum *= 10;
						mCurrentNum += Integer.parseInt(((JButton) e.getSource()).getText());
						display.SetText(Integer.toString(mCurrentNum));
					}
				});
			}

			numpad.mClear.addActionListener((e) -> {

				mCurrentNum = 0;
				mLastNum = 0;
				mOperation = "clear";
				display.SetText(Integer.toString(mLastNum));
			});

			numpad.mAdd.addActionListener((e) -> {
				
				if (mOperation == "sub")
					mLastNum -= mCurrentNum;
				else
					mLastNum += mCurrentNum;

				mCurrentNum = 0;
				mOperation = "add";
				display.SetText(Integer.toString(mLastNum));
			});

			numpad.mSub.addActionListener((e) -> {

				if (mOperation == "sub")
					mLastNum -= mCurrentNum;
				else
					mLastNum += mCurrentNum;

				mCurrentNum = 0;
				mOperation = "sub";
				display.SetText(Integer.toString(mLastNum));
			});

			numpad.mResult.addActionListener((e) -> {

				if (mOperation == "sub")
					mLastNum -= mCurrentNum;
				else
					mLastNum += mCurrentNum;

				mCurrentNum = 0;
				mOperation = "result";
				display.SetText(Integer.toString(mLastNum));
			});

			add(display);
			add(numpad);
		}
	}

	JavaHW4() {

		setTitle("Calculator");
		setSize(600, 640);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(new Calculator(getContentPane()));
		setVisible(true);
	}

	public static void main(String[] args) {

		new JavaHW4();
	}
}
