package com.tp.holdem.menu;

import javax.swing.JButton;

public class ButtonFixedRaiseLess extends JButton {
	public ButtonFixedRaiseLess(MainMenu menu) {
		super("<");
		addActionListener(new ButtonFixedRaiseLessListener(menu));
	}
}