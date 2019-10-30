package com.company.app;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
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

import com.company.app.data.BookUnit.BookStatus;
import com.company.app.data.Fine;
import com.company.app.utils.FComponent;
import com.company.app.data.BookUnit;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Administrator extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final String ADD_BOOK_ENDPOINT = "http://localhost:8080/company/webapi/books/add/book";
	private static final String REMOVE_BOOK_ENDPOINT = "http://localhost:8080/company/webapi/books/remove/book/";
	private static final String FETCH_ALL_BOOK_ENDPOINT = "http://localhost:8080/company/webapi/books/all";
	private static final String FETCH_FINE_USER_ENDPOINT = "http://localhost:8080/company/webapi/books/all";
	
	final private String dates[] = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
			"15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };
	final private String months[] = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
	final private String years[] = { "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004",
			"2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017",
			"2018", "2019" };
	
	private final Container container;

	private final JTextField tAuthour;
	private final JTextField tBorrowPrice;
	private final JTextField tSubject;
	private final JTextField tTitle;
	private final JTextField tType;
	private final JTextField tBookStatus;
	private final JComboBox cbYear;
	private final JComboBox cbMonth;
	private final JComboBox cbDates;

	private final JButton btnAdd;
	private final JButton fetchFinesBtn;
	private final JButton btnRemove;
	private final JButton fetchAllBooks;
	
	private final Vector<BookUnit> bookListView;
	private final JList<BookUnit> bookList;
	
	private final Vector<Fine> fineViewList;
	private final JList<Fine> fineList;

	@SuppressWarnings("unchecked")
	public Administrator() {
		setTitle("Administrator Panel");
		setBounds(300, 90, 900, 600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		bookListView = new Vector<>();
		fineViewList = new Vector<>();
		
		FComponent.getJLabel(this, "Manage Books", 30, 300, 30, 300, 30);

		FComponent.getJLabel(this, "Author", 15, 100, 20, 100, 100);
		FComponent.getJLabel(this, "Publish Date", 15, 100, 20, 100, 150);
		FComponent.getJLabel(this, "Borrow Price", 15, 100, 20, 100, 200);
		FComponent.getJLabel(this, "Subject", 15, 100, 20, 100, 250);
		FComponent.getJLabel(this, "Title", 15, 100, 20, 100, 300);
		FComponent.getJLabel(this, "Type", 15, 100, 20, 100, 350);
		FComponent.getJLabel(this, "Book Status", 15, 100, 20, 100, 400);

		tAuthour = FComponent.getJTextField(this, 15, 200, 20, 200, 100);

		cbDates = FComponent.getJComboBox(this, dates, 15, 80, 20, 200, 150);
		cbMonth = FComponent.getJComboBox(this, months, 15, 80, 20, 270, 150);
		cbYear = FComponent.getJComboBox(this, years, 15, 80, 20, 370, 150);

		tBorrowPrice = FComponent.getJTextField(this, 15, 200, 20, 200, 200);
		tSubject = FComponent.getJTextField(this, 15, 200, 20, 200, 250);
		tTitle = FComponent.getJTextField(this, 15, 200, 20, 200, 300);
		tType = FComponent.getJTextField(this, 15, 200, 20, 200, 350);
		tBookStatus = FComponent.getJTextField(this, 15, 200, 20, 200, 400);

		fetchFinesBtn = FComponent.getJButton(this, "Fines", 15, 100, 20, 50, 500, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("FIRE in eeee");
				new BookAdd(createBook()).execute();
			}

		});
		
		
		btnAdd = FComponent.getJButton(this, "Add", 15, 100, 20, 130, 500, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("FIRE in eeee");
				new BookAdd(createBook()).execute();
			}

		});

		btnRemove = FComponent.getJButton(this, "Remove", 15, 100, 20, 270, 500, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("FIRE in eeee");
				BookUnit bookItem = (BookUnit) bookList.getSelectedValue();
				new BookDelete(bookItem.getId()).execute();
			}

		});
		
		fetchAllBooks = FComponent.getJButton(this, "List books", 15, 100, 20, 410, 500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bookListView.clear();
				fetchAllBooks.updateUI();
				new FetchAllBook().execute();
			}

		});

		FComponent.getJLabel(this, "Book List:", 20, 200, 100, 600, 70);
		bookList = FComponent.getJList(this, new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		}, bookListView, 450, 100);
		
		FComponent.getJLabel(this, "Book List:", 20, 200, 100, 600, 70);
		fineList = FComponent.getJList(this, new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		}, fineViewList, 670, 100);
	
		
		container = getContentPane();
		container.setLayout(null);

		setVisible(true);
	}

	private String createBook() {
		Date date = FComponent.convertDate(cbYear, cbMonth, cbDates);
		double borrowPrice = 0;

		if (!tBorrowPrice.getText().isEmpty())
			borrowPrice = Double.parseDouble(tBorrowPrice.getText());

		BookUnit book = new BookUnit(App.getUserId(), tTitle.getText(), tType.getText(), tSubject.getText(),
				tAuthour.getText(), date, new Date(), borrowPrice, BookStatus.AVAILABLE);

		return new Gson().toJson(book);
	}

	private class BookAdd extends SwingWorker<BookUnit, Void> {
		private String jsonData = "";

		BookAdd(String JSON_STRING) {
			this.jsonData = JSON_STRING;
			System.out.println("Url in constructor" + JSON_STRING);
		}

		@Override
		protected BookUnit doInBackground() {
			BookUnit book = null;
			try {
				book = search(jsonData);

			} catch (IOException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			return book;
		}

		private BookUnit search(String jsonData) throws RuntimeException, IOException {
			BookUnit book = null;
			CloseableHttpClient httpClient = null;

			httpClient = HttpClients.createDefault();
			HttpEntity httpEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);

			HttpPost httpPost = new HttpPost(ADD_BOOK_ENDPOINT);
			httpPost.setHeader(HttpHeaders.AUTHORIZATION, App.getToken());
			httpPost.setEntity(httpEntity);

			CloseableHttpResponse response = null;

			try {
				response = httpClient.execute(httpPost);
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new RuntimeException(
							"Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
				}

				BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
				book = new Gson().fromJson(br.readLine(), BookUnit.class);

			} finally {
				if (httpClient != null)
					httpClient.close();
				if (response != null)
					httpClient.close();
			}

			return book;
		}

		@Override
		protected void done() {
			BookUnit book = null;
			try {
				book = get();
				System.out.println(book + " get");
				System.out.println(get() + " in get");
			} catch (InterruptedException | ExecutionException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}

		}
	}

	private class BookDelete extends SwingWorker<BookUnit, Void> {
		private int bookId;

		BookDelete(int bookId) {
			this.bookId = bookId;
		}

		private BookUnit deleteBook() throws IOException {
			CloseableHttpClient httpClient = null;
			BookUnit book = null;
			try {
				String query = new StringBuilder(REMOVE_BOOK_ENDPOINT).append(bookId).toString();

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
					e.printStackTrace();
				}

				System.out.println("Response from server: \n");
				
				BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				book = new Gson().fromJson(br.readLine(), BookUnit.class);
				System.out.println(br.readLine());
			} catch (IOException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			} finally {
				if (httpClient != null)
					httpClient.close();	
			}
			return book;
		}

		@Override
		protected BookUnit doInBackground() {
			BookUnit book = null;
			try {
				book = deleteBook();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return book;
		}
	}
	
	private class FetchAllBook extends SwingWorker<List<BookUnit>, Void> {
		
		private List<BookUnit> getAllBooks() throws IOException {
			List<BookUnit> books = null;
			CloseableHttpClient httpClient = null;
			try {
				String query = new StringBuilder(FETCH_ALL_BOOK_ENDPOINT)
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
					e.printStackTrace();
				}

				System.out.println("Response from server: \n");
				BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				books = new Gson().fromJson(br.readLine(), new TypeToken<ArrayList<BookUnit>>() {
				}.getType());
				
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
		protected List<BookUnit> doInBackground()  {
			List<BookUnit> books = null;	
			try {
				books = getAllBooks();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return books;
		}
		
		@Override
		protected void done() {
			List<BookUnit> books = null;
			try {
				books = get();
			} catch (InterruptedException | ExecutionException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			bookListView.addAll(books);
			bookList.updateUI();
		}
	}
	
	private class FetchAllFine extends SwingWorker<List<Fine>, Void> {
		
		private List<Fine> getAllFines() throws IOException {
			List<Fine> fines = null;
			CloseableHttpClient httpClient = null;
			try {
				
				String query = new StringBuilder(FETCH_ALL_BOOK_ENDPOINT)
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
					e.printStackTrace();
				}

				System.out.println("Response from server: \n");
				BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				fines = new Gson().fromJson(br.readLine(), new TypeToken<ArrayList<BookUnit>>() {
				}.getType());
				System.out.println(br.readLine());
				
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
		protected List<Fine> doInBackground()  {
			List<Fine> fines = null;	
			try {
				fines = getAllFines();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return fines;
		}
		
		@Override
		protected void done() {
			List<Fine> fines = null;
			try {
				System.out.println(get());
				fines = get();
			} catch (InterruptedException | ExecutionException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			fineViewList.addAll(fines);
			fineList.updateUI();
		}
	}
}
