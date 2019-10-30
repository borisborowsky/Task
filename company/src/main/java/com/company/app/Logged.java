package com.company.app;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.ws.rs.core.HttpHeaders;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.company.app.data.BookUnit;
import com.company.app.data.Fine;
import com.company.app.data.BookUnit.BookStatus;
import com.company.app.utils.FComponent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Logged extends JFrame {
	private final static Logger LOGGER = Logger.getLogger(Logged.class.getName());
	
	private static final long serialVersionUID = 1L;
	private static final String FINE_CHECK_ENDPOINT = "http://localhost:8080/company/webapi/members/member/fines/";
	private static final String URL_ENDPOINT_CATALOG = "http://localhost:8080/company/webapi/catalog/search/";
	private static final String URL_ENDPOINT_BORROW = "http://localhost:8080/company/webapi/catalog/borrow/";
	private static final String URL_ENDPOINT_SEARCH_BOOK_TAKEN_BY_USER = "http://localhost:8080/company/webapi/catalog/search/borrowed/";

	final private String dates[] = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
			"16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };
	final private String months[] = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
	final private String years[] = { "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004",
			"2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017",
			"2018", "2019" };
	
	private final Vector<BookUnit> bookListView;
	private final Vector<BookUnit> searchListView;
	private final JList<BookUnit> bookList;
	private final JList<BookUnit> searchList;
	private final List<JTextField> fieldList;
	private final Container c;

	private final JTextField tAuthour;
	private final JTextField tBorrowPrice;
	private final JTextField tSubject;
	private final JTextField tTitle;
	private final JTextField tType;
	
	private final JTextArea txtBookInfo;
	
	private final JComboBox cbYear;
	private final JComboBox cbMonth;
	private final JComboBox cbDates;

	private JButton btnTaken;
	private JButton btnSearch;
	private JButton btnBorrow;
	private JButton btnRemove;

	private final String query = "";

	Logged() {
		setTitle("Logged Frame");
		setBounds(300, 90, 900, 600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);

		Handler handlerObj = new ConsoleHandler();
		handlerObj.setLevel(Level.ALL);
		LOGGER.addHandler(handlerObj);
		LOGGER.setLevel(Level.ALL);
		LOGGER.setUseParentHandlers(false);
		searchListView = new Vector();
		bookListView = new Vector();
		fieldList = new ArrayList<>();
		
		 Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	        // Set location of the window to center.
	     setLocation(dimension.width/2-this.getSize().width/2, dimension.height/2-this.getSize().height/2);
		
		new Notify().execute();
		
		bookList = FComponent.getJList(this, new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				BookUnit bookItem = (BookUnit) bookList.getSelectedValue();
				txtBookInfo.setText("");
				txtBookInfo.setText(bookItem.createView());
			}
		}, bookListView, 450, 50);

		searchList = FComponent.getJList(this, new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				BookUnit bookItem = (BookUnit) searchList.getSelectedValue();
				txtBookInfo.setText("");
				txtBookInfo.setText(bookItem.createView());	
			}
		}, searchListView, 670, 50);
		
		
		txtBookInfo = FComponent.getJTextArea(this, 13, 200, 150, 550, 340);
		FComponent.getJLabel(this, "Book description:", 20, 200, 20, 550, 310);
		
		// Labels
		FComponent.getJLabel(this, "Result from catalog:", 20, 200, 20, 670, 10);
		FComponent.getJLabel(this, "Your Books' result:", 20, 200, 20, 450, 10);

		FComponent.getJLabel(this, "Author", 15, 100, 20, 50, 250);
		FComponent.getJLabel(this, "Publish Date", 15, 100, 20, 50, 100);
		FComponent.getJLabel(this, "Borrow Price", 15, 100, 20, 50, 300);
		FComponent.getJLabel(this, "Subject", 15, 100, 20, 50, 200);
		FComponent.getJLabel(this, "Title", 15, 100, 20, 50, 50);
		FComponent.getJLabel(this, "Type", 15, 100, 20, 50, 150);
		// Text fields
		tAuthour = FComponent.getJTextField(this, 15, 200, 20, 200, 250, fieldList);
		tBorrowPrice = FComponent.getJTextField(this, 15, 200, 20, 200, 300, fieldList);
		tSubject = FComponent.getJTextField(this, 15, 200, 20, 200, 200, fieldList);
		tTitle = FComponent.getJTextField(this, 15, 200, 20, 200, 50, fieldList);
		tType = FComponent.getJTextField(this, 15, 200, 20, 200, 150, fieldList);

		// Combo boxes for date
		cbDates = FComponent.getJComboBox(this, dates, 13, 70, 20, 200, 100);
		cbMonth = FComponent.getJComboBox(this, months, 13, 70, 20, 270, 100);
		cbYear = FComponent.getJComboBox(this, years, 13, 70, 20, 340, 100);

		btnSearch = FComponent.getJButton(this, "Search", 15, 100, 20, 530, 540, a -> {
			bookListView.clear();
			bookList.updateUI();
			BookUnit book = createBook(fieldList);
			String json = new Gson().toJson(book);

			System.out.println(json);
			new Search(json).execute();
		});

		btnBorrow = FComponent.getJButton(this, "Borrow", 15, 100, 20, 390, 540, a -> {
			BookUnit book = bookList.getSelectedValue();
			System.out.println(App.getUserId() + " User id in Logged");
			new BookBorrow(book.getId()).execute();
		});

		btnTaken = FComponent.getJButton(this, "Your books", 15, 100, 20, 240, 540, a -> {
			new UserCatalogue().execute();
		});
	
		c = getContentPane();

		c.setLayout(null);

		setVisible(true);
	}

	private BookUnit createBook(List<JTextField> list) {
		Date date = FComponent.convertDate(cbYear, cbMonth, cbDates);
		
		double borrowPrice = 0;
		
		if (!tBorrowPrice.getText().isEmpty())
			borrowPrice = Double.parseDouble(tBorrowPrice.getText());
		
		return new BookUnit(App.getUserId(), tTitle.getText(), tType.getText(), tSubject.getText(), tAuthour.getText(),
				date, new Date(), borrowPrice, BookStatus.AVAILABLE);
	}

	private class Search extends SwingWorker<List<BookUnit>, Void> {
		private String jsonDate = "";

		Search(String JSON_STRING) {
			this.jsonDate = JSON_STRING;
			System.out.println("Url in constructor" + JSON_STRING);
		}

		@Override
		protected List<BookUnit> doInBackground() {
			List<BookUnit> result = null;
			try {
				result = search(jsonDate);
			} catch (IOException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			return result;
		}

		private List<BookUnit> search(String JSON_STRING) throws RuntimeException, IOException {
			List<BookUnit> result = null;
			CloseableHttpClient httpClient = null;

			httpClient = HttpClients.createDefault();

			HttpEntity httpEntity = new StringEntity(JSON_STRING, ContentType.APPLICATION_JSON);

			HttpPost httpPost = new HttpPost(URL_ENDPOINT_CATALOG);
			httpPost.setHeader(HttpHeaders.AUTHORIZATION, App.getToken());
			httpPost.setEntity(httpEntity);

			System.out.println(App.getToken());
			System.out.println(httpPost.getHeaders("Autorization").toString());

			CloseableHttpResponse response = null;

			try {
				response = httpClient.execute(httpPost);
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new RuntimeException(
							"Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
				}

				BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
				result = new Gson().fromJson(br.readLine(), new TypeToken<ArrayList<BookUnit>>() {
				}.getType());

			} finally {
				if (httpClient != null)
					httpClient.close();
				if (response != null)
					httpClient.close();
			}

			return result;
		}

		@Override
		protected void done() {
			try {
				List<BookUnit> list = get();
				bookListView.addAll(list);
				bookList.updateUI();
			} catch (InterruptedException | ExecutionException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
		}
	}

	private class BookBorrow extends SwingWorker<Void, Void> {
		private int bookId;

		BookBorrow(int bookId) {
			this.bookId = bookId;
		}

		private void borrowBook() throws IOException {
			CloseableHttpClient httpClient = null;
			try {
				String userId = String.valueOf(App.getUserId());

				String query = new StringBuilder(URL_ENDPOINT_BORROW).append(userId).append("/").append(bookId)
						.toString();

				httpClient = HttpClients.createDefault();
				HttpGet httpGet = new HttpGet(query);

				httpGet.setHeader(HttpHeaders.AUTHORIZATION, App.getToken());
				CloseableHttpResponse response = null;
				try {
					response = httpClient.execute(httpGet);

					if (response.getStatusLine().getStatusCode() != 200) {
						throw new RuntimeException(
								"Failed : Http error code " + response.getStatusLine().getStatusCode());
					}
				} catch (RuntimeException e) {
					LOGGER.log(Level.SEVERE, e.toString(), e);
					e.printStackTrace();
				}

				System.out.println("Response from server: \n");
				BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				System.out.println(br.readLine());
			} catch (IOException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			} finally {
				if (httpClient != null)
					httpClient.close();
			}
		}
		@Override
		protected Void doInBackground() throws Exception {
			borrowBook();
			return null;
		}
	}
	
	
	/**
	 * 
	 * @This SwingWorker class make a request to the server for
	 * the current logged user to check if it have a fines. 
	 * If a fine's are found the user is notified.
	 *
	 */
	private class Notify extends SwingWorker<List<Fine>, Void> {
		
		private List<Fine> checkForFines() throws IOException {
			CloseableHttpClient httpClient = null;
			List<Fine> fines = null;
			try {
				String userId = String.valueOf(App.getUserId());

				String query = new StringBuilder(FINE_CHECK_ENDPOINT).append(userId)
						.toString();

				httpClient = HttpClients.createDefault();
				HttpGet httpGet = new HttpGet(query);

				httpGet.setHeader(HttpHeaders.AUTHORIZATION, App.getToken());
				CloseableHttpResponse response = null;
				try {
					response = httpClient.execute(httpGet);

					if (response.getStatusLine().getStatusCode() != 200) {
						throw new RuntimeException(
								"Failed : Http error code " + response.getStatusLine().getStatusCode());
					}
				} catch (RuntimeException e) {
					LOGGER.log(Level.SEVERE, e.toString(), e);
					e.printStackTrace();
				}

				System.out.println("Response from server: \n");
				BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				
				fines = new Gson().fromJson(br.readLine(), new TypeToken<ArrayList<Fine>>() {
				}.getType());
				
			} catch (IOException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			} finally {
				if (httpClient != null)
					httpClient.close();
			}
			return fines;
		}

		@Override
		protected void done() {
			List<Fine> fines = null;
			try {
				fines = get();
				System.out.println(fines.get(0));
				new FinePanel(fines).setVisible(true);
			} catch (InterruptedException | ExecutionException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
		}

		@Override
		protected List<Fine> doInBackground() throws Exception {
			List<Fine> fines = null;
			fines = checkForFines();
			return fines;
		}
	}

	private class UserCatalogue extends SwingWorker<List<BookUnit>, Void> {
		
		private List<BookUnit> searchTakenBooks() throws IOException {
			List<BookUnit> books = null;
			CloseableHttpClient httpClient = null;
			try {
				String userId = String.valueOf(App.getUserId());

				String query = new StringBuilder(URL_ENDPOINT_SEARCH_BOOK_TAKEN_BY_USER).append(userId).toString();

				System.out.println(query + " QUERY");
				httpClient = HttpClients.createDefault();
				HttpGet httpGet = new HttpGet(query);
				httpGet.setHeader(HttpHeaders.AUTHORIZATION, App.getToken());

				CloseableHttpResponse response = null;
				try {
					response = httpClient.execute(httpGet);

					if (response.getStatusLine().getStatusCode() != 200) {
						throw new RuntimeException(
								"Failed : Http error code " + response.getStatusLine().getStatusCode());
					}
				} catch (RuntimeException e) {
					LOGGER.log(Level.SEVERE, e.toString(), e);
					e.printStackTrace();
				}

				System.out.println("Response from server: \n");
				BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

				books = new Gson().fromJson(br.readLine(), new TypeToken<ArrayList<BookUnit>>() {
				}.getType());

				System.out.println(books.toString());
			} catch (IOException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			} finally {
				if (httpClient != null)
					httpClient.close();
			}

			return books;
		}

		@Override
		protected void done() {
			List<BookUnit> books = null;
			try {
				System.out.println(get());
				books = get();
			} catch (InterruptedException | ExecutionException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			searchListView.addAll(books);
			searchList.updateUI();
		}

		@Override
		protected List<BookUnit> doInBackground() throws Exception {
			return searchTakenBooks();
		}
	}

}
