package com.speed_alert;

import android.location.Location;

public class CLocation extends Location {
	  private boolean bUseMetricUnits = false;

	  public CLocation(Location location)
	  {
	    this(location, true);
	  }

	  public CLocation(Location location, boolean bUseMetricUnits)
	  {
	    super(location);
	    this.bUseMetricUnits = bUseMetricUnits;
	  }

	  public boolean getUseMetricUnits()
	  {
	    return this.bUseMetricUnits;
	  }

	  public void setUseMetricUnits(boolean bUseMetricUnits)
	  {
	    this.bUseMetricUnits = bUseMetricUnits;
	  }



	  
	  public double getLatitude()
	  {
	    double nSpeed = super.getLatitude();

	    

	    return nSpeed;
	  }
	
	
	  @Override
	  public float getSpeed()
	  {
	    float nSpeed = super.getSpeed();

	    if (!this.getUseMetricUnits())
	    {
	      // Convert meters/second to miles/hour
	      nSpeed = nSpeed * 3.600f;
	    }

	    return nSpeed;
	  }
	}
	