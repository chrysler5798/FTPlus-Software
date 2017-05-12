package main.window;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.SwingWorker.StateValue;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.web.WebView;
import main.couleurList;
import main.listener.ouvrirListener;
import net.miginfocom.swing.MigLayout;

public class mainWindow extends JFrame
{

	static private final String newline = "\n";
	private final String PC_PATH = System.getenv("APPDATA")+File.separator+"FTPlus"+File.separator;
	private String SITE_POST = "";
	
	JTextArea textArea;
	int i = 0;
	JList<String> list;
	JButton btnNewButton_1;
	boolean isTrue, mp3isTrue;
	WebView webView;
	MediaPlayer mediaP;
	DefaultListModel<String> model;
	File newSon;
	Media music;
	
	public mainWindow()
	{
		setTitle("FTPlus - "+getUser());
		
		setSize(1400,600);
		
		setLocationRelativeTo(null);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().setLayout(new MigLayout("", "[][][384px,grow][][]", "[][250px][][grow][]"));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		model = new DefaultListModel<>();
		list = new JList<>();

		setupList();
		
		textArea = new JTextArea();
		
		scrollPane.setMinimumSize(new Dimension(100, 100));
		scrollPane.setViewportView(list);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JButton addButton = new JButton("AJOUTER");
		JButton ouvrirButton = new JButton("OUVRIR");
		JButton syncButton = new JButton("SYNC");
		JButton playButton = new JButton();
		JButton supButton = new JButton();
		
		addButton.setIcon(new ImageIcon(getClass().getResource("/main/img/add.png")));
		ouvrirButton.setIcon(new ImageIcon(getClass().getResource("/main/img/browser.png")));
		syncButton.setIcon(new ImageIcon(getClass().getResource("/main/img/sync.png")));
		playButton.setIcon(new ImageIcon(getClass().getResource("/main/img/play.png")));
		supButton.setIcon(new ImageIcon(getClass().getResource("/main/img/delete.png")));
		
		getContentPane().add(addButton, "cell 2 0,grow");
		getContentPane().add(ouvrirButton, "cell 2 0,grow");
		getContentPane().add(syncButton, "cell 2 0,grow");
		getContentPane().add(playButton, "cell 2 0");
		getContentPane().add(supButton, "cell 2 0");
		
		Font buttonFont = new Font("Tahoma", Font.BOLD, 11);
		
		addButton.setFont(buttonFont);
		ouvrirButton.setFont(buttonFont);
		syncButton.setFont(buttonFont);

		list.addListSelectionListener(new ListSelectionListener()
		{
			
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				//String value = list.getSelectedValue();
				//if(value != null)
				//{
					//String mp3Value = value.substring(value.length() - 3);
//					if(mediaP != null)
//					{
//						playButton.setIcon(new ImageIcon(getClass().getResource("/main/img/play.png")));
//						mediaP.stop();
//						isTrue = false;
//					}
					//mp3isTrue = mp3Value.equals("mp3");
					
					//if(mp3isTrue)
					//{
//						Media music = new Media("file:/"+PC_PATH+value);
//						mediaP = new MediaPlayer(music);
//						mediaP.setVolume(0.5f);
						//textArea.setText("Selectionne un fichier texte...");
					//}
					//else
					//{
					//	setupPreview();
					//}
				//}
			}
		});
		
		ouvrirButton.addActionListener(new ouvrirListener());
		
		isTrue = false;
		
		playButton.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(isTrue)
				{
					if(mp3isTrue)
					{
						playButton.setIcon(new ImageIcon(getClass().getResource("/main/img/play.png")));
						isTrue = false;
					}
				}
				else
				{
					if(mp3isTrue)
					{
						playButton.setIcon(new ImageIcon(getClass().getResource("/main/img/pause.png")));
						isTrue = true;
					}
				}
				
			}
		});
		
		getContentPane().add(scrollPane, "cell 0 1,aligny top,grow");
		
		getContentPane().add(scrollPane_1, "cell 2 3,aligny top,grow");
		btnNewButton_1 = new JButton("Sauvegarder");
		btnNewButton_1.setEnabled(false);
		getContentPane().add(btnNewButton_1, "cell 2 4,grow");
		
		JFXPanel jfxPanel = new JFXPanel();
		getContentPane().add(jfxPanel, "cell 0 0 1 5,growy");
		getContentPane().add(scrollPane, "cell 2 1,aligny top,grow");
		
		Platform.runLater(() -> {
		    webView = new WebView();
		    jfxPanel.setScene(new Scene(webView));
		    webView.getEngine().load("http://www.youtube.com/");
		});
		
		addButton.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					waitForRunLater(webView);
					
					String videoId = convertToShortYt(webView.getEngine().getLocation());
			    	String urlToDl = "http://www.youtubeinmp3.com/fetch/?format=JSON&video=https://www.youtube.com/watch?v="+videoId;
			    	
				    JSONObject json = readJsonFromUrl(urlToDl);
				    
				    String titleVideo = json.getString("title");
				    
			    	String newUrl = readFile(PC_PATH+"new_url.txt");
			    	String newSongs = readFile(PC_PATH+"new_title.txt");
			    	
			    	if(!newUrl.contains((CharSequence) videoId) &&
			    			!newSongs.contains((CharSequence) titleVideo))
			    	{
			    		
			    		FileWriter file = new FileWriter(new File(PC_PATH+"new_url.txt"));
				    	file.write(newUrl+videoId+newline);
				    	file.close();
				    	
				    	FileWriter writerNew = new FileWriter(new File(PC_PATH+"new_title.txt"));
				    	writerNew.write(newSongs+titleVideo+newline);
				    	writerNew.close();
				    	
				    	supButton.setEnabled(false);
				    	
				    	setupList();
			    	}
			    	
					//URL ytUrl = new URL(urlVideo);
					//titleVideo = titleVideo.replaceAll("[^a-zA-Z0-9.-]", "_");
					//newSon = new File(PC_PATH+titleVideo+".mp3");
					
//					SwingWorker sw = new SwingWorker(){
//					      protected Object doInBackground() throws Exception {
//					    	//FileUtils.copyURLToFile(ytUrl, newSon);
//					    	System.out.println("PROSCESS");	
//					        return null;
//					      }
//					         
//					      public void done(){            
//					        if(SwingUtilities.isEventDispatchThread())
//					        	System.out.println("DONE");
//					        	setupList();
//					      }         
//					};
//					sw.execute();
				}
				catch (IOException e1)
				{
					System.out.println(e1);
				}
				catch (InterruptedException e1)
				{
					System.out.println(e1);
				}
			}
		});
		
		syncButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
					try
					{
				    	String newUrl = readFile(PC_PATH+"new_url.txt");
				    	String pathforMusic = readFile(PC_PATH+"setup.txt");
				    	
				    	List<String> listMusics = getMusic();
				    	
				    	for (int i = 1; i < listMusics.size(); i+=2)
				    	{
				    		String titleVideo = listMusics.get(i).trim();
				    		
				    		File fileValue = new File(pathforMusic+File.separator+titleVideo+".mp3");
				    		if(!fileValue.exists())
				    		{
				    			String id = listMusics.get(i-1).trim();
				    			
						    	String urlJson = "http://www.youtubeinmp3.com/fetch/?format=JSON&filesize=1&video=https://www.youtube.com/watch?v="+id;
						    	JSONObject json = readJsonFromUrl(urlJson);
						    	
							    String urlToDl = json.getString("link");
							    
							    int fileSize = json.getInt("filesize")/1028;
							    
								URL ytUrl = new URL(urlToDl);
				    			downloadMusic(titleVideo, ytUrl, fileSize);
				    		}
						}
				    	
						try (BufferedReader br = new BufferedReader(new FileReader(new File(PC_PATH+"new_url.txt"))))
						{
						    String line;
						    
						    while ((line = br.readLine()) != null)
						    {
						    	String urlJson = "http://www.youtubeinmp3.com/fetch/?format=JSON&filesize=1&video=https://www.youtube.com/watch?v="+line;
						    	JSONObject json = readJsonFromUrl(urlJson);
						    	
							    String titleVideo = json.getString("title");
							    String urlToDl = json.getString("link");
							    
							    int fileSize = json.getInt("filesize")/1028;
							    
							    editMusic(line, titleVideo);
							    
								URL ytUrl = new URL(urlToDl);
								
								downloadMusic(titleVideo, ytUrl, fileSize);
								
								//String newtitleVideo = titleVideo.replaceAll("[^a-zA-Z0-9.-]", "_");
					            
								//String musicTxtName = "music.txt";
								
					            //File musicTxt = new File(PC_PATH+musicTxtName);
					            
					            //InputStream inputStream = new FileInputStream(musicTxt);
					            
					            //boolean doneUp = false;
					            
					            //inputStream.close();
						    }
						    
						}
						catch (Exception e4)
						{
							System.out.println(e4);
						}
				    	
				    	PrintWriter printToEdit = new PrintWriter(new File(PC_PATH+"new_title.txt"));
		        		printToEdit.print("");
				    	printToEdit.close();
				    	
						PrintWriter print = new PrintWriter(new File(PC_PATH+"new_url.txt"));
						print.print("");
						print.close();

					}
					catch (FileNotFoundException e1)
					{
						e1.printStackTrace();
					}
					catch (IOException e1)
					{
						e1.printStackTrace();
					}
//					if(file.length() < 104857600)
//			        {
//						try {
//			                   FileInputStream fileUp = new FileInputStream(file);
//			                   fClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
//			                   fClient.storeFile("ftplus/"+name, fileUp);
//			                   System.out.println(printServerReply(fClient));
//			                   fileUp.close();
//			                   fClient.logout();
//			                    
//			               	 } catch (IOException e1)
//			                 {
//			               		 e1.printStackTrace();
//			                 }
//			        }
//			        else
//			        {
//			           System.out.println("Erreur : fichier trop lourd ("+name+")");
//			        }
					}
		});
		
		supButton.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					editMusic("", list.getSelectedValue());
					System.out.println(list.getSelectedValue());
				} 
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
//				String pathforMusic = "";
//				try
//				{
//					pathforMusic = readFile(PC_PATH+"setup.txt");
//				}
//				catch (IOException e1)
//				{
//					e1.printStackTrace();
//				}
//				File fileToRm = new File(pathforMusic+list.getSelectedValue());
//
//				if(fileToRm.delete())
//				{
//					setupList();
//				}
//				else
//				{
//					System.out.println("Fail sup : "+pathforMusic+list.getSelectedValue());
//				}
			}
		});
		
		textArea.addKeyListener(new KeyListener()
		{
			
			@Override
			public void keyTyped(KeyEvent e)
			{
				
			}
			
			@Override
			public void keyReleased(KeyEvent e)
			{
				
			}
			
			@Override
			public void keyPressed(KeyEvent e)
			{
				btnNewButton_1.setEnabled(true);
			}
		});
		
		scrollPane_1.setViewportView(textArea);
		textArea.setText("Selectionne un fichier texte...");
//            System.out.println("Opening: " + file.getAbsolutePath() + newline);
//            if(file.length() < 104857600)
//            {
//                try {
//                    FileInputStream fileUp = new FileInputStream(file.getAbsolutePath());
//                    fClient.setFileType(FTP.BINARY_FILE_TYPE);
//                    fClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
//                    fClient.storeFile("ftplus/"+file.getName(), fileUp);
//                    System.out.println(printServerReply(fClient));
//                    fileUp.close();
//                    fClient.logout();
//                    
//                } catch (IOException e)
//                {
//                	e.printStackTrace();
//                }
//            }
//            else
//            {
//            	System.out.println("Trop lourd, nabo");
//            }
//
//        } else {
//        	System.out.println("Open command cancelled by user." + newline);
//        }
		setVisible(true);
	}
	
	static String readFile(String path) throws IOException 
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, Charset.defaultCharset());
	}
	
	private String convertToShortYt(String url)
	{
    	int debutId = url.indexOf("watch?v=")+8;
    	int finId = debutId+11;
    	return url.substring(debutId, finId);
	}
	
	private String getUser()
	{
		try
		{
			return readFile(PC_PATH+"user.txt");
		}
		catch (IOException e1)
		{
			return e1.toString();
		}
	}
	
	private boolean editMusic(String urlMusic, String titleMusic) throws IOException
	{
    	HttpClient client = new HttpClient();
		
		List<String> listMusic = getMusic();
		
    	if(listMusic != null)
    	{
    		if(urlMusic.equals(""))
    		{
        		for (int i = 1; i < listMusic.size(); i+=2)
        		{
					String act = listMusic.get(i).trim();
					if(act.equals(titleMusic))
					{
						listMusic.remove(i);
						listMusic.remove(i-1);
					}
				}
    		}
    		else
    		{
    			urlMusic = urlMusic.replaceAll("\\s+","");
    			
        		listMusic.add(urlMusic);
        		
        		titleMusic = StringUtils.stripAccents(titleMusic);
        		
        		listMusic.add(titleMusic);
    		}
    		
    		PostMethod postUp = new PostMethod(SITE_POST);
    		
    		postUp.addParameter("type", "3");
    		postUp.addParameter("username", getUser());
    		postUp.addParameter("music", listMusic.toString());
    		
        	if(client.executeMethod(postUp) == 200 && postUp.getResponseBodyAsString().equals("1"))
        	{
        		return true;
        	}
        	else
        	{
        		return false;
        	}
    	}
    	else
    	{
    		return false;
    	}
	}
	
	List<String> getMusic()
	{
    	HttpClient client = new HttpClient();
    	PostMethod postGet = new PostMethod(SITE_POST);
    
    	postGet.addParameter("type", "4");
    	postGet.addParameter("username", getUser());
    	
    	try
    	{
			if(client.executeMethod(postGet) == 200)
			{
				String musicOld = postGet.getResponseBodyAsString();
				
				musicOld = musicOld.substring(2,musicOld.length()-1);
				
				if(musicOld.isEmpty())
				{
					return new ArrayList<String>();
				}
				else
				{
					return new ArrayList<String>(Arrays.asList(musicOld.split(",")));
				}
			}
			else
			{
				return null;
			}
		}
    	catch (IOException e)
    	{
			e.printStackTrace();
			return null;
		}
	}
	
	void downloadMusic(String titleVideo, URL urlVideo, int sizeVideo) throws IOException
	{
		String pathFolder = readFile(PC_PATH+"setup.txt");
		
		titleVideo = StringUtils.stripAccents(titleVideo);
		
		File newSon = new File(pathFolder+File.separator+titleVideo+".mp3");
		
		SwingWorker sw = new SwingWorker()
		{
			protected Object doInBackground() throws Exception
			{
				FileUtils.copyURLToFile(urlVideo, newSon);
				return null;
			}
			
			public void done()
			{     
				if(SwingUtilities.isEventDispatchThread())
				{
					if(newSon.length()/1028<sizeVideo)
					{
						try
						{
							newSon.delete();
							FileUtils.copyURLToFile(urlVideo, newSon);
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
					}
					else
					{
						setupList();
					}
		        }
			} 
		};
		
		sw.execute();
	}
	
	public void setupList()
	{
		model.removeAllElements();
		list.removeAll();
		
		list.setCellRenderer(new couleurList());
		
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://164.132.145.12/ftplus/account.php");
		
		post.addParameter("type","4");
		post.addParameter("username",getUser());
		
		String resultMusic = "";
		
		try
		{
			client.executeMethod(post);
			resultMusic = post.getResponseBodyAsString();
		}
		catch (HttpException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		resultMusic = resultMusic.substring(2, resultMusic.length()-1);
		
		List<String> listMusics = new ArrayList<String>(Arrays.asList(resultMusic.split(",")));
		
		for (int i = 1; i < listMusics.size(); i+=2)
		{
			String music = listMusics.get(i).trim();
			if(!music.isEmpty())
			{
				model.addElement(music);
			}
		}
		
		//		try (BufferedReader br = new BufferedReader(new FileReader(new File(PC_PATH+"music.txt"))))
		//		{
		//		    String line;
		//		    while ((line = br.readLine()) != null)
		//		    {
		//		    	String urlToDl = "http://www.youtubeinmp3.com/fetch/?format=JSON&video=https://www.youtube.com/watch?v="+line;
		//		    	JSONObject json = readJsonFromUrl(urlToDl);
		//			    String titleVideo = json.getString("title");
		//		       model.addElement(titleVideo);
		//		       
		//		    }
		//		    
		//		}
		//		catch (Exception e4)
		//		{
		//			System.out.println(e4);
		//		}
		
		
		try (BufferedReader br = new BufferedReader(new FileReader(new File(PC_PATH+"new_title.txt"))))
		{
		    String line;
		    while ((line = br.readLine()) != null)
		    {
		       model.addElement(line);
		       
		    }
		}
		catch (Exception e4)
		{
			System.out.println(e4);
		}
		
		//		for (File file : files) {
		//			if(file.isFile())
		//			{
		//				model.addElement(file.getName());	
		//			}
		//		}
		
		list.setModel(model);
		list.setSelectedIndex(0);
	}
	
	public static void waitForRunLater(WebView webView) throws InterruptedException
	{
	    Semaphore semaphore = new Semaphore(0);
		Platform.runLater((new Runnable()
		{
			@Override
			public void run()
			{
				webView.getEngine().reload();
				semaphore.release();
			}
		}));
	    semaphore.acquire();
	}
	
	  private static String readAll(Reader rd) throws IOException
	  {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
		  }

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException
	{
		InputStream is = new URL(url).openStream();
		try {
		      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		      String jsonText = readAll(rd);
		      JSONObject json = new JSONObject(jsonText);
		      return json;
		    } finally {
		      is.close();
		}
	}	
		  
	private void setupPreview()
	{
		try {
			File txtEdit = new File(PC_PATH+list.getSelectedValue());
			FileReader fr = new FileReader(txtEdit);
			BufferedReader br = new BufferedReader(fr);
			textArea.read(br, null);
			btnNewButton_1.setEnabled(false);
		}
		catch(Exception e2) {System.out.println(e2);}
	}
	
    protected static ImageIcon createImageIcon(String path)
    {
        URL imgURL = mainWindow.class.getResource(path);
        
        if (imgURL != null)
        {
            return new ImageIcon(imgURL);
        }
        else
        {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}