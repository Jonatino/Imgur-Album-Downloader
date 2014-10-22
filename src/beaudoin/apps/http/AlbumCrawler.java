package beaudoin.apps.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JProgressBar;

import beaudoin.apps.gui.GUI;
import beaudoin.apps.imgur.ImgurImage;

public final class AlbumCrawler {

	private String webpage;

	private AlbumCrawler(String webpage) {
		this.webpage = webpage;
	}

	public static AlbumCrawler create(String webpage) {
		if (!webpage.endsWith("noscript"))
			webpage += "/noscript";
		if (!webpage.startsWith("http://"))
			webpage = "http://" + webpage;
		return new AlbumCrawler(webpage);
	}

	public List<ImgurImage> getImages() {
		List<String> source = getPageSource();
		List<ImgurImage> images = new LinkedList<>();
		String pattern = "(?<=<img\\ssrc=\"\\/\\/)[^\"]*";
		source.stream().forEach(s -> {
			Matcher m = Pattern.compile(pattern).matcher(s);
			if (m.find()) {
				images.add(new ImgurImage(m.group()));
			}
		});
		return images;
	}

	List<String> getPageSource() {
		List<String> lines = new LinkedList<String>();
		try {
			URL url = new URL(webpage);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;
			while ((line = in.readLine()) != null) {
				lines.add(line);
			}
			in.close();
		} catch (Exception e) {
		}
		return lines;
	}

	public void download(JProgressBar bar, String destination) {
		if (!destination.endsWith("\\"))
			destination += "\\";
		download(bar, new File(destination));
	}

	public void download(JProgressBar bar, File destination) {
		Thread t = new Thread(() -> {
			int downloaded = 0;
			List<ImgurImage> images = getImages();
			for (ImgurImage i : images) {
				i.download(destination);
				downloaded++;
				int perc = (int) ((((double) downloaded) / images.size()) * 100);
				bar.setValue(perc);
				if (perc == 100)
					GUI.get().notifyFinished();
			}
		});
		t.start();

	}
}
