import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1000436184773186430L;

	private JButton startScriptButton;
	private JCheckBox attackInCombat;
	private JComboBox<String> attackSide;
	private JLabel jLabel1;
	public boolean startScript = false;

	public GUI() {
		super("RC Killer options");
		initComponents();
	}

	private void initComponents() {

		attackSide = new JComboBox<String>();
		jLabel1 = new JLabel();
		attackInCombat = new JCheckBox();
		startScriptButton = new JButton();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		attackSide.setModel(new DefaultComboBoxModel<String>(new String[] {
				"Left Side", "Right Side" }));

		jLabel1.setText("Side to attack crabs: ");

		attackInCombat.setText("Attack crabs in combat");

		startScriptButton.setText("Start RC Killer");
		startScriptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startScript = true;
				dispose();
			}
		});
		
		setVisible(true);
		setResizable(false);
		setAlwaysOnTop(true);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addContainerGap(
																		GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(
																		jLabel1)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		attackSide,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(23, 23,
																		23)
																.addComponent(
																		attackInCombat)
																.addGap(0,
																		0,
																		Short.MAX_VALUE))
												.addGroup(
														GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addContainerGap()
																.addComponent(
																		startScriptButton,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)))
								.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								layout.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
										.addComponent(attackSide,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(jLabel1))
						.addPreferredGap(
								LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(attackInCombat)
						.addPreferredGap(
								LayoutStyle.ComponentPlacement.RELATED, 15,
								Short.MAX_VALUE).addComponent(startScriptButton)
						.addContainerGap()));

		pack();
	}
	
}
