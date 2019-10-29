package com.company.app;

import java.awt.Container;
import java.awt.Font;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.company.app.data.Fine;
import com.company.app.utils.FComponent;

public class FinePanel extends JFrame {
	private static final long serialVersionUID = 1L;

	private Container c;

	private JTextArea txtFine;
	private final List<Fine> fines;


	public FinePanel(List<Fine> fines) {
		setTitle("Fines");
		setBounds(300, 90, 400, 400);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		this.fines = fines;
		txtFine = FComponent.getJTextArea(this, 17, 300, 300, 45, 45);
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < fines.size(); i++)
			sb.append(fines.get(i).toString());
		
		txtFine.setText(sb.toString());
	
		c = getContentPane();

		c.setLayout(null);

		setVisible(true);
	}
	
//	public static JTextArea getJTextArea(JFrame jFrame, int fontSize, int width, int height, int x, int y) {
//		JTextArea jTextArea = new JTextArea();
//		jTextArea.setFont(new Font("Arial", Font.PLAIN, fontSize));
//		jTextArea.setSize(width, height);
//		jTextArea.setLocation(x, y);
//		jTextArea.setLineWrap(true);
//		jFrame.getContentPane().add(jTextArea);
//		return jTextArea;
//	}
}
