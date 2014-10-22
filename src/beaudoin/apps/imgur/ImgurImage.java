package beaudoin.apps.imgur;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public final class ImgurImage {

	String imageUrl;

	public ImgurImage(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public boolean download(File out) {
		try {
			URL url = new URL(imageUrl.startsWith("http://") ? imageUrl : "http://" + imageUrl);

			InputStream is = new BufferedInputStream(url.openStream());
			OutputStream os = new BufferedOutputStream(new FileOutputStream(out + "/" + getName()));
			for (int i; (i = is.read()) != -1;)
				os.write(i);
			is.close();
			os.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	String getName() {
		return imageUrl.substring(imageUrl.indexOf("/"));
	}

	String getType() {
		String name = getName();
		return name.substring(name.indexOf(".") + 1);
	}
}
