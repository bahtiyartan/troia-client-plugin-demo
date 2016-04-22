package com.ias.client.plugin.test;

import java.awt.Dimension;

import javax.swing.JFrame;

public class SampleApp {

	public static void main(String[] args) {
		SamplePluginFrame tool = new SamplePluginFrame();
		tool.setSize(new Dimension(600, 750));
		tool.setLocationRelativeTo(null);
		tool.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tool.setVisible(true);
	}
}
