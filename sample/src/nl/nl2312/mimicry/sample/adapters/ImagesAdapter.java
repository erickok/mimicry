package nl.nl2312.mimicry.sample.adapters;

import java.security.InvalidParameterException;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Simple adapter that shows full bleed images (given some set of image drawable
 * resource id) yet is backed by a set of titles too, which is the data object
 * returned in {@link #getItem(int)}.
 * 
 * @author Eric Kok
 */
public class ImagesAdapter extends BaseAdapter {

	private final Context context;
	private final int[] images;
	private final String[] titles;

	public ImagesAdapter(Context context, int[] images, String[] titles) {
		this.context = context;
		this.images = images;
		this.titles = titles;
		if (titles.length != images.length)
			throw new InvalidParameterException(
					"List of images should be of equal length as the list of titles.");
	}

	@Override
	public int getCount() {
		return images.length;
	}

	@Override
	public Object getItem(int position) {
		return titles[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = (ImageView) convertView;
		if (imageView == null) {
			imageView = new ImageView(context);
		}
		imageView.setAdjustViewBounds(true);
		imageView.setImageResource(images[position]);
		return imageView;
	}
}
