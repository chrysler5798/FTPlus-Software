package main;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.apache.commons.net.ftp.FTPClient;

import main.window.folderWindow;
import main.window.mainWindow;
import main.window.userWindow;

public class launchAct
{
	final static String newline = System.getProperty("line.separator");
	final static String PC_PATH = System.getenv("APPDATA")+File.separator+"FTPlus"+File.separator;
	
	public static void main(String[] args) throws IOException, InterruptedException
	{
		
		setupFolder();
		
		if(!checkConn())
		{
		    JOptionPane optionPane = new JOptionPane("Aucune connexion Internet",JOptionPane.WARNING_MESSAGE);
		    JDialog dialog = optionPane.createDialog("FTPlus");
		    Toolkit.getDefaultToolkit().beep();
		    dialog.setAlwaysOnTop(true);
		    dialog.setVisible(true);
		    System.exit(0);
		}
		
		
		if(readFile(PC_PATH+"user.txt").isEmpty())
		{
			new userWindow();
		}
		else if(readFile(PC_PATH+"setup.txt").isEmpty())
		{
			new folderWindow();
		}
		else
		{
			new mainWindow();
		}
	    
		//try
		//{
			//fClient.connect(SERVER_IP, PORT);
			//System.out.println(printServerReply(fClient));
			//int replayCode = fClient.getReplyCode();
			//if(!FTPReply.isPositiveCompletion(replayCode))
			//{
			//	result = "Erreur "+replayCode;
			//	return;
			//}
			//boolean success = fClient.login(USER, PASSWORD);
			//System.out.println(printServerReply(fClient));
			
			//if(!success)
			//{
			//	result = "Erreur : User FTP";
			//	return;
			//}
			//else
			//{
			//	result = "Tout : OK";
				
//				String pathFtp = "ftplus/khrys/";
//				
//				files = fClient.listFiles(pathFtp);
//				fClient.setFileType(FTP.BINARY_FILE_TYPE);
//				
//				for (FTPFile ftpFile : files)
//				{
//					String name = ftpFile.getName();
//					String path = pathFtp+name;
//					File dlFile = new File(name);
//					OutputStream outStream = new BufferedOutputStream(new FileOutputStream(dlFile));
//					boolean isDownload = fClient.retrieveFile(path, outStream);
//					
//					if(isDownload)
//					{
//						System.out.println("Download : OK ("+name+")");
//					}
//					outStream.close();
//				}
			//}
//		}
//		catch (IOException ex)
//		{
//			result = "Erreur";
//			ex.printStackTrace();
//		}
		
//		for (int i = 0; i < 4; i++) {
//	    	String urlJson = "http://www.youtubeinmp3.com/fetch/?format=JSON&filesize=1&video=https://www.youtube.com/watch?v=1XZGHOxnCto";
//	    	JSONObject json = readJsonFromUrl(urlJson);
//	    	
//		    String titleVideo = json.getString("title");
//		    String stringToDl = json.getString("link");
//		    String size = json.getString("filesize");
//		    
//		    int newSize = Integer.parseInt(size);
//		    
//		    System.out.println(newSize/1028);
//			URL urlDl = new URL(stringToDl);
//			File sonFile = new File(PC_PATH+"test"+i+".mp3");
//			FileUtils.copyURLToFile(urlDl, sonFile);
//			
//			int filesi = (int) (sonFile.length()/1028);
//			System.out.println(filesi);
//			
//			TimeUnit.SECONDS.sleep(5);
//			
//			if(filesi<25)
//			{
//				System.out.println("OK");
//				sonFile.delete();
//				FileUtils.copyURLToFile(urlDl, sonFile);
//			}
//		}
	}
	
	private static void setupFolder() throws IOException
	{	
		new File(PC_PATH).mkdir();
		
		new File(PC_PATH+"new_url.txt").createNewFile();
		new File(PC_PATH+"new_title.txt").createNewFile();
		new File(PC_PATH+"setup.txt").createNewFile();
		new File(PC_PATH+"user.txt").createNewFile();
	}
	
	private static boolean checkConn()
	{
		try
		{
			return InetAddress.getByName("www.google.com").isReachable(2000);
		}
		catch (IOException e)
		{
			return false;
		}
	}
	
	static String readFile(String path) throws IOException 
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, Charset.defaultCharset());
	}
	
	private static String printServerReply(FTPClient ftpClient)
	{
		String txtR = "";
		String[] msgs = ftpClient.getReplyStrings();
		if(msgs != null && msgs.length > 0)
		{
			for (String string : msgs)
			{
				txtR += newline+"SERVER: "+string;
			}
		}
		return txtR;
	}
}