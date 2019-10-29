package com.company.app;

import java.awt.Container;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.ws.rs.core.HttpHeaders;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.company.app.utils.FComponent;
import com.company.catalogue.BookUnit;

public class BookPanel extends JFrame {
	private static final String REMOVE_BOOK_ENDPOINT = "http://localhost:8080/company/webapi/books/remove/book";
	
	private final Container container;

	private final JTextField tAuthour;


	public BookPanel() {

		setTitle("Searching");
		setBounds(300, 90, 300, 300);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);

		FComponent.getJLabel(this, "Manage Books", 30, 300, 30, 300, 30);
		
		FComponent.getJLabel(this, "Author", 15, 100, 20, 100, 100);
	

		tAuthour = FComponent.getJTextField(this, 15, 200, 20, 200, 100);

		container = getContentPane();
		container.setLayout(null);

		setVisible(true);
	}

}