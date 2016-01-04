package com.wikitude.samples.utils.urllauncher;

import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.view.WindowManager;

import com.example.deon.furnituar.SampleCamFragment;
import com.wikitude.sdksamples.R;

/**
 * Demo Implementation of a fragment use of the Wikitude ARchitectView.
 *
 */
public class ARchitectUrlLauncherCamActivity extends FragmentActivity {
	
	public static final String ARCHITECT_ACTIVITY_EXTRA_KEY_URL = "url2load";

	@Override
	protected void onCreate( final Bundle icicle ) {
		super.onCreate( icicle );

		this.requestWindowFeature( Window.FEATURE_NO_TITLE );
		this.getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
		this.setVolumeControlStream( AudioManager.STREAM_MUSIC );

		this.setContentView( R.layout.sample_cam_fragment_main );

		this.findViewById( R.id.mainFragementParent ).setBackgroundColor( Color.BLACK );
		
		if ( icicle == null ) {
			/* start transaction to set required fragments */
			final FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
			fragmentTransaction.replace( R.id.mainFragement, new SampleCamFragment() );
			/* commit transaction */
			fragmentTransaction.commit();
		}

	}
	

}