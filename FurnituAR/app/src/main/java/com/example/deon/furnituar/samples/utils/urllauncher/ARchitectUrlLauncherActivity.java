package com.wikitude.samples.utils.urllauncher;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.wikitude.architect.ArchitectView;
import com.wikitude.sdksamples.R;




/**
 * Activity showing intro slides describing app in few words
 */
public class ARchitectUrlLauncherActivity extends Activity {


	/**
	 * onMenu ID for clearing URL history
	 */
	private final int				MENU_ID_HISTORY_CLEAR	= 1;

	private static final String 	tmpInforamtionFileName = "visitedUrl.tmp";


	@Override
	protected void onCreate( final Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		
		/* set content view to parent layout*/
		this.setContentView( R.layout.urllauncher_main );

		/* the web-view containing EULA text*/
		final AutoCompleteTextView url = (AutoCompleteTextView)this.findViewById( R.id.url );
		final Properties prop = loadProperties();
		String urlText = prop.getProperty("default_url", "");
		url.setText(urlText);

		final String[] visitedUrls = this.getVisitedUrlsArray();

		if ( visitedUrls != null && visitedUrls.length > 0 ) {


			final ArrayAdapter<String> adapter = new ArrayAdapter<String>( this,
					android.R.layout.simple_dropdown_item_1line, visitedUrls );

			url.setAdapter( adapter );
		}

		/* button to visit url */
		final Button buttonVisitUrl = (Button)this.findViewById( R.id.button_visit_url );

		buttonVisitUrl.setOnClickListener( new View.OnClickListener() {

			@Override
			public void onClick( final View arg0 ) {
				String urlString = url.getText().toString();

				try {
					new URL( urlString );
				} catch ( final Exception e ) {
					urlString = "http://" + urlString;
				}

				final List<String> visitedUrls = ARchitectUrlLauncherActivity.this.getVisitedUrls();
				if ( !visitedUrls.contains( urlString ) ) {
					visitedUrls.add( urlString );
					//also add string without http prefix
					if(urlString.contains("http://"))
						visitedUrls.add(urlString.replace("http://", ""));
				}
				ARchitectUrlLauncherActivity.this.setVisitedUrls( visitedUrls );
				ARchitectUrlLauncherActivity.this.launchArchitectCam( urlString );
			}
		} );
	}

	private static Properties loadProperties() {
		final Properties prop = new Properties();
		try {
			prop.load(ArchitectView.class.getClassLoader().getResourceAsStream("assets/samples.properties"));
		} catch (final Exception ex) {
			// ignore
		}
		return prop;
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.refreshAutoCompletionUrls();
	}

	@Override
	public boolean onOptionsItemSelected( final MenuItem item ) {
		/* clear url-history in onMenu */
		this.setVisitedUrls( new ArrayList<String>() );
		this.refreshAutoCompletionUrls();
		return true;
	}


	@Override
	public boolean onPrepareOptionsMenu( final Menu menu ) {
		/* clear url-history in onMenu */
		menu.clear();
		int menuCounter = 0;
		final MenuItem item = menu.add( 1, this.MENU_ID_HISTORY_CLEAR, menuCounter++, R.string.urllauncher_menu_clear_history );
		item.setIcon( android.R.drawable.ic_delete );
		return true;
	}


	private void refreshAutoCompletionUrls() {
		final AutoCompleteTextView url = (AutoCompleteTextView)this.findViewById( R.id.url );
		final String[] visitedUrls = this.getVisitedUrlsArray();

		if ( visitedUrls != null ) {
			final ArrayAdapter<String> adapter = new ArrayAdapter<String>( this,
					android.R.layout.simple_dropdown_item_1line, visitedUrls );

			url.setAdapter( adapter );
		}
	}
	

	/**
	 * list of visited Urls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<String> getVisitedUrls() {
		try {
			final File tmpfile = new File( this.getCacheDir().getAbsolutePath() + File.separator + ARchitectUrlLauncherActivity.tmpInforamtionFileName );
			final InputStream file = new FileInputStream( tmpfile );
			final InputStream buffer = new BufferedInputStream( file );
			final ObjectInput input = new ObjectInputStream( buffer );
			try {
				return (List<String>)input.readObject();
			} finally {
				input.close();
			}
		} catch ( final Exception e ) {
			return new ArrayList<String>();
		}
	}


	/***
	 * 
	 * @return visited urls as String-Array
	 */
	private String[] getVisitedUrlsArray() {
		final List<String> visitedUrls = this.getVisitedUrls();
		final String[] visitedUrlsArray = new String[visitedUrls.size()];
		int i = 0;
		for ( final String visitedUrl : visitedUrls ) {
			visitedUrlsArray[i++] = visitedUrl;
		}
		return visitedUrlsArray;
	}

	/**
	 * launches ARchitect Cam with given url
	 * @throws UnsupportedEncodingException 
	 */
	private void launchArchitectCam( final String url ) {
		final Intent architectIntent = new Intent( this, ARchitectUrlLauncherCamActivity.class); 
		
		try {
			final String encodedUrl = URLEncoder.encode( url, "UTF-8" );
			architectIntent.putExtra( ARchitectUrlLauncherCamActivity.ARCHITECT_ACTIVITY_EXTRA_KEY_URL, encodedUrl );
		} catch (UnsupportedEncodingException e) {
			Toast.makeText(this, "Unexpected Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		ARchitectUrlLauncherActivity.this.startActivity( architectIntent );
	}

	/**
	 * set visited url history
	 * @param urls
	 */
	private void setVisitedUrls( final List<String> urls ) {

		final File tmpfile = new File( this.getCacheDir().getAbsolutePath() + File.separator + ARchitectUrlLauncherActivity.tmpInforamtionFileName );

		try {
			final OutputStream file = new FileOutputStream( tmpfile );
			final OutputStream buffer = new BufferedOutputStream( file );
			final ObjectOutput output = new ObjectOutputStream( buffer );
			try {
				output.writeObject( urls );
			} finally {
				output.close();
			}
		} catch ( final IOException e ) {
			e.printStackTrace();
		}
	}
}
