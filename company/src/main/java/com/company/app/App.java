package com.company.app;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.company.app.data.Credentials;
import com.company.app.exception.TokenException;

public final class App extends JFrame implements Runnable {
	private static final String AUTHENTICATION_END_POINT = "http://localhost:8080/company/webapi/authentication";
	private static int userId;
	
	private final JLabel labelUsername = new JLabel("Enter username: ");
	private final JLabel labelPassword = new JLabel("Enter password: ");
	private final JTextField textUsername = new JTextField(20);
	private final JPasswordField fieldPassword = new JPasswordField(20);

	private static String token;
	

	private JLabel loadingLbl;
	private JLabel labelLoadingProcess;
	private Logged loggedUserView;
	
	private final JButton btnRegister = getButton(e -> new Register().setVisible(true), "Register");
	private final JButton btnAdmin = getButton((ActionListener) e -> {
        new Administrator().setVisible(true);
        
    }, "Admin");

	private final JButton btnLogin = getButton((ActionListener) e -> {
        String password = fieldPassword.getText();
        String username = textUsername.getText();
        loadingLbl.setVisible(true);
        Authenticate authenticate = new Authenticate(username, password);
        authenticate.execute();
        visibilityOfComponent(btnAdmin, btnRegister, labelUsername, labelPassword,
        		textUsername, fieldPassword, false);
    }, "Login");
		
	private void visibilityOfComponent(JButton btnAdmin, JButton buttonRegister,
			JLabel label1, JLabel label2, JTextField txtField, JPasswordField passField, boolean param) {
		btnAdmin.setVisible(param);
		buttonRegister.setVisible(param);
		label1.setVisible(param);
		label2.setVisible(param);
		txtField.setVisible(param);
		passField.setVisible(param);
	}
	

	private JLabel getLoadingLbl() {
		ClassLoader classLoader = getClass().getClassLoader();
		ImageIcon loading = new ImageIcon(Objects.requireNonNull(classLoader.getResource("Gif/loader.gif")).getFile());
		JLabel loadingLbl = new JLabel("Checking Credentials... ", loading, JLabel.CENTER);
		loadingLbl.setVisible(false);
		return loadingLbl;
	}

	@Override
	public void run() {
		final JPanel loginPanel = getLoginPanel();
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
	}

	class Authenticate extends SwingWorker<String, Void> {
		private String username = "";
		private String password = "";

		Authenticate(String username, String password) {
			btnLogin.setVisible(false);
			this.username = username;
			this.password = password;
		
		}

		private String login(String username, String password) throws TokenException,
						RuntimeException, ClientProtocolException, IOException {
		
			final String credentials = new Credentials(username, password).toString();
			System.out.println(credentials);
			
			CloseableHttpClient httpClient = HttpClients.createDefault();

			HttpEntity httpEntity = new StringEntity(credentials, ContentType.APPLICATION_JSON);

			HttpPost httpPost = new HttpPost(AUTHENTICATION_END_POINT);
			httpPost.setEntity(httpEntity);

			CloseableHttpResponse response = httpClient.execute(httpPost);
			String token = "";

			try {
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new RuntimeException(
							"Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
				}

				System.out.println("Output from server ... \n");

				try (BufferedReader br = new BufferedReader(
						new InputStreamReader((response.getEntity().getContent())))) {
					
					token = br.readLine();
				
					if (token.equals(""))
						throw new TokenException("Could not retrieve a valid token from server response");
				
					userId = Integer.parseInt(token.substring(token.length() - 1));	
				}

			} finally {
				if (httpClient != null)
					httpClient.close();
				if (response != null)
					httpClient.close();
			}
			return token;
		}

		@Override
		protected String doInBackground() throws Exception, TokenException {
			String user = "";
			try {
				user = login(username, password);
			} catch (RuntimeException | ClientProtocolException e) {
				e.printStackTrace();
			}
			return user;
		}

		@Override
		public void done() {
			try {
				if (get() == null) return;
				
				token = get();

				loadingLbl.setVisible(false);
				visibilityOfComponent(btnAdmin, btnRegister, labelUsername, labelPassword,
		        		textUsername, fieldPassword, true);
				
				btnLogin.setVisible(true);
				
				new Logged().setVisible(true);
				
			} catch (InterruptedException | ExecutionException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}		
		}
	}

	private JButton getButton(ActionListener actionListener, String text) {
		final JButton btn = new JButton(text);
		btn.addActionListener(actionListener);
		return btn;
	}
	
	private static void addobjects(Component componente, JPanel jPanel, GridBagLayout layout, GridBagConstraints gbc,
			int gridx, int gridy, int gridwidth){
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        
        layout.setConstraints(componente, gbc);
        jPanel.add(componente);
    }

	private JPanel getLoginPanel() {
	    GridBagLayout gridBagLayout = new GridBagLayout();
		final JPanel loginPanel = new JPanel(gridBagLayout);
		loadingLbl = getLoadingLbl();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridx = 0;
		constraints.gridy = 0;
		loginPanel.add(labelUsername, constraints);
		
		
		constraints.gridx = 4;
		constraints.gridy = 1;
		loginPanel.add(loadingLbl, constraints);
		
		// add components to the panel
		constraints.gridx = 0;
		constraints.gridy = 0;
		loginPanel.add(labelUsername, constraints);
		
		constraints.gridx = 1;
		loginPanel.add(textUsername, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		loginPanel.add(labelPassword, constraints);

		constraints.gridx = 1;
		loginPanel.add(fieldPassword, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.WEST;
		loginPanel.add(btnLogin, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.EAST;
		loginPanel.add(btnRegister, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.CENTER;
		loginPanel.add(btnAdmin, constraints);
		
		loginPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Login Panel"));
		add(loginPanel);

		return loginPanel;
	}
	
	public static String getToken() {
		return token;
	}
	
	public static int getUserId() {
		return userId;
	}
}
